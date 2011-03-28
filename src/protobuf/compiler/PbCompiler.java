package protobuf.compiler;

import com.intellij.compiler.CompilerConfiguration;
import com.intellij.compiler.impl.CompilerUtil;
import com.intellij.facet.FacetManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.compiler.*;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.StreamUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import protobuf.PbBundle;
import protobuf.facet.PbFacet;
import protobuf.facet.PbFacetType;
import protobuf.file.PbFileType;
import protobuf.settings.application.PbCompilerApplicationSettings;
import protobuf.settings.facet.ProtobufFacetConfiguration;

import java.io.DataInput;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nikolay Matveev
 * Date: Apr 5, 2010
 */
public class PbCompiler implements SourceGeneratingCompiler {

    private final static Logger LOG = Logger.getInstance(PbCompiler.class.getName());

    private static final GenerationItem[] EMPTY_GENERATION_ITEM_ARRAY = new GenerationItem[]{};

    private static final String PROTOC_WINDOWS = "protoc.exe";
    private static final String PROTOC_LINUX = "protoc";
    private static final String PROTOC_MAC = "protoc";


    private static final Matcher ERROR_IN_LINE_MATCHER = Pattern.compile("[^:]*:[0-9]*:[0-9]*:.*").matcher("");
    private static final Matcher ERROR_IN_FILE_MATCHER = Pattern.compile("[^:]*:[^:]*").matcher("");

    Project myProject;

    PbCompilerApplicationSettings compilerAppSettings;

    public PbCompiler(Project project) {
        myProject = project;

        compilerAppSettings = ApplicationManager.getApplication().getComponent(PbCompilerApplicationSettings.class);
    }

    @Override
    public GenerationItem[] getGenerationItems(CompileContext compileContext) {
        final ProjectFileIndex fileIndex = ProjectRootManager.getInstance(myProject).getFileIndex();
        final CompileScope compileScope = compileContext.getCompileScope();
        final CompilerConfiguration compilerConfiguration = CompilerConfiguration.getInstance(myProject);
        final VirtualFile[] files = compileScope.getFiles(PbFileType.PROTOBUF_FILE_TYPE, false);
        final List<GenerationItem> generationItems = new ArrayList<GenerationItem>(files.length);
        for (VirtualFile file : files) {
            if (!compilerConfiguration.isExcludedFromCompilation(file)) {
                Module module = compileContext.getModuleByFile(file);
                final PbFacet facet = PbFacet.getInstance(module);
                if (facet != null) { // Generate if a Protobuf facet has been created for the module.
                    generationItems.add(new PbGenerationItem(file, module, fileIndex.isInTestSourceContent(file)));
                }
            }
        }
        if (generationItems.size() > 0) {
            return generationItems.toArray(new GenerationItem[generationItems.size()]);
        }

        return EMPTY_GENERATION_ITEM_ARRAY;
    }

