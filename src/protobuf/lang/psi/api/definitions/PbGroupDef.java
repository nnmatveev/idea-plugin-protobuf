package protobuf.lang.psi.api.definitions;

import protobuf.lang.psi.api.PbAssignable;
import protobuf.lang.psi.api.PbPsiScopeHolder;

/**
 * author: Nikolay Matveev
 * Date: Apr 3, 2010
 */

//todo [low] it is not best idea to extends PbMessageDef
public interface PbGroupDef extends PbFieldDef, PbMessageDef, PbPsiScopeHolder {
}
