package protobuf;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * @author Nikolay Matveev
 */
public class PbLoader implements ApplicationComponent {

    private final static Logger LOG = Logger.getInstance(PbLoader.class.getName());

    public void initComponent() {
        
    }

    public void disposeComponent() {

    }

    @NotNull
    public String getComponentName() {
        return "protobuf.support.loader";
    }

}
