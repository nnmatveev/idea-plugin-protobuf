package protobuf.lang.psi.api;

import com.intellij.psi.PsiElement;
import protobuf.lang.psi.PbPsiElementVisitor;

/**
 * @author Nikolay Matveev
 * Date: Mar 9, 2010
 */
public interface PbPsiElement extends PsiElement {
    
    public void accept(PbPsiElementVisitor visitor);
}
