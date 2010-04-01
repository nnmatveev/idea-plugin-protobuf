package protobuf.settings.application;

import com.intellij.openapi.components.PersistentStateComponent;
import protobuf.settings.application.ProtobufApplicationSettings;

import javax.swing.*;
import java.io.File;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public class ProtobufApplicationSettingsForm {
    private JTextField protocPathInput;

    private JPanel settingsComponent;

    //private final ProtobufApplicationSettings settings = new ProtobufApplicationSettings();

    //public ProtobufApplicationSettingsForm(ProtobufApplicationSettings settings) {
    //    loadState(settings);
    //}

    public JPanel getSettingsComponent() {
        return settingsComponent;
    }
    
    //public boolean isModified(ProtobufApplicationSettings protobufSettings){
    //    return !protocPathInput.equals(protobufSettings.PROTOC_HOME);
    //}

    //private boolean checkInput(){
    //    return new File(protocPathInput.getText()).exists();
    //}
}
