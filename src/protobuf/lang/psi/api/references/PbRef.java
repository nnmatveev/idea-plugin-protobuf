package protobuf.lang.psi.api.references;

import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiQualifiedReference;
import com.intellij.psi.PsiReference;

/**
 * author: Nikolay Matveev
 * Date: Mar 30, 2010
 */
public interface PbRef extends PsiReference, PsiQualifiedReference {

    enum ReferenceKind {
        ANY,
        DIRECTORY,        
        PACKAGE,
        MESSAGE,
        MESSAGE_OR_ENUM,
        MESSAGE_OR_PACKAGE,
        MESSAGE_FIELD,
        EXTEND_FIELD,
        EXTEND_FIELD_INSIDE        
    }

    String getReferenceName();

    ReferenceKind getKind();
}
