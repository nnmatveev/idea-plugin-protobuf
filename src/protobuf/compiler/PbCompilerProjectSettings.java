package protobuf.compiler;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

/**
 * author: Nikolay Matveev
 * Date: Apr 6, 2010
 */

@State(
        name = "PbCompilerProjectSettings",
        storages = {
                @Storage(id = "default", file = "$PROJECT_FILE$"),
                @Storage(id = "dir", file = "$PROJECT_CONFIG_DIR$/proto_compiler.xml", scheme = StorageScheme.DIRECTORY_BASED)
        }                
)
public class PbCompilerProjectSettings implements PersistentStateComponent<PbCompilerProjectSettings>, ProjectComponent {

    public boolean COMPILE_PROTO = false;
    public String OUTPUT_SOURCE_DIRECTORY = "";

    public static PbCompilerProjectSettings getInstance(Project project) {
        return project.getComponent(PbCompilerProjectSettings.class);
    }

    @Override
    public PbCompilerProjectSettings getState() {
        return this;
    }

    @Override
    public void loadState(PbCompilerProjectSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    @Override
    public void projectOpened() {

    }

    @Override
    public void projectClosed() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return "PbCompilerProjectSettings";
    }

    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {
        
    }
}
