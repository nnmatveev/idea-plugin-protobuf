package protobuf.compiler;

import com.intellij.openapi.compiler.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import protobuf.settings.application.ProtobufApplicationSettings;

import java.io.DataInput;
import java.io.IOException;

/**
 * author: Nikolay Matveev
 * Date: Mar 14, 2010
 */
public class ProtobufGeneratingCompiler implements GeneratingCompiler {

    private Project myProject;

    public ProtobufGeneratingCompiler(Project project){
        myProject = project;
    }

    public GenerationItem[] getGenerationItems(CompileContext compileContext) {
        
        return new GenerationItem[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public GenerationItem[] generate(CompileContext compileContext, GenerationItem[] generationItems, VirtualFile virtualFile) {
        //
        return new GenerationItem[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ValidityState createValidityState(DataInput dataInput) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @NotNull
    public String getDescription() {
        return "ProtobufCompiler";
    }

    public boolean validateConfiguration(CompileScope compileScope) {        
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static void compile(VirtualFile file) throws IOException{
        ProtobufApplicationSettings settings = ProtobufApplicationSettings.getInstance();

    }
}
