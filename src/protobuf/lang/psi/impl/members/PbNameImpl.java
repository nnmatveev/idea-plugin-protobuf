package protobuf.lang.psi.impl.members;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.members.PbName;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 29, 2010
 */
public class PbNameImpl extends PbPsiElementImpl implements PbName {
    public PbNameImpl(ASTNode node){
        super(node);
    }

    @Override
    public String toString() {
        return "Name";
    }
}
