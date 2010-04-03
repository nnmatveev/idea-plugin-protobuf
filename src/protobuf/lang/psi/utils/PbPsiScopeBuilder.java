package protobuf.lang.psi.utils;

import protobuf.lang.psi.PbPsiEnums;
import protobuf.lang.psi.api.PbAssignable;
import protobuf.lang.psi.api.PbPsiScope;
import protobuf.lang.psi.api.PbPsiScopeHolder;

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

    public void append(PbPsiScope scope, PbPsiEnums.ReferenceKind kind) {
        append(scope.getElementsInScope(kind));
    }

    public void append(PbPsiScope[] scopes, PbPsiEnums.ReferenceKind kind) {
        for (PbPsiScope scope : scopes) {
            append(scope, kind);
        }
    }

    public void extractAndAppend(PbPsiScopeHolder scopeHolder, PbPsiEnums.ReferenceKind kind) {
            append(scopeHolder.getScope().getElementsInScope(kind));
    }

    /*public void extractAndAppend(PbPsiScopeHolder[] scopeHolders) {
        for (PbPsiScopeHolder scope : scopeHolders) {
            append(scope.getScope().getElementsInScope());
        }
    }*/

    public void extractAndAppend(PbPsiScopeHolder[] scopeHolders, PbPsiEnums.ReferenceKind kind) {
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
