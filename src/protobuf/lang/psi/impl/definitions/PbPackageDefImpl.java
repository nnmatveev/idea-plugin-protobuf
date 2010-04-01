package protobuf.lang.psi.impl.definitions;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.references.PbPackageRef;
import protobuf.lang.psi.api.references.PbRef;
import protobuf.lang.psi.api.references.PbTypeRef;
import protobuf.lang.psi.api.definitions.PbPackageDef;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 10, 2010
 */
public class PbPackageDefImpl extends PbPsiElementImpl implements PbPackageDef {
    public PbPackageDefImpl(ASTNode node){
        super(node);
    }

    public String toString(){
        return "package definition";
    }

    @Override
    public String getPackageName() {
        final PbPackageRef packageRef = findChildByClass(PbPackageRef.class);
        if(packageRef == null){
            return "";
        }        
        return packageRef.getText();
    }
}
