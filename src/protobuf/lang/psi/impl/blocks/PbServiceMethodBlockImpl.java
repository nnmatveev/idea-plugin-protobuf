package protobuf.lang.psi.impl.blocks;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.blocks.PbServiceMethodBlock;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 29, 2010
 */
public class PbServiceMethodBlockImpl extends PbPsiElementImpl implements PbServiceMethodBlock {
    public PbServiceMethodBlockImpl(ASTNode node){
        super(node);
    }

    @Override
    public String toString() {
        return "Service method block";
    }
}
