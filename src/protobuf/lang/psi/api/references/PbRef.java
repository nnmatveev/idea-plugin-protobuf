package protobuf.lang.psi.api.references;

import com.intellij.psi.PsiQualifiedReference;
import com.intellij.psi.PsiReference;

/**
 * author: Nikolay Matveev
 * Date: Mar 30, 2010
 */
public interface PbRef extends PsiReference, PsiQualifiedReference {

    String getReferenceName();
}
