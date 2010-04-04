package protobuf.lang.psi.impl.definitions;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.definitions.PbFieldDef;
import static protobuf.lang.psi.PbPsiEnums.*;
import static protobuf.lang.ProtobufTokenTypes.*;

import protobuf.lang.psi.api.members.PbFieldType;
import protobuf.lang.psi.impl.PbPsiElementImpl;
import protobuf.lang.psi.impl.members.PbFieldTypeImpl;
import protobuf.lang.psi.impl.members.PbNameImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public class PbFieldDefImpl extends PbPsiElementImpl implements PbFieldDef {
    public PbFieldDefImpl(ASTNode node){
        super(node);
    }

    @Override
    public void accept(ProtobufPsiElementVisitor visitor) {
        visitor.visitFieldDefinition(this);
    }

    @Override
    public String toString(){
        return "field definition";
    }
    
    public FieldLabel getLabel() {
        PsiElement firstChild = getFirstChild();
        IElementType firstChildType = firstChild.getNode().getElementType();
        if(firstChildType.equals(OPTIONAL)){
            return FieldLabel.OPTIONAL;
        } else if (firstChildType.equals(REPEATED)){
            return FieldLabel.REPEATED;
        } else {
            return FieldLabel.REQUIRED;
        }
    }

    public FieldType getType() {
        PbFieldType fieldType = findChildByClass(PbFieldType.class);
        return fieldType.getType();
    }

    public PsiElement setName(@NonNls String s) throws IncorrectOperationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }   

    @Override
    public String getName() {
        return findChildByClass(PbNameImpl.class).getText();
    }
}
