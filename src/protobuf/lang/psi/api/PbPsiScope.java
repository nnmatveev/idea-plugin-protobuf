package protobuf.lang.psi.api;

import com.intellij.psi.PsiNamedElement;
import protobuf.lang.psi.api.references.PbRef;

/**
 * author: Nikolay Matveev
 * Date: Mar 30, 2010
 */
public interface PbPsiScope {
    
    //PbAssignable[] getElementsInScope();

    PbAssignable[] getElementsInScope(PbRef.ReferenceKind kind);
}
