package protobuf.lang.psi.api.auxiliary;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import protobuf.lang.psi.api.PbPsiElement;

/**
 * @author Nikolay Matveev
 */

public interface PbNamedElement extends PbPsiElement, PsiNamedElement {

    PsiElement getNameElement();

}
