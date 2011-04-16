package protobuf.lang.psi.impl.declaration;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import protobuf.lang.psi.PbPsiElementVisitor;
import protobuf.lang.psi.api.declaration.PbGroupDef;
import protobuf.lang.psi.impl.auxiliary.PbNamedBlockHolderImpl;
import protobuf.lang.psi.utils.PbPsiUtil;

/**
 * @author Nikolay Matveev
 */
public class PbGroupDefImpl extends PbNamedBlockHolderImpl implements PbGroupDef {

    public PbGroupDefImpl(ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull PbPsiElementVisitor visitor) {
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
