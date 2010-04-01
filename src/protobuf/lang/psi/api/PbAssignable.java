package protobuf.lang.psi.api;

import com.intellij.psi.PsiElement;

/**
 * author: Nikolay Matveev
 * Date: Mar 31, 2010
 */
public interface PbAssignable{
    public String getName();

    public PsiElement getAim();
}
