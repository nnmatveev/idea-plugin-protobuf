package protobuf.lang.psi.impl.blocks;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiNamedElement;
import protobuf.lang.psi.api.PbAssignable;
import protobuf.lang.psi.api.blocks.PbMessageBlock;
import protobuf.lang.psi.api.definitions.PbMessageDef;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 29, 2010
 */
public class PbMessageBlockImpl extends PbPsiElementImpl implements PbMessageBlock {

    private static PbMessageDef[] EMPTY_MESSAGE_DEFS = new PbMessageDef[0];
    
    public PbMessageBlockImpl(ASTNode node){
        super(node);
    }

    @Override
    public String toString() {
        return "Message block";
    }

    @Override
    public PbAssignable[] getElementsInScope() {
        return findChildrenByClass(PbAssignable.class);
    }
}