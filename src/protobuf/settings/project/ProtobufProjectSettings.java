package protobuf.settings.project;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StorageScheme;
import com.intellij.util.xmlb.XmlSerializerUtil;

/**
 * author: Nikolay Matveev
 * Date: Mar 15, 2010
 */

@State(name = "ProtobufProjectSettings", storages = {@Storage(id = "protobuf_project", file = "$PROJECT_CONFIG_DIR$/protobuf.xml", scheme= StorageScheme.DIRECTORY_BASED)})
public class ProtobufProjectSettings  implements PersistentStateComponent<ProtobufProjectSettings> {

    public String JAVA_OUT = getDefaultJavaOut();

    public ProtobufProjectSettings getState() {
        return this;
    }

    public void loadState(ProtobufProjectSettings settings) {
        XmlSerializerUtil.copyBean(settings, this);
    }

    private String getDefaultJavaOut(){
        return "";
    }
}
