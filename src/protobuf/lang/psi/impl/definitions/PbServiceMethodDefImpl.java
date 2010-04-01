package protobuf.lang.psi.impl.definitions;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.definitions.PbServiceMethodDef;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 24, 2010
 */
public class PbServiceMethodDefImpl extends PbPsiElementImpl implements PbServiceMethodDef {
    public PbServiceMethodDefImpl(ASTNode node){
        super(node);
    }

    public String toString(){
        return "service method definition";
    }
}
