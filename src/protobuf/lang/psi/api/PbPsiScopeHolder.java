package protobuf.lang.psi.api;

import protobuf.lang.psi.api.references.PbRef;
import protobuf.lang.psi.impl.references.PbRefImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 31, 2010
 */
public interface PbPsiScopeHolder {
    PbPsiScope getScope();   
}
