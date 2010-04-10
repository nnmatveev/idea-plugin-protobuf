package protobuf.lang.psi.impl.declaration;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.declaration.PbGroupDef;
import protobuf.lang.psi.impl.auxiliary.PbNamedBlockHolderImpl;
import protobuf.lang.psi.utils.PbPsiUtil;

/**
 * author: Nikolay Matveev 
 */
public class PbGroupDefImpl extends PbNamedBlockHolderImpl implements PbGroupDef {

    public PbGroupDefImpl(ASTNode node) {
        super(node);
    }

    @Override
    public void accept(ProtobufPsiElementVisitor visitor) {
        visitor.visitGroupDefinition(this);        
    }    

    @Override
    public String getFieldName() {
        String name = getName();
        if (name != null) {
            return name.toLowerCase();
        }
        return null;
    }

    @Override
    public PsiElement getNameElement() {
        return PbPsiUtil.getChild(this,2,true,true,false);
    }
}
