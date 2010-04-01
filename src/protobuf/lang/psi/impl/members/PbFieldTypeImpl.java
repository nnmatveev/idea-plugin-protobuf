package protobuf.lang.psi.impl.members;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.members.PbFieldType;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 11, 2010
 */
public class PbFieldTypeImpl extends PbPsiElementImpl implements PbFieldType {
    public PbFieldTypeImpl(ASTNode node){
        super(node);
    }

    public String toString(){
        return "field type";
    }

    public Type getType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
