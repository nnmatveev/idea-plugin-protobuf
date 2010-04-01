package protobuf.lang.psi.impl.blocks;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiNamedElement;
import protobuf.lang.psi.api.PbAssignable;
import protobuf.lang.psi.api.blocks.PbEnumBlock;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 29, 2010
 */
public class PbEnumBlockImpl  extends PbPsiElementImpl implements PbEnumBlock {
    public PbEnumBlockImpl(ASTNode node){
        super(node);
    }

    @Override
    public String toString() {
        return "Enum block";
    }

    @Override
    public PbAssignable[] getElementsInScope() {
        return findChildrenByClass(PbAssignable.class);
    }
}
