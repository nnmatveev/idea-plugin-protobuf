package protobuf.lang.psi.impl.declaration;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.declaration.PbEnumDef;
import protobuf.lang.psi.impl.auxiliary.PbNamedBlockHolderImpl;
import protobuf.lang.psi.utils.PbPsiUtil;

/**
 * author: Nikolay Matveev
 * Date: Mar 10, 2010
 */
public class PbEnumDefImpl extends PbNamedBlockHolderImpl implements PbEnumDef {
    public PbEnumDefImpl(ASTNode node){
        super(node);
    }

        @Override
    public void accept(ProtobufPsiElementVisitor visitor) {
        visitor.visitEnumDefinition(this);
    }

    @Override
    public PsiElement getNameElement() {        
        return PbPsiUtil.getChild(this,1,true,true,false);
    }
}
