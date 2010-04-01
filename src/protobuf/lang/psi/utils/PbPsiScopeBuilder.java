package protobuf.lang.psi.utils;

import com.intellij.psi.PsiNamedElement;
import protobuf.lang.psi.api.PbAssignable;
import protobuf.lang.psi.api.PbPsiScope;
import protobuf.lang.psi.api.PbPsiScopeHolder;

import java.util.ArrayList;

/**
 * author: Nikolay Matveev
 * Date: Mar 31, 2010
 */
public class PbPsiScopeBuilder implements PbPsiScope {
    ArrayList<PbAssignable> elementsInScope = new ArrayList<PbAssignable>();

    public void append(PbAssignable elem) {
        elementsInScope.add(elem);
    }

    public void append(PbAssignable[] elems) {
        for (PbAssignable elem : elems) {
            elementsInScope.add(elem);
        }
    }

    public void append(PbPsiScope[] scopes) {
        for (PbPsiScope scope : scopes) {
            append(scope.getElementsInScope());
        }
    }

    public void extractAndAppend(PbPsiScopeHolder[] scopeHolders) {
        for (PbPsiScopeHolder scope : scopeHolders) {
            append(scope.getScope().getElementsInScope());
        }
    }

    @Override
    public PbAssignable[] getElementsInScope() {
        return elementsInScope.toArray(new PbAssignable[elementsInScope.size()]);
    }

    public PbPsiScope getScope() {
        return this;
    }
}
