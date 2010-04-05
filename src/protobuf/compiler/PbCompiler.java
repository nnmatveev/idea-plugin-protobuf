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
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;
import protobuf.file.ProtobufFileType;
import protobuf.util.PbBundle;

import javax.print.DocFlavor;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * author: Nikolay Matveev
 * Date: Apr 5, 2010
 */
public class PbCompiler implements SourceGeneratingCompiler {

    private final static Logger LOG = Logger.getInstance(PbCompiler.class.getName());

    private static final GenerationItem[] EMPTY_GENERATION_ITEM_ARRAY = new GenerationItem[]{};

    private static final String PROTOC_EXE = "protoc.exe";

    //regexp

    Project myProject;

    public PbCompiler(Project project) {
        myProject = project;
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
        final String commandBase = pathToCompiler + " --proto_path=" + baseDir + " --java_out=" + baseDir + " ";
        if (generationItems.length > 0) {
            for (GenerationItem item : generationItems) {
                try {
                    Process proc = Runtime.getRuntime().exec(commandBase + item.getPath());                    
                    processStreams(compileContext, proc.getInputStream(), proc.getErrorStream());
                } catch (IOException e) {
                    e.printStackTrace();
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
        final String pathToCompiler = getPathToCompiler();
        File compilerFile = new File(pathToCompiler);
        if (!compilerFile.exists()) {
            Messages.showErrorDialog("protoc not found in stated path: " + pathToCompiler, "Protocol Buffers");
            return false;
        }
        return true;
    }

    private String getPathToCompiler() {
        PbCompilerSettings compilerSettings = ApplicationManager.getApplication().getComponent(PbCompilerSettings.class);
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(inp));            
            while(reader.ready()){
                processLine(context,reader.readLine());                
            }
            reader.close();
            inp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processLine(CompileContext context, String line){
        //dispatch regexpese will be done
        processErrorLine(context,line);    
    }

    private void processErrorLine(CompileContext context, String line){
        String errorText = null;
        String url = null;
        int lineNum = 0;
        int columnNum = 0;
        int ind1 = line.indexOf(':');
        url = line.substring(0,ind1);
        int ind2 = line.indexOf(':',ind1);
        lineNum = Integer.parseInt(line.substring(ind1,ind2));
        ind1 = line.indexOf(':',ind2);
        columnNum = Integer.parseInt(line.substring(ind2,ind1));
        errorText = line.substring(ind1);
//        if(errorText !=null && url != null && lineNum!=0&& columnNum!=0){
//            context.addMessage(CompilerMessageCategory.ERROR,errorText,url,lineNum,columnNum);
        //}
        context.addMessage(CompilerMessageCategory.ERROR,errorText,url,lineNum,columnNum);
    }
}
