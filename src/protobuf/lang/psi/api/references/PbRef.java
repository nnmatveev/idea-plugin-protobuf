package protobuf.lang.psi.api.references;

import com.intellij.psi.PsiQualifiedReference;
import com.intellij.psi.PsiReference;
import protobuf.lang.psi.PbPsiEnums;

/**
 * author: Nikolay Matveev
 * Date: Mar 30, 2010
 */
public interface PbRef extends PsiReference, PsiQualifiedReference {

    String getReferenceName();

    PbPsiEnums.ReferenceKind getRefKind();

    PbPsiEnums.ReferenceKind getCompletionKind();

    boolean isLeafReference();
}
