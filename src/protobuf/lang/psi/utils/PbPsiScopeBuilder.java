package protobuf.lang.psi.utils;

import com.intellij.psi.PsiNamedElement;
import protobuf.lang.psi.api.PbAssignable;
import protobuf.lang.psi.api.PbPsiScope;
import protobuf.lang.psi.api.PbPsiScopeHolder;
import protobuf.lang.psi.api.references.PbRef;

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

    public void append(PbPsiScope scope) {
        append(scope.getElementsInScope());
    }

    public void extractAndAppend(PbPsiScopeHolder[] scopeHolders) {
        for (PbPsiScopeHolder scope : scopeHolders) {
            append(scope.getScope().getElementsInScope());
        }
    }

    public void append(PbPsiScope scope, PbRef.ReferenceKind kind) {
        /*
        append(scope.getElementsInScope());
        *
    }

    public void append(PbPsiScope[] scopes) {
        for (PbPsiScope scope : scopes) {
            append(scope.getElementsInScope());
        }
    }

    public void append(PbPsiScope[] scopes, PbRef.ReferenceKind kind) {
        /*for (PbPsiScope scope : scopes) {
            append(scope, kind);
        } */
    }

    public void filter(Class...classes){
        for(PbAssignable assignable: elementsInScope){
            for(Class filterClass : classes){
                if(PbAssignable.class.equals(filterClass)){
                    elementsInScope.remove(assignable);
                }
            }
        }
    }



    @Override
    public PbAssignable[] getElementsInScope() {
        return elementsInScope.toArray(new PbAssignable[elementsInScope.size()]);
    }

    @Override    
    public PbAssignable[] getElementsInScope(PbRef.ReferenceKind kind) {
        for(PbAssignable element : elementsInScope){
            
        }
        return new PbAssignable[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PbPsiScope getScope() {
        return this;
    }
}
