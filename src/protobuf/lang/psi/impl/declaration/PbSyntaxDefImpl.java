package protobuf.lang.psi.impl.declaration;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import protobuf.lang.PbElementTypes;
import protobuf.lang.psi.PbPsiElementVisitor;
import protobuf.lang.psi.api.declaration.PbSyntaxDef;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * @author Travis Cripps
 */
public class PbSyntaxDefImpl extends PbPsiElementImpl implements PbSyntaxDef {

    public PbSyntaxDefImpl(ASTNode node){
        super(node);
    }

    @Override
    public void accept(@NotNull PbPsiElementVisitor visitor) {
        visitor.visitSyntaxDefinition(this);
    }

    @Override
    public String getSyntaxLevel() {
        String value = null;
        PsiElement val = this.findChildByType(PbElementTypes.VALUE);
        if (val != null) {
            value = StringUtil.stripQuotesAroundValue(val.getText().trim());
        }
        return value;
    }

}
