package protobuf.lang.psi.impl.members;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.members.PbOptionName;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Apr 2, 2010
 */
public class PbOptionNameImpl extends PbPsiElementImpl implements PbOptionName {
    public PbOptionNameImpl(ASTNode node){
        super(node);
    }

    public String toString(){
        return "Options name";
    }
}
