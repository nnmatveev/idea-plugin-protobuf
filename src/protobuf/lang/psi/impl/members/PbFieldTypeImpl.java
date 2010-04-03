package protobuf.lang.psi.impl.members;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.members.PbFieldType;
import protobuf.lang.psi.api.references.PbRef;
import protobuf.lang.psi.impl.PbPsiElementImpl;
import static protobuf.lang.psi.PbPsiEnums.*;

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

    public FieldType getType() {
        if(findChildByClass(PbRef.class) != null){
            return FieldType.CUSTOM_TYPE;
        }
        return FieldType.BUILT_IN_TYPE;
    }
}
