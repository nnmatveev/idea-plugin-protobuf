package protobuf.lang.psi.impl.declaration;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.declaration.PbEnumConstantDef;
import protobuf.lang.psi.impl.PbPsiElementImpl;
import protobuf.lang.psi.impl.auxiliary.PbNamedElementImpl;
import protobuf.lang.psi.utils.PbPsiUtil;

/**
 * author: Nikolay Matveev
 * Date: Mar 10, 2010
 */
public class PbEnumConstantDefImpl extends PbNamedElementImpl implements PbEnumConstantDef {
    public PbEnumConstantDefImpl(ASTNode node){
        super(node);
    }

    @Override
    public void accept(ProtobufPsiElementVisitor visitor) {
        visitor.visitEnumConstantDefinition(this);
    }   

    @Override
    public PsiElement getNameElement() {
        return PbPsiUtil.getChild(this,0,true,true,false);
    }
}
