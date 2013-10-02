package protobuf.lang.psi.api.reference;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiQualifiedReference;
import com.intellij.psi.PsiReference;
import protobuf.lang.psi.PbPsiEnums;

/**
 * @author Nikolay Matveev
 * Date: Mar 30, 2010
 */
public interface PbRef extends PsiReference, PsiQualifiedReference {

    String getReferenceName();

    PbPsiEnums.ReferenceKind getRefKind();

    PbPsiEnums.CompletionKind getCompletionKind();

    boolean isLastInChainReference();

    PsiElement getReferenceNameElement();

    PbRef getQualifierRef(); // Added for compatibility with API changes in Idea 12.
}
