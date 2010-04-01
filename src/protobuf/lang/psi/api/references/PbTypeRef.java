package protobuf.lang.psi.api.references;

import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiQualifiedReference;
import protobuf.lang.psi.api.PbPsiElement;

/**
 * author: Nikolay Matveev
 * Date: Mar 22, 2010
 */
public interface PbTypeRef extends PbRef, PbPsiElement, PsiQualifiedReference, PsiPolyVariantReference{
}
