package protobuf.lang.psi.impl.members;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.members.PbValue;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Apr 5, 2010
 */
public class PbValueImpl extends PbPsiElementImpl implements PbValue{
    public PbValueImpl(ASTNode node){
        super(node);
    }
}
