package protobuf.compiler;

import com.intellij.compiler.CompilerSettingsFactory;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import protobuf.settings.application.PbCompilerApplicationSettings;
import protobuf.settings.application.PbCompilerConfigurable;

/**
 * author: Nikolay Matveev
 * Date: Apr 5, 2010
 */
public class PbCompilerSettingsFactory implements CompilerSettingsFactory {
    @Override
    public Configurable create(Project project) {
        return new PbCompilerConfigurable(PbCompilerApplicationSettings.getInstance(),project);
    }
}
