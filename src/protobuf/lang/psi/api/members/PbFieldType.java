package protobuf.lang.psi.api.members;

import protobuf.lang.psi.api.PbPsiElement;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public interface PbFieldType extends PbPsiElement {

    enum Type {
        BUILT_IN_TYPE,
        GROUP,
        USER_DEFINED_TYPE,

        BOOL,
        STRING,
        BYTES,
        DOUBLE,
        FLOAT,
        INT32,
        INT64,
        UINT32,
        UINT64,
        FIXED32,
        FIXED64,
        SFIXED32,
        SFIXED64,
        SINT32,
        SINT64,
    }
    
    public Type getType();
        
}
