package protobuf.compiler;

import com.intellij.compiler.CompilerConfiguration;
import com.intellij.compiler.impl.CompilerUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.compiler.*;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.StreamUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import protobuf.file.ProtobufFileType;
import protobuf.util.PbBundle;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: Nikolay Matveev
 * Date: Apr 5, 2010
 */
public class PbCompiler implements SourceGeneratingCompiler {

    private final static Logger LOG = Logger.getInstance(PbCompiler.class.getName());

    private static final GenerationItem[] EMPTY_GENERATION_ITEM_ARRAY = new GenerationItem[]{};

    private static final String PROTOC_WINDOWS = "protoc.exe";
    private static final String PROTOC_LINUX = "protoc";


    private static final Matcher ERROR_IN_LINE_MATCHER = Pattern.compile("[^:]*:[0-9]*:[0-9]*:.*").matcher("");
    private static final Matcher ERROR_IN_FILE_MATCHER = Pattern.compile("[^:]*:[^:]*").matcher("");

    Project myProject;


    PbCompilerApplicationSettings compilerAppSettings;
    PbCompilerProjectSettings compilerProjectSettings;

    public PbCompiler(Project project) {
        myProject = project;

        compilerAppSettings = ApplicationManager.getApplication().getComponent(PbCompilerApplicationSettings.class);
        compilerProjectSettings = myProject.getComponent(PbCompilerProjectSettings.class);
    }

    @Override
    public GenerationItem[] getGenerationItems(CompileContext compileContext) {
        ProjectFileIndex fileIndex = ProjectRootManager.getInstance(myProject).getFileIndex();
        CompileScope compileScope = compileContext.getCompileScope();
        CompilerConfiguration compilerConfiguration = CompilerConfiguration.getInstance(myProject);
        VirtualFile[] files = compileScope.getFiles(ProtobufFileType.PROTOBUF_FILE_TYPE, false);
        ArrayList<GenerationItem> generationItems = new ArrayList<GenerationItem>(files.length);
        for (VirtualFile file : files) {
            if (!compilerConfiguration.isExcludedFromCompilation(file)) {
                generationItems.add(new PbGenerationItem(file, compileContext.getModuleByFile(file), fileIndex.isInTestSourceContent(file)));
            }
        }
        if (generationItems.size() > 0) {
            return generationItems.toArray(new GenerationItem[generationItems.size()]);
        }

        return EMPTY_GENERATION_ITEM_ARRAY;
    }

    @Override
    public GenerationItem[] generate(CompileContext compileContext, GenerationItem[] generationItems, VirtualFile outputRootDirectory) {
        //todo [medium] if files located not in project root dir, problem may occur
        final String commandBase = getPathToCompiler() + "" + " --proto_path=" + getBaseDir() + " --java_out=" + compilerProjectSettings.OUTPUT_SOURCE_DIRECTORY + " ";
        if (generationItems.length > 0) {
            for (GenerationItem item : generationItems) {
                Process proc;
                try {
                    proc = Runtime.getRuntime().exec(commandBase + item.getPath());
                    processStreams(compileContext, proc.getInputStream(), proc.getErrorStream(), (PbGenerationItem)item);
                    proc.destroy();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        CompilerUtil.refreshIOFile(new File(compilerProjectSettings.OUTPUT_SOURCE_DIRECTORY));
        return EMPTY_GENERATION_ITEM_ARRAY;
    }

    @Override
    public ValidityState createValidityState(DataInput dataInput) throws IOException {
        return null;
    }

    @NotNull
    @Override
    public String getDescription() {
        return PbBundle.message("compiler.description");
    }

    @Override
    public boolean validateConfiguration(CompileScope compileScope) {
        //check if compiler supports current operation system
        if (getCompilerExecutableName() == null) {
            Messages.showErrorDialog(PbBundle.message(
                    "compiler.validate.error.unsupported.os"),
                    PbBundle.message("compiler.validate.error.title"));
            return false;
        }
        //check path to compiler
        final String pathToCompiler = getPathToCompiler();
        File compilerFile = new File(pathToCompiler);
        if (!compilerFile.exists()) {
            Messages.showErrorDialog(PbBundle.message(
                    "compiler.validate.error.path.to.protoc", pathToCompiler),
                    PbBundle.message("compiler.validate.error.title"));
            return false;
        }
        //check that output source directory exists
        VirtualFile outputDirectory = LocalFileSystem.getInstance().findFileByIoFile(new File(compilerProjectSettings.OUTPUT_SOURCE_DIRECTORY));
        if (outputDirectory == null || !outputDirectory.exists()) {
            Messages.showErrorDialog(PbBundle.message(
                    "compiler.validate.error.ouput.source.directory.not.exists", compilerProjectSettings.OUTPUT_SOURCE_DIRECTORY),
                    PbBundle.message("compiler.validate.error.title"));
            return false;
        }
        //check that output source directory is a project source directory
        ProjectRootManager rootManager = ProjectRootManager.getInstance(myProject);
        VirtualFile[] sourceDirectories = rootManager.getContentSourceRoots();
        boolean isSourceDirectory = false;
        for (VirtualFile sourceDirectory : sourceDirectories) {
            if (sourceDirectory.equals(outputDirectory)) {
                isSourceDirectory = true;
                break;
            }
        }
        if (!isSourceDirectory) {
            Messages.showErrorDialog(PbBundle.message(
                    "compiler.validate.error.ouput.source.directory.not.source", compilerProjectSettings.OUTPUT_SOURCE_DIRECTORY),
                    PbBundle.message("compiler.validate.error.title"));
            return false;
        }
        return true;
    }

    //todo for linux and mac

    private String getCompilerExecutableName() {
        if (SystemInfo.isWindows) {
            return PROTOC_WINDOWS;
        } else if (SystemInfo.isLinux) {
            return PROTOC_LINUX;
        }
        return null;
    }

    private String getPathToCompiler() {
        return compilerAppSettings.PATH_TO_COMPILER + getCompilerExecutableName();
    }

    private String getBaseDir() {
        //todo [low] problems may occurs when source path is separated from project 
        return myProject.getBaseDir().getPath();
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
