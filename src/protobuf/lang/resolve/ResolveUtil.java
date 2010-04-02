package protobuf.lang.resolve;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.tree.TokenSet;
import protobuf.lang.psi.api.*;
import protobuf.lang.psi.api.definitions.PbEnumDef;
import protobuf.lang.psi.api.definitions.PbMessageDef;
import protobuf.lang.psi.api.references.PbRef;
import static protobuf.lang.ProtobufElementTypes.*;

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
            assert assignableElement != null;
            assert assignableElement.getName() != null;
            if (assignableElement.getName().equals(refName)) {
                return ResolveUtil.wrapInResolveResult(assignableElement.getAim());
            }
        }
        return PbResolveResult.EMPTY_RESULT;
    }

    public static PbResolveResult[] resolveInScopeByName(PbPsiScopeHolder scopeHolder, String refName) {
        return resolveInScopeByName(scopeHolder.getScope(),refName);
    }
}
