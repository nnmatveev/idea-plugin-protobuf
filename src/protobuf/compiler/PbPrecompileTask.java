package protobuf.compiler;

import com.intellij.compiler.CompilerWorkspaceConfiguration;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompileTask;
import com.intellij.openapi.compiler.GeneratingCompiler;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A precompile task for the Protobuffer compiler.
 * @author Travis Cripps
 */
public class PbPrecompileTask implements CompileTask {

    private static final Logger LOG = Logger.getInstance("#protobuf.compiler.PbPrecompileTask");

    @Override
    public boolean execute(CompileContext context) {
        boolean result = true;
        final Project project = context.getProject();

        // Kick off the {@PBCompiler protobuffers generating compiler}.
        PbCompiler compiler = new PbCompiler(project);
        GeneratingCompiler.GenerationItem[] generationItems = compiler.getGenerationItems(context);
        compiler.generate(context, generationItems, null);
        return result;
    }

}
