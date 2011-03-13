package protobuf.lang.psi.impl.auxiliary;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.auxiliary.PbBlockHolder;
import protobuf.lang.psi.api.block.PbBlock;

/**
 * @author Nikolay Matveev
 */

public abstract class PbNamedBlockHolderImpl extends PbNamedElementImpl implements PbBlockHolder {

    protected PbNamedBlockHolderImpl(ASTNode node) {
        super(node);
    }

    @Override
    public PbBlock getBlock() {
        return findChildByClass(PbBlock.class);
    }
}
