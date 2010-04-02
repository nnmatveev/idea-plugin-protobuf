package protobuf.lang.resolve;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveResult;

/**
 * author: Nikolay Matveev
 * Date: Mar 29, 2010
 */
public class PbResolveResult implements ResolveResult {
    public static final PbResolveResult[] EMPTY_RESULT = new PbResolveResult[0];

    private PsiElement myElement;

    public PbResolveResult(PsiElement element){
        myElement = element;
    }
    @Override
    public PsiElement getElement() {
        return myElement;
    }

    @Override
    public boolean isValidResult() {
        return false;  //todo complete
    }
}
