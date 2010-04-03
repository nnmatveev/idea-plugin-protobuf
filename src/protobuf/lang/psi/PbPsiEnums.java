package protobuf.lang.psi;

/**
 * author: Nikolay Matveev
 * Date: Apr 3, 2010
 */
public class PbPsiEnums {
    public enum FieldLabel{
        REQUIRED,
        REPEATED,
        OPTIONAL
    }

    public enum FieldType{
        BUILT_IN_TYPE,
        GROUP,
        CUSTOM_TYPE,

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

    public enum ReferenceKind {        
        DIRECTORY,
        PACKAGE,
        MESSAGE_OR_GROUP,
        MESSAGE_OR_ENUM_OR_GROUP,
        MESSAGE_OR_PACKAGE_OR_GROUP,
        MESSAGE_OR_GROUP_FIELD,
        EXTEND_FIELD,
        EXTEND_FIELD_INSIDE
    }
}
