package protobuf.lang.psi.impl.member;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import protobuf.lang.psi.PbPsiEnums;
import protobuf.lang.psi.PbPsiElementVisitor;
import protobuf.lang.psi.api.member.PbValue;
import protobuf.lang.psi.impl.PbPsiElementImpl;
import static protobuf.lang.PbElementTypes.*;

/**
 * author: Nikolay Matveev
 * Date: Apr 5, 2010
 */
public class PbValueImpl extends PbPsiElementImpl implements PbValue{
    public PbValueImpl(ASTNode node){
        super(node);
    }

    @Override
    public void accept(PbPsiElementVisitor visitor) {
        visitor.visitValue(this);
    }

    @Override
    public PbPsiEnums.ValueType getType() {
        if(findChildByType(NUM_INT) != null){
            return PbPsiEnums.ValueType.NUM_INT;
        }
        if(findChildByType(NUM_DOUBLE) != null){
            return PbPsiEnums.ValueType.NUM_DOUBLE;
        }
        if(findChildByType(STRING_LITERALS) != null){
            return PbPsiEnums.ValueType.STRING;
        }
        if(findChildByType(BOOL_VALUES) != null){
            return PbPsiEnums.ValueType.BOOL;
        }
        PsiElement ikChild = findChildByType(IK);
        if(ikChild != null){
            if(ikChild.getText().equals("inf") || ikChild.getText().equals("nan")){
                return PbPsiEnums.ValueType.NUM_DOUBLE;
            }
            return PbPsiEnums.ValueType.ENUM_CONSTANT;
        }
        return null;
    }
}
