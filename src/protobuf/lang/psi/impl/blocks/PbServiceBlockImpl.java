package protobuf.lang.psi.impl.blocks;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.blocks.PbServiceBlock;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 29, 2010
 */
public class PbServiceBlockImpl  extends PbPsiElementImpl implements PbServiceBlock {
    public PbServiceBlockImpl(ASTNode node){
        super(node);
    }

    @Override
    public String toString() {
        return "Service block";
    }
}