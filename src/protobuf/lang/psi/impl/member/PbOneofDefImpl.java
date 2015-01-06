package protobuf.lang.psi.impl.member;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import protobuf.lang.psi.PbPsiElementVisitor;
import protobuf.lang.psi.api.declaration.PbEnumDef;
import protobuf.lang.psi.api.member.PbOneofDef;
import protobuf.lang.psi.impl.auxiliary.PbNamedBlockHolderImpl;
import protobuf.lang.psi.utils.PbPsiUtil;

/**
 * Created by david on 1/6/2015.
 */
public class PbOneofDefImpl extends PbNamedBlockHolderImpl implements PbOneofDef {
    public PbOneofDefImpl(ASTNode node){
        super(node);
    }

    @Override
    public void accept(@NotNull PbPsiElementVisitor visitor) {
        visitor.visitOneofDefinition(this);
    }

    @Override
    public PsiElement getNameElement() {
        return PbPsiUtil.getChild(this, 1, true, true, false);
    }
}
