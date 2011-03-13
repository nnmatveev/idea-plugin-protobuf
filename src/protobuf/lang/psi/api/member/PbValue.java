package protobuf.lang.psi.api.member;

import protobuf.lang.psi.PbPsiEnums;
import protobuf.lang.psi.api.PbPsiElement;

/**
 * @author Nikolay Matveev
 * Date: Apr 5, 2010
 */
public interface PbValue extends PbPsiElement {

    PbPsiEnums.ValueType getType();
}
