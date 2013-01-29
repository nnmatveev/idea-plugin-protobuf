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


        boolean useOutOfProcessBuild = false;
        try { // IDEA 11 doesn't have the useOutOfProcessBuild method, so we have to do things the hard way.
            CompilerWorkspaceConfiguration config = CompilerWorkspaceConfiguration.getInstance(project);
            Method m = config.getClass().getMethod("useOutOfProcessBuild");
            useOutOfProcessBuild = (Boolean)m.invoke(config);
        } catch (NoSuchMethodException nsme) {
            // Do nothing.
        } catch (IllegalAccessException iae) {
            // Do nothing.
        } catch (InvocationTargetException ite) {
            // Do nothing.
        }

        if (useOutOfProcessBuild) {
            // When using the out of process build, kick off the {@PBCompiler protobuffers generating compiler}.
            // When using the internal compiler, the {@PBCompiler} is invoked directly by IDEA.
            PbCompiler compiler = new PbCompiler(project);
            GeneratingCompiler.GenerationItem[] generationItems = compiler.getGenerationItems(context);
            compiler.generate(context, generationItems, null);
        }
        return result;
    }

}
