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
    private JPanel settingsPanel;

    PbCompilerSettings mySettings;
    Project myProject;

    public PbCompilerConfigurable(PbCompilerSettings settings, Project project) {
        mySettings = settings;
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
        return mySettings.COMPILE_PROTO != enableCompilationCheckBox.isSelected() ||
                !mySettings.PATH_TO_COMPILER.equals(pathField.getText());
    }

    @Override
    public void apply() throws ConfigurationException {
        if (enableCompilationCheckBox.isSelected() && !mySettings.COMPILE_PROTO) {
            CompilerManager compilerManager = CompilerManager.getInstance(myProject);
            compilerManager.addCompilableFileType(ProtobufFileType.PROTOBUF_FILE_TYPE);
            CompilerManager.getInstance(myProject).addCompiler(new PbCompiler(myProject));
        } else if (!enableCompilationCheckBox.isSelected() && mySettings.COMPILE_PROTO) {
            CompilerManager compilerManager = CompilerManager.getInstance(myProject);
            compilerManager.removeCompilableFileType(ProtobufFileType.PROTOBUF_FILE_TYPE);
            for(PbCompiler compiler : compilerManager.getCompilers(PbCompiler.class)){
                compilerManager.removeCompiler(compiler);
            }
        }

        mySettings.COMPILE_PROTO = enableCompilationCheckBox.isSelected();
        mySettings.PATH_TO_COMPILER = pathField.getText();
    }

    @Override
    public void reset() {
        enableCompilationCheckBox.setSelected(mySettings.COMPILE_PROTO);
        pathField.setText(mySettings.PATH_TO_COMPILER);
    }

    @Override
    public void disposeUIResources() {
    }
}
