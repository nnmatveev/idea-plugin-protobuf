package protobuf.file;

import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;

/**
 * A file template descriptor for the ProtoFile.proto file template.
 * @author Travis Cripps
 */
public class PbProtoFileTemplateDescriptor extends FileTemplateDescriptor {

    public PbProtoFileTemplateDescriptor(@NonNls String fileName) {
        super(fileName);
    }

    public PbProtoFileTemplateDescriptor(@NonNls String fileName, Icon icon) {
        super(fileName, icon);
    }

    @Override
    public String getDisplayName() {
        return "Protobuf file";
    }

}
