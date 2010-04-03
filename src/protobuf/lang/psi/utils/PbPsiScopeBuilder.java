package protobuf.lang.psi.utils;

import com.intellij.psi.PsiNamedElement;
import protobuf.lang.psi.api.PbAssignable;
import protobuf.lang.psi.api.PbPsiScope;
import protobuf.lang.psi.api.PbPsiScopeHolder;
import protobuf.lang.psi.api.definitions.PbMessageDef;
import protobuf.lang.psi.api.references.PbRef;

import java.util.ArrayList;

/**
 * author: Nikolay Matveev
 * Date: Mar 31, 2010
 */
public class PbPsiScopeBuilder {
    ArrayList<PbAssignable> elementsInScope = new ArrayList<PbAssignable>();

    public void append(PbAssignable elem) {
        elementsInScope.add(elem);
    }

    public void append(PbAssignable[] elems) {
        for (PbAssignable elem : elems) {
            elementsInScope.add(elem);
        }
    }

    public void append(PbPsiScope scope) {
        append(scope.getElementsInScope(PbRef.ReferenceKind.ANY));
    }

    public void append(PbPsiScope scope, PbRef.ReferenceKind kind) {
        append(scope.getElementsInScope(kind));
    }

    public void append(PbPsiScope[] scopes) {
        for (PbPsiScope scope : scopes) {
            append(scope.getElementsInScope(PbRef.ReferenceKind.ANY));
        }
    }

    public void append(PbPsiScope[] scopes, PbRef.ReferenceKind kind) {
        for (PbPsiScope scope : scopes) {
            append(scope, kind);
        }
    }

    public void extractAndAppend(PbPsiScopeHolder scopeHolder) {
            append(scopeHolder.getScope().getElementsInScope(PbRef.ReferenceKind.ANY));
    }

    public void extractAndAppend(PbPsiScopeHolder scopeHolder, PbRef.ReferenceKind kind) {        
            append(scopeHolder.getScope().getElementsInScope(kind));
    }

    /*public void extractAndAppend(PbPsiScopeHolder[] scopeHolders) {
        for (PbPsiScopeHolder scope : scopeHolders) {
            append(scope.getScope().getElementsInScope());
        }
    }*/

    public void extractAndAppend(PbPsiScopeHolder[] scopeHolders, PbRef.ReferenceKind kind) {
        for (PbPsiScopeHolder scope : scopeHolders) {
            append(scope.getScope().getElementsInScope(kind));
        }
    }

    public PbAssignable[] getElements() {
        return elementsInScope.toArray(new PbAssignable[elementsInScope.size()]);
    }  

    /*public PbPsiScope getScope() {
        return this;
    }*/
}
