package protobuf.lang.psi.api.member;

import protobuf.lang.psi.api.PbPsiElement;

/**
 * @author Nikolay Matveev
 * Date: Mar 12, 2010
 */
public interface PbOptionList extends PbPsiElement {

    public PbOptionAssignment[] getOptionAssigments();
}
