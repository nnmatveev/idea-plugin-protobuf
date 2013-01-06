package protobuf;

import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory;
import protobuf.file.PbProtoFileTemplateDescriptor;

/**
 * A file template provider for protobuf files.
 * @author Travis Cripps
 */
public class PbFileTemplateProvider implements FileTemplateGroupDescriptorFactory {

    public static final String PROTO_FILE_TEMPLATE_NAME = "ProtoFile.proto";

    @Override
    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
        final FileTemplateGroupDescriptor groupDescriptor = new FileTemplateGroupDescriptor("Protobuf", PbIcons.FILE_TYPE);
        groupDescriptor.addTemplate(new PbProtoFileTemplateDescriptor(PROTO_FILE_TEMPLATE_NAME, PbIcons.FILE_TYPE));
        return groupDescriptor;
    }

}
