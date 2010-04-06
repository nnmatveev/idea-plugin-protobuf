package protobuf.compiler;

import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import protobuf.file.ProtobufFileType;

import javax.swing.*;

/**
 * author: Nikolay Matveev
 * Date: Apr 5, 2010
 */
public class PbCompilerConfigurable implements Configurable {
    private JCheckBox enableCompilationCheckBox;
    private JTextField pathField;
    private JTextField outputSourceField;
    private JPanel settingsPanel;

    PbCompilerApplicationSettings myAppSettings;
    PbCompilerProjectSettings myProjectSettings;
    Project myProject;

    public PbCompilerConfigurable(PbCompilerApplicationSettings settings, Project project) {
        myAppSettings = settings;
        myProject = project;
        myProjectSettings = project.getComponent(PbCompilerProjectSettings.class);
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
        return myProjectSettings.COMPILE_PROTO != enableCompilationCheckBox.isSelected() ||
                !myAppSettings.PATH_TO_COMPILER.equals(pathField.getText())||
                !myProjectSettings.OUTPUT_SOURCE_DIRECTORY.equals(outputSourceField.getText());
    }

    @Override
    public void apply() throws ConfigurationException {
        if (enableCompilationCheckBox.isSelected() && !myProjectSettings.COMPILE_PROTO) {
            CompilerManager compilerManager = CompilerManager.getInstance(myProject);
            compilerManager.addCompilableFileType(ProtobufFileType.PROTOBUF_FILE_TYPE);
            CompilerManager.getInstance(myProject).addCompiler(new PbCompiler(myProject));
        } else if (!enableCompilationCheckBox.isSelected() && myProjectSettings.COMPILE_PROTO) {
            CompilerManager compilerManager = CompilerManager.getInstance(myProject);
            compilerManager.removeCompilableFileType(ProtobufFileType.PROTOBUF_FILE_TYPE);
            for(PbCompiler compiler : compilerManager.getCompilers(PbCompiler.class)){
                compilerManager.removeCompiler(compiler);
            }
        }

        myAppSettings.PATH_TO_COMPILER = pathField.getText();
        myProjectSettings.COMPILE_PROTO = enableCompilationCheckBox.isSelected();
        myProjectSettings.OUTPUT_SOURCE_DIRECTORY = outputSourceField.getText();
    }

    @Override
    public void reset() {
        enableCompilationCheckBox.setSelected(myProjectSettings.COMPILE_PROTO);
        outputSourceField.setText(myProjectSettings.OUTPUT_SOURCE_DIRECTORY);
        pathField.setText(myAppSettings.PATH_TO_COMPILER);
    }

    @Override
    public void disposeUIResources() {
    }
}
