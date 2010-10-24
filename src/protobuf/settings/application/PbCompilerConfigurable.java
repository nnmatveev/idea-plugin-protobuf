package protobuf.settings.application;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import protobuf.compiler.PbCompilerApplicationSettings;

import javax.swing.*;

/**
 * author: Nikolay Matveev
 * Date: Apr 5, 2010
 */
public class PbCompilerConfigurable implements Configurable {
    private JTextField pathField;
    private JPanel settingsPanel;

    PbCompilerApplicationSettings myAppSettings;
    Project myProject;

    public PbCompilerConfigurable(PbCompilerApplicationSettings settings, Project project) {
        myAppSettings = settings;
        myProject = project;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Protocol Buffers Compiler";
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public String getHelpTopic() {
        return null;
    }

    @Override
    public JComponent createComponent() {
        return settingsPanel;
    }

    @Override
    public boolean isModified() {
        return !myAppSettings.PATH_TO_COMPILER.equals(pathField.getText());
    }

    @Override
    public void apply() throws ConfigurationException {
        myAppSettings.PATH_TO_COMPILER = pathField.getText();
    }

    @Override
    public void reset() {
        pathField.setText(myAppSettings.PATH_TO_COMPILER);
    }

    @Override
    public void disposeUIResources() {
    }
}
