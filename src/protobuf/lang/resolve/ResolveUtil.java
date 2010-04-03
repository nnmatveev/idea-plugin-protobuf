package protobuf.lang.resolve;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import protobuf.lang.psi.PbPsiEnums;
import protobuf.lang.psi.api.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 29, 2010
 */
public class ResolveUtil {

    private final static Logger LOG = Logger.getInstance(ResolveUtil.class.getName());

    public static PbResolveResult[] wrapInResolveResult(PsiElement element) {
        return new PbResolveResult[]{new PbResolveResult(element)};
    }

    public static PsiElement resolveInScopeByName(PbAssignable[] assignableElements, String refName) {        
        for (PbAssignable assignableElement : assignableElements) {
            assert assignableElement != null;
            assert assignableElement.getName() != null;
            if (assignableElement.getName().equals(refName)) {
                return assignableElement.getAim();
            }
        }
        return null;
    }

    /*public static PsiElement resolveInScopeByName(PbPsiScope scope, String refName) {
        PbAssignable[] assignableElements = scope.getElements();
        for (PbAssignable assignableElement : assignableElements) {
            assert assignableElement != null;            
            assert assignableElement.getName() != null;
            if (assignableElement.getName().equals(refName)) {
                return assignableElement.getAim();
            }
        }
        return null;
    } */

    public static PsiElement resolveInScopeByName(PbPsiScope scope, String refName, PbPsiEnums.ReferenceKind kind) {
        PbAssignable[] assignableElements = scope.getElementsInScope(kind);
        int i = 0;
        for (PbAssignable assignableElement : assignableElements) {
            assert assignableElement != null;
            LOG.info((i++) + ": assignable name " + assignableElement.getName());
            assert assignableElement.getName() != null;
            if (assignableElement.getName().equals(refName)) {
                return assignableElement.getAim();
            }
        }
        return null;
    }

    /*public static PsiElement resolveInScopeByName(PbPsiScopeHolder scopeHolder, String refName) {
        return resolveInScopeByName(scopeHolder.getScope(), refName);
    } */

}
