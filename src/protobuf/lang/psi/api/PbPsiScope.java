package protobuf.lang.psi.api;

import com.intellij.psi.PsiNamedElement;

/**
 * author: Nikolay Matveev
 * Date: Mar 30, 2010
 */
public interface PbPsiScope {
    PbAssignable[] getElementsInScope();
}
