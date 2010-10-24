package protobuf.settings.facet;

import com.intellij.facet.ui.FacetEditorContext;
import com.intellij.facet.ui.FacetEditorTab;
import com.intellij.facet.ui.FacetValidatorsManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.annotations.Nls;
import protobuf.util.PbBundle;

import javax.swing.*;

/**
 * The Protobuf facet settings editor form.
 * @author Travis Cripps
 */
public class ProtobufFacetEditor extends FacetEditorTab {

    private JPanel settingsPanel;
    private ProtobufFacetCommonSettingsEditor commonSettingsEditor;
    ProtobufFacetConfiguration configuration;

    public ProtobufFacetEditor(FacetEditorContext editorContext, FacetValidatorsManager validatorsManager, ProtobufFacetConfiguration configuration) {
        this.configuration = configuration;

        final Project project = editorContext.getProject();
        final Module module = editorContext.getModule();

        commonSettingsEditor.getEnableCompilationCheckbox().setSelected(configuration.isCompilationEnabled());
        commonSettingsEditor.getProtobufCompilerOutputPathField().setText(configuration.getCompilerOutputPath());
        commonSettingsEditor.getProtobufCompilerOutputPathField().addBrowseFolderListener(project, new CompilerOutputBrowseFolderActionListener(project, module, commonSettingsEditor.getProtobufCompilerOutputPathField()));
    }

    @Nls
    @Override
    public String getDisplayName() {
        return PbBundle.message("facet.protobuf.settings");
    }

    @Override
    public JComponent createComponent() {
        return settingsPanel;
    }

    @Override
    public boolean isModified() {
        boolean compilationEnabled = commonSettingsEditor.getEnableCompilationCheckbox().isSelected();
        String outputPath = commonSettingsEditor.getProtobufCompilerOutputPathField().getText().trim();

        return (configuration.isCompilationEnabled() != compilationEnabled ||
            !Comparing.equal(configuration.getCompilerOutputPath(), FileUtil.toSystemIndependentName(outputPath)));
    }

    @Override
    public void apply() throws ConfigurationException {
        configuration.setIsCompilationEnabled(commonSettingsEditor.getEnableCompilationCheckbox().isSelected());
        configuration.setCompilerOutputPath(FileUtil.toSystemIndependentName(commonSettingsEditor.getProtobufCompilerOutputPathField().getText().trim()));
    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {
        
    }

    public JPanel getMainPanel() {
        return settingsPanel;
    }

    public ProtobufFacetCommonSettingsEditor getCommonSettingsEditor() {
        return commonSettingsEditor;
    }

    public JCheckBox getEnableCompilationCheckbox() {
        return commonSettingsEditor.getEnableCompilationCheckbox();
    }

    public TextFieldWithBrowseButton getProtobufCompilerOutputPathField() {
        return commonSettingsEditor.getProtobufCompilerOutputPathField();
    }

}
