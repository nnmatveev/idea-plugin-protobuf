package protobuf.lang.psi.impl.declaration;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import protobuf.lang.psi.PbPsiElementVisitor;
import protobuf.lang.psi.api.declaration.PbServiceDef;
import protobuf.lang.psi.impl.auxiliary.PbNamedBlockHolderImpl;
import protobuf.lang.psi.utils.PbPsiUtil;

/**
 * author: Nikolay Matveev
 */
public class PbServiceDefImpl extends PbNamedBlockHolderImpl implements PbServiceDef {
    public PbServiceDefImpl(ASTNode node) {
        super(node);
    }

    @Override
    public void accept(PbPsiElementVisitor visitor) {
        visitor.visitServiceDefinition(this);        
    }

    @Override
    public PsiElement getNameElement() {
        return PbPsiUtil.getChild(this,1,true,true,false);        
    }
}
