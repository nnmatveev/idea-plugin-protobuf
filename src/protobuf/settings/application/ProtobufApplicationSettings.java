package protobuf.settings.application;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 13, 2010
 */

@State(name = "ProtobufApplicationSettings", storages = {@Storage(id = "protobuf_application", file = "$APP_CONFIG$/protobuf.xml", scheme=StorageScheme.DIRECTORY_BASED)})
public class ProtobufApplicationSettings implements PersistentStateComponent<ProtobufApplicationSettings> {

    public String PROTOC_HOME = "";

    public static ProtobufApplicationSettings getInstance(){
        return ApplicationManager.getApplication().getComponent(ProtobufApplicationSettings.class);
    }

    public ProtobufApplicationSettings getState() {
        return this;
    }

    public void loadState(ProtobufApplicationSettings settings) {
        //XmlSerializerUtil.copyBean(settings, this);
    }
}
