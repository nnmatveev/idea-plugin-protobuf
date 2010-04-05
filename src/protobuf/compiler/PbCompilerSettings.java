package protobuf.compiler;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

/**
 * author: Nikolay Matveev
 * Date: Apr 5, 2010
 */

@State(
        name = "PbCompilerSettings",
        storages = {
                @Storage(id = "proto", file = "$APP_CONFIG$/proto_compiler.xml")
        }
)
public class PbCompilerSettings implements PersistentStateComponent<PbCompilerSettings>, ApplicationComponent {

    public boolean COMPILE_PROTO = false;
    public String PATH_TO_COMPILER = "";

    public static PbCompilerSettings getInstance() {
        return ApplicationManager.getApplication().getComponent(PbCompilerSettings.class);
    }

    @Override
    public PbCompilerSettings getState() {
        return this;
    }

    @Override
    public void loadState(PbCompilerSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "PbCompilerSettings";
    }

    @Override
    public void initComponent() {        
    }

    @Override
    public void disposeComponent() {
    }
}
