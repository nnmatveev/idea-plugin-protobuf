package protobuf.lang.psi.api;

import protobuf.lang.psi.PbPsiEnums;

/**
 * author: Nikolay Matveev
 * Date: Mar 30, 2010
 */
public interface PbPsiScope {        

    PbAssignable[] getElementsInScope(PbPsiEnums.ReferenceKind kind);
}
