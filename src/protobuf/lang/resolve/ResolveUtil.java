package protobuf.lang.resolve;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import protobuf.lang.psi.api.PbAssignable;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.PbPsiElement;
import protobuf.lang.psi.api.PbPsiScope;

import java.util.ArrayList;

/**
 * author: Nikolay Matveev
 * Date: Mar 29, 2010
 */
public class ResolveUtil {
    public static PbResolveResult[] wrapInResolveResult(PsiElement element) {
        return new PbResolveResult[]{new PbResolveResult(element)};
    }

    public static PbResolveResult[] resolveInScopeByName(PbPsiScope scope, String refName) {
        PbAssignable[] assignableElements = scope.getElementsInScope();
        for (PbAssignable assignableElement : assignableElements) {
            //todo java.lang.NullPointerException
            if (assignableElement.getName().equals(refName)) {
                return ResolveUtil.wrapInResolveResult(assignableElement.getAim());
            }
        }
        return null;
    }
}
