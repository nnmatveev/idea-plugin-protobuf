package protobuf.lang.completion;

import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import protobuf.lang.psi.utils.PbPsiUtil;

/**
 * author: Nikolay Matveev
 * Date: Apr 7, 2010
 */
public class PbVariantsProcessor implements PsiScopeProcessor {

    @Override
    public boolean execute(PsiElement psiElement, ResolveState resolveState) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> T getHint(Key<T> tKey) {
        return null;
    }

    @Override
    public void handleEvent(Event event, Object o) {

    }

    public PsiElement[] getVariants(){
        return PbPsiUtil.EMPTY_PSI_ARRAY;
    }
}
