package protobuf.lang.resolve;

import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import protobuf.lang.psi.api.reference.PbRef;

/**
 * @author Nikolay Matveev
 * Date: Apr 4, 2010
 */
public class PbResolveProcessor implements PsiScopeProcessor {

    PbRef myRef;

    public PbResolveProcessor(PbRef ref) {
        myRef = ref;
    }

    @Override
    public boolean execute(PsiElement psiElement, ResolveState resolveState) {
        
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> T getHint(Key<T> tKey) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void handleEvent(Event event, Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
