package protobuf.settings.project;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import protobuf.util.PbBundle;

import javax.swing.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 15, 2010
 */
public class ProtobufProjectConfigurable implements Configurable {

    private ProtobufProjectSettingsForm mySettingsForm;

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
            mySettingsForm = new ProtobufProjectSettingsForm();
        }
        return mySettingsForm.getSettingsComponent();
    }

    public boolean isModified() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void apply() throws ConfigurationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void reset() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void disposeUIResources() {
        mySettingsForm = null;
    }
}
