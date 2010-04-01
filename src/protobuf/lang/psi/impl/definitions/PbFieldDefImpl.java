package protobuf.lang.psi.impl.definitions;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.definitions.PbFieldDef;
import static protobuf.lang.psi.api.members.PbFieldType.Type;

import protobuf.lang.psi.impl.PbPsiElementImpl;

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

    public String toString(){
        return "field definition";
    }

    //todo: complete
    public boolean isGroup() {
        return false;
    }

    //todo:complete
    public Label getLabel() {
        return null;
    }

    public Type getType() {
        return null;
    }

    public PsiElement setName(@NonNls String s) throws IncorrectOperationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
