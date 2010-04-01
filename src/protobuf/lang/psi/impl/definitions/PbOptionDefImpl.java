package protobuf.lang.psi.impl.definitions;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.definitions.PbOptionDef;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 11, 2010
 */
public class PbOptionDefImpl extends PbPsiElementImpl implements PbOptionDef {
    public PbOptionDefImpl(ASTNode node){
        super(node);
    }

    public String toString(){
        return "option definition";
    }
}

