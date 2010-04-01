package protobuf.lang.psi.impl.definitions;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.definitions.PbExtendDef;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */

//todo: inspection that nested 'extend' is bad
public class PbExtendDefImpl extends PbPsiElementImpl implements PbExtendDef {
    public PbExtendDefImpl(ASTNode node){
        super(node);
    }

    public String toString(){
        return "extend definition";
    }
}
