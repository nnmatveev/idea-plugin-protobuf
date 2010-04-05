package protobuf.lang.psi.api.members;

import protobuf.lang.psi.api.PbPsiElement;
import protobuf.lang.psi.api.references.PbRef;

import static protobuf.lang.psi.PbPsiEnums.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public interface PbFieldType extends PbPsiElement {

    FieldType getType();

    PbRef getTypeRef();

}
