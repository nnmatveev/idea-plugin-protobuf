package protobuf.lang.psi.api.auxiliary;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;

/**
 * @author Nikolay Matveev
 */

public interface PbNamedElement extends PsiNamedElement {
    PsiElement getNameElement();
}
