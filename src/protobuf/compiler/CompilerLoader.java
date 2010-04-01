package protobuf.compiler;

import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import protobuf.file.ProtobufFileType;

/**
 * author: Nikolay Matveev
 * Date: Mar 14, 2010
 */
public class CompilerLoader implements ProjectComponent {
    private final Project myProject;

    public CompilerLoader(Project project) {
        myProject = project;
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        return "CompilerLoader";
    }

    public void projectOpened() {
        CompilerManager compilerManager = CompilerManager.getInstance(myProject);
        compilerManager.addCompilableFileType(ProtobufFileType.PROTOBUF_FILE_TYPE);
        compilerManager.addCompiler(new ProtobufGeneratingCompiler(myProject));
    }

    public void projectClosed() {
        CompilerManager compilerManager = CompilerManager.getInstance(myProject);
        compilerManager.removeCompilableFileType(ProtobufFileType.PROTOBUF_FILE_TYPE);
        compilerManager.removeCompiler(new ProtobufGeneratingCompiler(myProject));
    }
}
