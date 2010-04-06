package protobuf.compiler;

import com.intellij.compiler.CompilerConfiguration;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.compiler.*;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.StreamUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import protobuf.file.ProtobufFileType;
import protobuf.util.PbBundle;

import java.io.*;
import java.util.ArrayList;

/**
 * author: Nikolay Matveev
 * Date: Apr 5, 2010
 */
public class PbCompiler implements SourceGeneratingCompiler {

    private final static Logger LOG = Logger.getInstance(PbCompiler.class.getName());

    private static final GenerationItem[] EMPTY_GENERATION_ITEM_ARRAY = new GenerationItem[]{};

    private static final String PROTOC_EXE = "protoc.exe";

    //regexp
    //private static final Pattern NEW_LINE = Pattern.compile("\r\n");

    //private static final Matcher REGULAR_ERROR_IN_FILE_MATCHER = Pattern.compile("[^:]*:[^:]:[^:].*").matcher("");

    Project myProject;

    String myUrlBase;

    public PbCompiler(Project project) {
        myProject = project;
        myUrlBase = "file://" + myProject.getBaseDir().getPath() + "/";
    }

    @Override
    public GenerationItem[] getGenerationItems(CompileContext compileContext) {
        ProjectFileIndex fileIndex = ProjectRootManager.getInstance(myProject).getFileIndex();
        CompileScope compileScope = compileContext.getCompileScope();
        CompilerConfiguration compilerConfiguration = CompilerConfiguration.getInstance(myProject);
        VirtualFile[] files = compileScope.getFiles(ProtobufFileType.PROTOBUF_FILE_TYPE, false);
        ArrayList<GenerationItem> generationItems = new ArrayList<GenerationItem>(files.length);
        for (VirtualFile file : files) {
            //if (!compilerConfiguration.isExcludedFromCompilation(file) || ) {
            generationItems.add(new PbGenerationItem(file, compileContext.getModuleByFile(file), fileIndex.isInTestSourceContent(file)));
            //}
        }
        if (generationItems.size() > 0) {
            return generationItems.toArray(new GenerationItem[generationItems.size()]);
        }

        return EMPTY_GENERATION_ITEM_ARRAY;
    }

    @Override
    public GenerationItem[] generate(CompileContext compileContext, GenerationItem[] generationItems, VirtualFile outputRootDirectory) {
        final String pathToCompiler = getPathToCompiler();
        final String baseDir = myProject.getBaseDir().getPath();
        final String outputDirectory;
        //myProject.getM
        ProjectRootManager rootManager = ProjectRootManager.getInstance(myProject);        
        rootManager.getContentSourceRoots();
        final String commandBase = pathToCompiler + " --proto_path=" + baseDir + " --java_out=" + baseDir + " --error_format=gcc" + " ";
        if (generationItems.length > 0) {
            for (GenerationItem item : generationItems) {
                try {
                    Process proc = Runtime.getRuntime().exec(commandBase + item.getPath());
                    processStreams(compileContext, proc.getInputStream(), proc.getErrorStream());
                    proc.destroy();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }
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
        PbCompilerApplicationSettings compilerAppSettings = ApplicationManager.getApplication().getComponent(PbCompilerApplicationSettings.class);
        PbCompilerProjectSettings compilerProjectSettings = myProject.getComponent(PbCompilerProjectSettings.class);
        //check path to compiler
        final String pathToCompiler = getPathToCompiler();
        File compilerFile = new File(pathToCompiler);
        if (!compilerFile.exists()) {
            Messages.showErrorDialog("protoc not found in stated path: " + pathToCompiler, "Protocol Buffers");
            return false;
        }
        //check that output source directory is a project source directory
        ProjectRootManager rootManager = ProjectRootManager.getInstance(myProject);
        VirtualFile[] sourceDirectories = rootManager.getContentSourceRoots();
        for(VirtualFile sourceDirectory : sourceDirectories){
            //sourceDirectory.
        }
        return true;
    }

    private String getPathToCompiler() {
        PbCompilerApplicationSettings compilerSettings = ApplicationManager.getApplication().getComponent(PbCompilerApplicationSettings.class);
        final String pathToCompiler;
        if (SystemInfo.isWindows) {
            pathToCompiler = compilerSettings.PATH_TO_COMPILER + "\\" + PROTOC_EXE;
        } else if (SystemInfo.isLinux) {
            pathToCompiler = compilerSettings.PATH_TO_COMPILER + "\\" + PROTOC_EXE;
        } else {
            pathToCompiler = compilerSettings.PATH_TO_COMPILER + "\\" + PROTOC_EXE;
        }
        return pathToCompiler;
    }

    private void processStreams(CompileContext context, InputStream inp, InputStream err) {
        try {
            String[] errorLines = StreamUtil.readText(err).split("\r\n");
            for (String line : errorLines) {
                processLine(context, line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processLine(CompileContext context, String line) {
        if (line.matches("[^:]*:[0-9]*:[0-9]*:.*")) {
            String[] r = line.split(":");
            context.addMessage(CompilerMessageCategory.ERROR, r[3], myUrlBase + r[0], Integer.parseInt(r[1]), Integer.parseInt(r[2]));
        } else if (line.matches("[^:]*:[^:]*")) {
            String[] r = line.split(":");
            context.addMessage(CompilerMessageCategory.ERROR, r[1], myUrlBase + r[0], -1, -1);
        } else {
            context.addMessage(CompilerMessageCategory.ERROR, line, null, -1, -1);
        }

    }
}