    @Override
    public GenerationItem[] generate(CompileContext compileContext, GenerationItem[] generationItems, VirtualFile outputRootDirectory) {
        final ArrayList<GenerationItem> generatedItems = new ArrayList<GenerationItem>();
        final Set<Module> modulesToRefresh = new HashSet<Module>();
        final String protocPath = getPathToCompiler();
        if (generationItems.length > 0) {
            for (GenerationItem genItem : generationItems) {
                Process proc;
                PbGenerationItem item = (PbGenerationItem)genItem;
                if (item.shouldGenerate()) {
                    try {
                        StringBuilder compilerCommand = new StringBuilder();
                        compilerCommand.append(protocPath);
                        compilerCommand.append(" --proto_path=").append(item.getBaseDir());
                        compilerCommand.append(" --java_out=").append(item.getOutputPath());
                        compilerCommand.append(" ").append(item.getPath());
                        LOG.info("Invoking protoc: " + compilerCommand.toString());
                        proc = Runtime.getRuntime().exec(compilerCommand.toString());
                        processStreams(compileContext, proc.getInputStream(), proc.getErrorStream(), item);
                        proc.destroy();
                        generatedItems.add(genItem);
                        modulesToRefresh.add(item.getModule());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // Force the Virtual files to refresh so generated the module sources will be available to the project and to the UI.
        for (Module module : modulesToRefresh) {
            final PbFacet facet = PbFacet.getInstance(module);
            if (facet != null) {
                ProtobufFacetConfiguration config = facet.getConfiguration();
                if (config.isCompilationEnabled()) {
                    File outputPathFile = new File(facet.getConfiguration().getCompilerOutputPath());
                    CompilerUtil.refreshIOFile(outputPathFile);

                    // Refresh the source root corresponding to the output source path.
                    VirtualFile outputDirectory = LocalFileSystem.getInstance().findFileByIoFile(outputPathFile);
                    if (outputDirectory != null && outputDirectory.exists()) {
                        ModuleRootManager rootManager = ModuleRootManager.getInstance(module);
                        VirtualFile[] sourceDirectories = rootManager.getSourceRoots();
                        for (VirtualFile sourceDirectory : sourceDirectories) {
                            String sourcePathUrl = sourceDirectory.getPresentableUrl();
                            if (sourcePathUrl.equals(outputDirectory.getPresentableUrl())) {
                                LOG.info("Forcing refresh of source directory '" + sourceDirectory.getPath() + "' for module '" + module.getName() + "'");
                                sourceDirectory.refresh(false, true);
                                break;
                            }
                        }
                    }
                }
            }
        }

        return generatedItems.toArray(new GenerationItem[generatedItems.size()]);
    }

    @Override
    public ValidityState createValidityState(DataInput dataInput) throws IOException {
        return PbGenerationItemValidityState.load(dataInput);
    }

    @NotNull
    @Override
    public String getDescription() {
        return PbBundle.message("compiler.description");
    }

    @Override
    public boolean validateConfiguration(CompileScope compileScope) {
        // Check if the compiler supports current operating system.
        if (getCompilerExecutableName() == null) {
            Messages.showErrorDialog(PbBundle.message(
                    "compiler.validate.error.unsupported.os"),
                    PbBundle.message("compiler.validate.error.title"));
            return false;
        }

        // Check the to path to the compiler.
        final String pathToCompiler = getPathToCompiler();
        File compilerFile = new File(pathToCompiler);
        if (!compilerFile.exists()) {
            Messages.showErrorDialog(PbBundle.message(
                    "compiler.validate.error.path.to.protoc", pathToCompiler),
                    PbBundle.message("compiler.validate.error.title"));
            return false;
        } else if (!compilerFile.canExecute()) {
            Messages.showErrorDialog(PbBundle.message(
                    "compiler.validate.error.protoc.not.executable", pathToCompiler + "/protoc"),
                    PbBundle.message("compiler.validate.error.title"));
            return false;
        }

        Module[] modules = compileScope.getAffectedModules();
        for (Module module : modules) {
            PbFacet facet = FacetManager.getInstance(module).getFacetByType(PbFacetType.ID);
            if (facet != null) {
                String outputPath = facet.getConfiguration().getCompilerOutputPath();

                // Make sure the configuration has a value for the output source directory.
                if (outputPath == null) {
                    Messages.showErrorDialog(PbBundle.message(
                            "compiler.validate.error.output.source.directory.not.set", module.getName()),
                            PbBundle.message("compiler.validate.error.title"));
                    return false;
                }

                // Check that the output source directory exists.  Try to create it if not.
                File outputPathFile = new File(outputPath);
                if (!outputPathFile.exists()) {
                    // Create the directory -- I don't necessarily like the directory creation here.  It should probably
                    // occur during the generate method, but in order to validate the directory as a source module,
                    // we have to do it now.
                    boolean creationSuccessful = false;
                    try {
                        creationSuccessful = outputPathFile.mkdirs();
                    } catch (SecurityException se) {
                        // Eat the exception
                    }

                    if (!creationSuccessful) {
                        Messages.showErrorDialog(PbBundle.message(
                                "compiler.validate.error.output.source.directory.not.created", outputPath, module.getName()),
                                PbBundle.message("compiler.validate.error.title"));
                        return false;
                    }

                } else if (!outputPathFile.isDirectory()) {
                    Messages.showErrorDialog(PbBundle.message(
                            "compiler.validate.error.output.source.directory.not.directory", outputPath, module.getName()),
                            PbBundle.message("compiler.validate.error.title"));
                }


                // We might have just created the output directory, so we have to make sure to refresh IDEA's view of it.
                VirtualFile outputDirectory = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(outputPathFile);
                if (outputDirectory == null || !outputDirectory.exists()) {
                    Messages.showErrorDialog(PbBundle.message(
                            "compiler.validate.error.output.source.directory.not.exists", outputPath),
                            PbBundle.message("compiler.validate.error.title"));
                    return false;
                }

                // Check that the output source directory is a module source directory.
                ModuleRootManager rootManager = ModuleRootManager.getInstance(module);
                VirtualFile[] sourceDirectories = rootManager.getSourceRoots();
                boolean isSourceDirectory = false;
                for (VirtualFile sourceDirectory : sourceDirectories) {
                    String sourcePathUrl = sourceDirectory.getPath();
                    if (sourcePathUrl.equals(outputDirectory.getPath())) {
                        isSourceDirectory = true;
                        break;
                    }
                }
                if (!isSourceDirectory) {
                    Messages.showErrorDialog(PbBundle.message(
                            "compiler.validate.error.output.source.directory.not.source", outputPath, module.getName()),
                            PbBundle.message("compiler.validate.error.title"));
                    return false;
                }
            }
        }

        return true;
    }

    //todo for linux and mac

    public static String getCompilerExecutableName() {
        if (SystemInfo.isWindows) {
            return PROTOC_WINDOWS;
        } else if (SystemInfo.isLinux) {
            return PROTOC_LINUX;
        } else if(SystemInfo.isMac){
            return PROTOC_MAC;
        }
        return null;
    }

    private String getPathToCompiler() {
        return compilerAppSettings.PATH_TO_COMPILER;
    }

    private void processStreams(CompileContext context, InputStream inp, InputStream err, PbGenerationItem item) {
        try {
            String[] errorLines = StreamUtil.readText(err).trim().split("\n");
            for (String line : errorLines) {
                processLine(context, line.trim(), item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processLine(CompileContext context, String line, PbGenerationItem item) {
        if (line.matches("[^:]*:[0-9]*:[0-9]*:.*")) {
            String[] r = line.split(":");
            context.addMessage(CompilerMessageCategory.ERROR, r[3], item.getUrl(), Integer.parseInt(r[1]), Integer.parseInt(r[2]));
        } else if (line.matches("[^:]*:[^:]*")) {
            String[] r = line.split(":");
            context.addMessage(CompilerMessageCategory.ERROR, r[1], item.getUrl(), -1, -1);
        } else if (line.length() == 0) {
        } else {
            context.addMessage(CompilerMessageCategory.ERROR, line, item.getUrl(), -1, -1);
        }

    }
}
