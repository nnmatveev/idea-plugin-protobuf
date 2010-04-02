package protobuf.lang.psi.impl.members;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.members.PbOptionAssigment;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public class PbOptionAssigmentImpl extends PbPsiElementImpl implements PbOptionAssigment {
    public PbOptionAssigmentImpl(ASTNode node){
        super(node);
    }

    public String toString(){
        return "Option assigment";
    }

}
