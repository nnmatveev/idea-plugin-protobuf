package protobuf.settings.facet;

import com.intellij.facet.Facet;
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
import org.jetbrains.annotations.NotNull;
import protobuf.PbBundle;
import protobuf.facet.PbFacet;

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
        commonSettingsEditor.getProtobufCompilerRunInFixedDirectory().setSelected(configuration.isCompilationUseGivenRunDirectory());
        commonSettingsEditor.getProtobufCompilerRunDirectory().setText(configuration.getCompilationRunDirectory());
        commonSettingsEditor.getProtobufCompilerRunDirectory().addBrowseFolderListener(project, new CompilerOutputBrowseFolderActionListener(project, module, commonSettingsEditor.getProtobufCompilerRunDirectory()));
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
    public void onFacetInitialized(@NotNull final Facet facet) {
        ((PbFacet)facet).updateCompilerOutputWatchRequest();
    }

    @Override
    public boolean isModified() {
        boolean compilationEnabled = commonSettingsEditor.getEnableCompilationCheckbox().isSelected();
        String outputPath = commonSettingsEditor.getProtobufCompilerOutputPathField().getText().trim();
        boolean compileInGivenPath = commonSettingsEditor.getProtobufCompilerRunInFixedDirectory().isSelected();
        String runDirectory = commonSettingsEditor.getProtobufCompilerRunDirectory().getText().trim();

        return (configuration.isCompilationEnabled() != compilationEnabled ||
                !Comparing.equal(configuration.getCompilerOutputPath(), FileUtil.toSystemIndependentName(outputPath)) ||
                configuration.isCompilationUseGivenRunDirectory() != compileInGivenPath ||
                !Comparing.equal(configuration.getCompilationRunDirectory(), FileUtil.toSystemIndependentName(runDirectory)));
    }

    @Override
    public void apply() throws ConfigurationException {
        configuration.setIsCompilationEnabled(commonSettingsEditor.getEnableCompilationCheckbox().isSelected());
        configuration.setCompilerOutputPath(FileUtil.toSystemIndependentName(commonSettingsEditor.getProtobufCompilerOutputPathField().getText().trim()));
        configuration.setCompilationUseGivenRunDirectory(commonSettingsEditor.getProtobufCompilerRunInFixedDirectory().isSelected());
        configuration.setCompilationRunDirectory(FileUtil.toSystemIndependentName(commonSettingsEditor.getProtobufCompilerRunDirectory().getText().trim()));
    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {
        
    }

    public JCheckBox getEnableCompilationCheckbox() {
        return commonSettingsEditor.getEnableCompilationCheckbox();
    }

    public TextFieldWithBrowseButton getProtobufCompilerOutputPathField() {
        return commonSettingsEditor.getProtobufCompilerOutputPathField();
    }

    public JCheckBox getProtoCompilerRunInFixedDirectory() {
        return commonSettingsEditor.getProtobufCompilerRunInFixedDirectory();
    }

    public TextFieldWithBrowseButton getProtobufCompilerRunDirectory() {
        return commonSettingsEditor.getProtobufCompilerRunDirectory();
    }

}
