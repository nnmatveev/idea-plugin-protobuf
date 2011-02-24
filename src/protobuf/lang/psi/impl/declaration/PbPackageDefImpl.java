package protobuf.lang.psi.impl.declaration;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Computable;
import protobuf.lang.psi.api.declaration.PbPackageDef;
import protobuf.lang.psi.api.reference.PbRef;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 10, 2010
 */
public class PbPackageDefImpl extends PbPsiElementImpl implements PbPackageDef {
    
    public PbPackageDefImpl(ASTNode node){
        super(node);
    }

    // Note that this method gets its information from inspecting the Psi element tree, so must use the runReadAction and
    // Computable because it's not run from the normal thread that deals with them.
    @Override
    public String getPackageName() {
        final PbRef packageRef = ApplicationManager.getApplication().runReadAction(new Computable<PbRef>() {
            public PbRef compute() {
                return findChildByClass(PbRef.class);
            }
        });
        if(packageRef == null){
            return "";
        }        
        return packageRef.getText();
    }
}
