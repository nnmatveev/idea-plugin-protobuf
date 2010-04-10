package protobuf.lang.psi.impl.member;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.member.PbOptionRefSeq;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev 
 */
public class PbOptionRefSeqImpl extends PbPsiElementImpl implements PbOptionRefSeq {
    public PbOptionRefSeqImpl(ASTNode node){        
        super(node);
    }
}
