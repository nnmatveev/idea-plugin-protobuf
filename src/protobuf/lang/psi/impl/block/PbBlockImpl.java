package protobuf.lang.psi.impl.block;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import protobuf.lang.psi.api.block.PbBlock;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * @author Nikolay Matveev
 * Date: Apr 2, 2010
 */
public class PbBlockImpl extends PbPsiElementImpl implements PbBlock {

    private final static Logger LOG = Logger.getInstance(PbBlockImpl.class.getName());    

    public PbBlockImpl(ASTNode node) {
        super(node);
    }       
}
