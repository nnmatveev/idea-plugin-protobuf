package protobuf;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import org.jetbrains.annotations.NotNull;
import protobuf.file.ProtobufFileType;

/**            
 * author: Nikolay Matveev
 */
public class ProtobufLoader implements ApplicationComponent {

    private final static Logger LOG = Logger.getInstance(ProtobufLoader.class.getName());

    public void initComponent() {
        registerFileTypes();
        registerTemplates();
    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        return "protobuf.support.loader";
    }

    private void registerFileTypes() {
        /*FileType[] registeredFileTypes = FileTypeManager.getInstance().getRegisteredFileTypes();
        for(FileType fileType : registeredFileTypes){
            if(fileType.equals(ProtobufFileType.PROTOBUF_FILE_TYPE)){
                return;
            }
        } */
        FileTypeManager.getInstance().registerFileType(ProtobufFileType.PROTOBUF_FILE_TYPE,ProtobufFileType.DEFAULT_ASSOCIATED_EXTENSIONS);
    }

    private void registerTemplates() {
        FileTemplateManager fileTemplateManager = FileTemplateManager.getInstance();
        FileTemplate template = fileTemplateManager.getTemplate("Proto File");
        if(template == null){
            template = fileTemplateManager.addTemplate("Proto File", "proto");                        
            template.setText(
                    "#parse(\"File Header.java\")\n" +
                    "#if ( $PACKAGE_NAME != \"\" )package ${PACKAGE_NAME};#end\n" +
                    "\n" +
                    "option java_package=\"${PACKAGE_NAME}\";"
            );
        }
    }
}
