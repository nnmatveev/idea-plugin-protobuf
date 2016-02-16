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
import com.intellij.openapi.util.text.StringUtil;
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
        commonSettingsEditor.getGenerateNanoProtoCheckBox().setSelected(configuration.isGenerateNanoProto());
        commonSettingsEditor.getProtobufCompilerOutputPathField().setText(configuration.getCompilerOutputPath());
        commonSettingsEditor.getProtobufCompilerOutputPathField().addBrowseFolderListener(project, new CompilerOutputBrowseFolderActionListener(project, module, commonSettingsEditor.getProtobufCompilerOutputPathField()));
        commonSettingsEditor.getProtobufAdditionalProtoPaths().setText(configuration.getAdditionalProtoPaths());
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
        boolean generateNanoProto = commonSettingsEditor.getGenerateNanoProtoCheckBox().isSelected();
        String outputPath = commonSettingsEditor.getProtobufCompilerOutputPathField().getText().trim();
        String additionalProtoPaths = commonSettingsEditor.getProtobufAdditionalProtoPaths().getText().trim();

        return ((configuration.isCompilationEnabled() != compilationEnabled) ||
            (configuration.isGenerateNanoProto() != generateNanoProto) ||
            (!Comparing.equal(configuration.getCompilerOutputPath(), FileUtil.toSystemIndependentName(outputPath))) ||
            (!Comparing.equal(configuration.getAdditionalProtoPaths(), toSystemIndependentPaths(additionalProtoPaths))));
    }

    @Override
    public void apply() throws ConfigurationException {
        configuration.setIsCompilationEnabled(commonSettingsEditor.getEnableCompilationCheckbox().isSelected());
        configuration.setGenerateNanoProto(commonSettingsEditor.getGenerateNanoProtoCheckBox().isSelected());
        configuration.setCompilerOutputPath(FileUtil.toSystemIndependentName(commonSettingsEditor.getProtobufCompilerOutputPathField().getText().trim()));
        configuration.setAdditionalProtoPaths(toSystemIndependentPaths(commonSettingsEditor.getProtobufAdditionalProtoPaths().getText()));
    }

    private String toSystemIndependentPaths(String paths) {
        String[] splitPaths = paths.trim().split(";");
        for (int i = 0; i < splitPaths.length; i++) {
            splitPaths[i] = FileUtil.toSystemIndependentName(splitPaths[i]);
        }
        return StringUtil.join(splitPaths, ";");
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

    public JCheckBox getGenerateNanoProtoCheckbox() {
        return commonSettingsEditor.getGenerateNanoProtoCheckBox();
    }

    public TextFieldWithBrowseButton getProtobufCompilerOutputPathField() {
        return commonSettingsEditor.getProtobufCompilerOutputPathField();
    }

    public JTextField getProtobufAdditionalProtoPathsField() {
        return commonSettingsEditor.getProtobufAdditionalProtoPaths();
    }

}
