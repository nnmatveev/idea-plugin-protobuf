package protobuf.lang.psi.api.definitions;

import protobuf.lang.psi.api.auxiliary.PbBlockHolder;
import protobuf.lang.psi.api.definitions.PbBlockDefinition;
import protobuf.lang.psi.api.definitions.PbToplevelDefinition;
import protobuf.lang.psi.api.references.PbRef;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public interface PbExtendDef extends PbToplevelDefinition, PbBlockDefinition, PbBlockHolder {

    public PbRef getTypeRef();
}
