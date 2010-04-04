package protobuf.lang.psi.impl.members;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.members.PbOptionRefSeq;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Apr 4, 2010
 */
public class PbOptionRefSeqImpl extends PbPsiElementImpl implements PbOptionRefSeq {
    public PbOptionRefSeqImpl(ASTNode node){        
        super(node);
    }
}
