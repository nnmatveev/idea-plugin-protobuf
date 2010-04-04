package protobuf.lang.psi.impl.auxiliary;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.auxiliary.PbBlockHolder;
import protobuf.lang.psi.api.blocks.PbBlock;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Apr 4, 2010
 */
public abstract class PbBlockHolderImpl extends PbPsiElementImpl implements PbBlockHolder {

    public PbBlockHolderImpl(ASTNode node) {
        super(node);
    }

    @Override
    public PbBlock getBlock() {
        return findChildByClass(PbBlock.class);
    }
}
