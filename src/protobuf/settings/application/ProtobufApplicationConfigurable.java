package protobuf.settings.application;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import protobuf.settings.application.ProtobufApplicationSettingsForm;
import protobuf.util.PbBundle;

import javax.swing.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public class ProtobufApplicationConfigurable implements Configurable {

    private ProtobufApplicationSettingsForm mySettingsForm;

    @Nls
    public String getDisplayName() {
        return PbBundle.message("protobuf.full");
    }

    public Icon getIcon() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getHelpTopic() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public JComponent createComponent() {
        if(mySettingsForm == null){
            //mySettingsForm = new ProtobufApplicationSettingsForm(ProtobufApplicationSettings.getInstance());
            mySettingsForm = new ProtobufApplicationSettingsForm();
        }
        return mySettingsForm.getSettingsComponent();
    }

    public boolean isModified() {
        //return mySettingsForm != null && mySettingsForm.isModified(ProtobufApplicationSettings.getInstance());
        return true;
    }

    public void apply() throws ConfigurationException {
        if(mySettingsForm != null){
            //mySettingsForm.loadState(ProtobufApplicationSettings.getInstance());
            //ProtobufApplicationSettings.getInstance().loadState(mySettingsForm.getState());
        }
    }

    public void reset() {
        if(mySettingsForm != null){
            //mySettingsForm.loadState(ProtobufApplicationSettings.getInstance());
        }
    }

    public void disposeUIResources() {
        mySettingsForm = null;
    }
}
