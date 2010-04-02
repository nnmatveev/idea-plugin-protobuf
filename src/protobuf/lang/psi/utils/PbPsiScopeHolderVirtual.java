package protobuf.lang.psi.utils;

import protobuf.lang.psi.api.PbPsiScope;
import protobuf.lang.psi.api.PbPsiScopeHolder;

/**
 * author: Nikolay Matveev
 * Date: Apr 2, 2010
 */
public class PbPsiScopeHolderVirtual implements PbPsiScopeHolder {
    private PbPsiScope myScope;

    public PbPsiScopeHolderVirtual(PbPsiScope scope) {
        myScope = scope;
    }

    @Override
    public PbPsiScope getScope() {
        return myScope;
    }
}
