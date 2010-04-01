package protobuf.lang.psi.impl.definitions;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.definitions.PbExtensionsDef;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public class PbExtensionsDefImpl extends PbPsiElementImpl implements PbExtensionsDef {
    public PbExtensionsDefImpl(ASTNode node){
        super(node);
    }

    public String toString(){
        return "extensions definition";
    }
}
