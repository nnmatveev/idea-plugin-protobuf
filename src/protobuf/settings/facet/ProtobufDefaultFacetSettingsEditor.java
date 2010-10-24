package protobuf.settings.facet;

import com.intellij.facet.ui.DefaultFacetSettingsEditor;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import protobuf.compiler.PbCompiler;
import protobuf.file.ProtobufFileType;

import javax.swing.*;

/**
 * An editor for the default settings for newly configured Protobuf facets.
 * @author Travis Cripps
 */
public class ProtobufDefaultFacetSettingsEditor extends DefaultFacetSettingsEditor {

    private JPanel settingsPanel;

    private ProtobufFacetCommonSettingsEditor commonSettingsEditor;

    Project project;
    ProtobufFacetConfiguration configuration;

    public ProtobufDefaultFacetSettingsEditor(Project project, ProtobufFacetConfiguration configuration) {
        this.project = project;
        this.configuration = configuration;

        commonSettingsEditor.getProtobufCompilerOutputPathField().addBrowseFolderListener(project, new CompilerOutputBrowseFolderActionListener(project, null, commonSettingsEditor.getProtobufCompilerOutputPathField()));
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
        return configuration.isCompilationEnabled() != commonSettingsEditor.getEnableCompilationCheckbox().isSelected() ||
                !configuration.getCompilerOutputPath().equals(commonSettingsEditor.getProtobufCompilerOutputPathField().getText());
    }

    @Override
    public void apply() throws ConfigurationException {
        boolean isCompilationCheckboxEnabled = commonSettingsEditor.getEnableCompilationCheckbox().isSelected();
        configuration.setIsCompilationEnabled(isCompilationCheckboxEnabled);
        if (isCompilationCheckboxEnabled && !configuration.isCompilationEnabled()) {
            CompilerManager compilerManager = CompilerManager.getInstance(project);
            compilerManager.addCompilableFileType(ProtobufFileType.PROTOBUF_FILE_TYPE);
            CompilerManager.getInstance(project).addCompiler(new PbCompiler(project));
        } else if (!isCompilationCheckboxEnabled && configuration.isCompilationEnabled()) {
            CompilerManager compilerManager = CompilerManager.getInstance(project);
            compilerManager.removeCompilableFileType(ProtobufFileType.PROTOBUF_FILE_TYPE);
            for (PbCompiler compiler : compilerManager.getCompilers(PbCompiler.class)) {
                compilerManager.removeCompiler(compiler);
            }
        }

        configuration.setCompilerOutputPath(commonSettingsEditor.getProtobufCompilerOutputPathField().getText().trim());
    }

    @Override
    public void reset() {
        commonSettingsEditor.getEnableCompilationCheckbox().setSelected(configuration.isCompilationEnabled());
        commonSettingsEditor.getProtobufCompilerOutputPathField().setText(configuration.getCompilerOutputPath());
    }

    @Override
    public void disposeUIResources() {
    }

}
