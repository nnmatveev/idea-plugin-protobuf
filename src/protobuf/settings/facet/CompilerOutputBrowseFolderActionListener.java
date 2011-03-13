package protobuf.settings.facet;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.ui.TextComponentAccessor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import protobuf.PbBundle;

import javax.swing.*;

/**
* A browse folder action listener used for setting the output path of the Protobuf compiler.
 * @author Travis Cripps
*/
class CompilerOutputBrowseFolderActionListener extends ComponentWithBrowseButton.BrowseFolderActionListener<JTextField> {

    private final Project project;
    private final Module module;

    public CompilerOutputBrowseFolderActionListener(final Project project, final Module module, final TextFieldWithBrowseButton textField) {
        super(null, PbBundle.message("file.chooser.description.select.output.directory.for.files.generated.by.protobuf.compiler"), textField,
                project, FileChooserDescriptorFactory.createSingleFolderDescriptor(), TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT);
        this.project = project;
        this.module = module;
    }

    /**
     * Gets the current module's content root folder as the starting point of the browse action.
     * @return the module's content root folder
     */
    @Override
    protected VirtualFile getInitialFile() {
        if (StringUtil.isEmpty(getComponentText())) {
            VirtualFile[] roots;
            if (module != null) {
                roots = ModuleRootManager.getInstance(this.module).getContentRoots();
            } else { // Fall back to the project root when the module is not available.
                roots = ProjectRootManager.getInstance(this.project).getContentRoots();
            }
            if (roots.length > 0) {
                return roots[0];
            }
        }
        return super.getInitialFile();
    }

}
