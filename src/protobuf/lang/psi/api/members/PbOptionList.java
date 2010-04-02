package protobuf.lang.psi.api.members;

import protobuf.lang.psi.api.PbPsiElement;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public interface PbOptionList extends PbPsiElement {

    public PbOptionAssigment[] getOptions();
}
