package protobuf;

import com.intellij.facet.FacetTypeRegistry;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileTypeManager;
import org.jetbrains.annotations.NotNull;
import protobuf.facet.PbFacetType;
import protobuf.file.PbFileType;

/**
 * @author Nikolay Matveev
 */
public class PbLoader implements ApplicationComponent {

    private final static Logger LOG = Logger.getInstance(PbLoader.class.getName());

    public void initComponent() {
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
            template.setText(
                    "#parse(\"File Header.java\")\n" +
                    "#if ( $PACKAGE_NAME != \"\" )package ${PACKAGE_NAME};#end\n" +
                    "\n" +
                    "option java_package=\"${PACKAGE_NAME}\";"
            );
        }
    }
}
