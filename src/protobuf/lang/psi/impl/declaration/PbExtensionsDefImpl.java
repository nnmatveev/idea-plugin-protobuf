package protobuf.lang.psi.impl.declaration;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.declaration.PbExtensionsDef;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 */
public class PbExtensionsDefImpl extends PbPsiElementImpl implements PbExtensionsDef {
    public PbExtensionsDefImpl(ASTNode node){
        super(node);
    }
}
