package protobuf.lang.psi.impl.definitions;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.PbPsiScope;
import protobuf.lang.psi.api.blocks.PbMessageBlock;
import protobuf.lang.psi.api.definitions.PbFieldDef;
import protobuf.lang.psi.api.definitions.PbMessageDef;
import protobuf.lang.psi.impl.PbPsiElementImpl;
import protobuf.lang.psi.impl.members.PbNameImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 10, 2010
 */
public class PbMessageDefImpl extends PbPsiElementImpl implements PbMessageDef {

    public PbMessageDefImpl(ASTNode node) {
        super(node);
    }

    @Override
    public void accept(ProtobufPsiElementVisitor visitor) {
        visitor.visitMessageDefinition(this);
    }
    
    @Override
    public String toString() {
        return "message definition";
    }

    public PbFieldDef[] getFields() {
        return findChildrenByClass(PbFieldDefImpl.class);
    }

    public PsiElement setName(@NonNls String s) throws IncorrectOperationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getName(){
        return findChildByClass(PbNameImpl.class).getText();
    }

    @Override
    public PsiElement getAim() {
        return this;
    }

    @Override
    public PbPsiScope getScope() {
        return findChildByClass(PbMessageBlock.class);
    }
}
