package protobuf.completion;

import com.intellij.psi.PsiElement;
import protobuf.lang.psi.api.reference.PbRef;
import protobuf.lang.psi.utils.PbPsiUtil;

/**
 * author: Nikolay Matveev
 */

public abstract class PbCompletionUtil {

    public static PsiElement[] getVariantsInScope(final PsiElement scope, final PbRef ref){
        
        return PbPsiUtil.EMPTY_PSI_ARRAY;
    }
}
