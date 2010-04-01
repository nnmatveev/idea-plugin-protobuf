package protobuf.lang.psi.impl.references;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import protobuf.lang.ProtobufTokenTypes;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 30, 2010
 */
public abstract class PbRefImpl extends PbPsiElementImpl {
    public PbRefImpl(ASTNode node) {
        super(node);
    }

    public PsiReference getReference() {
        return (PsiReference) this;
    }

    public boolean isSoft() {
        return false;
    }

    public String getReferenceName() {
        PsiElement psi = findChildByType(ProtobufTokenTypes.IK);
        return psi != null ? psi.getText() : null;
    }

    public PsiElement getElement() {
        return this;
    }

    public Object[] getVariants() {
        return new Object[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PsiElement handleElementRename(String s) throws IncorrectOperationException {
        throw new IncorrectOperationException();
    }

    public PsiElement bindToElement(@NotNull PsiElement psiElement) throws IncorrectOperationException {
        throw new IncorrectOperationException();
    }   
}
