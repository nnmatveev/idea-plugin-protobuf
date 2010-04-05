package protobuf.lang.psi.impl.definitions;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.blocks.PbBlock;
import protobuf.lang.psi.api.definitions.PbGroupDef;
import protobuf.lang.psi.impl.PbPsiElementImpl;
import protobuf.lang.psi.impl.members.PbNameImpl;

import static protobuf.lang.psi.PbPsiEnums.*;

/**
 * author: Nikolay Matveev
 * Date: Apr 3, 2010
 */
public class PbGroupDefImpl extends PbPsiElementImpl implements PbGroupDef {

    public PbGroupDefImpl(ASTNode node){
        super(node);
    }

    @Override
    public void accept(ProtobufPsiElementVisitor visitor) {
        super.accept(visitor);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String toString(){
        return "Group definition";
    }

    /*@Override
    public FieldType getType() {
        return FieldType.GROUP;
    } */

    @Override
    public String getName() {
        return findChildByClass(PbNameImpl.class).getText();
    }

    @Override
    public PbBlock getBlock() {
        return findChildByClass(PbBlock.class);
    }

    @Override
    public PsiElement setName(@NonNls String s) throws IncorrectOperationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
