package protobuf.lang.psi.api.member;

import protobuf.lang.psi.PbPsiEnums;
import protobuf.lang.psi.api.PbPsiElement;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public interface PbOptionAssignment extends PbPsiElement {

    PbPsiEnums.OptionType getType();

    String getOptionName();

    String getOptionValue();
}
