package protobuf;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStreamReader;

/**            
 * author: Nikolay Matveev
 * Date: Mar 5, 2010
 */
public class ProtobufLoader implements ApplicationComponent {

    private final static Logger LOG = Logger.getInstance(ProtobufLoader.class.getName());

    public void initComponent() {
        LOG.info("ProtobufLoader inited");
        registerTemplates();
    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        return "protobuf.support.loader";
    }

    private void registerTemplates() {
        FileTemplateManager fileTemplateManager = FileTemplateManager.getInstance();
        FileTemplate template = fileTemplateManager.getTemplate("Proto File");
        if(template == null){
            template = fileTemplateManager.addTemplate("Proto File", "proto");            
            template.setText("#parse(\"File Header.java\")\n #if ( $PACKAGE_NAME != \"\" )package ${PACKAGE_NAME} ;#end");
        }
    }
}
