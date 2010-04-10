package protobuf.lang.psi.api.declaration;

import protobuf.lang.psi.api.PbPsiElement;
import protobuf.lang.psi.api.auxiliary.PbBlockHolder;
import protobuf.lang.psi.api.reference.PbRef;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public interface PbExtendDef extends PbPsiElement, PbBlockHolder {

    public PbRef getTypeRef();
}
