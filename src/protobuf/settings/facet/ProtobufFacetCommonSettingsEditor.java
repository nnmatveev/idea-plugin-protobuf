package protobuf.settings.facet;

import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.util.ui.ThreeStateCheckBox;

import javax.swing.*;

/**
 * A panel for settings that are common across all of the Protobuf facet configuration editors.
 * @author Travis Cripps
 */
public class ProtobufFacetCommonSettingsEditor extends JComponent {

    private JPanel mainPanel;
    private ThreeStateCheckBox enableCompilationCheckBox;
    private TextFieldWithBrowseButton protobufCompilerOutputPath;
    private JTextField protobufAdditionalProtoPaths;

    public ProtobufFacetCommonSettingsEditor() {
        super();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public ThreeStateCheckBox getEnableCompilationCheckbox() {
        return enableCompilationCheckBox;
    }

    public TextFieldWithBrowseButton getProtobufCompilerOutputPathField() {
        return protobufCompilerOutputPath;
    }

    public JTextField getProtobufAdditionalProtoPaths() {
        return protobufAdditionalProtoPaths;
    }

}
