package protobuf.lang.psi;

/**
 * author: Nikolay Matveev
 * Date: Apr 3, 2010
 */
public class PbPsiEnums {

    public enum ReferenceKind {
        FILE,
        PACKAGE,
        MESSAGE_OR_GROUP,
        MESSAGE_OR_ENUM_OR_GROUP,
        MESSAGE_OR_PACKAGE_OR_GROUP,
        MESSAGE_OR_GROUP_FIELD,
        EXTEND_FIELD,
        ENUM_CONSTANT
    }

    public enum CompletionKind {
        //complete
    }

    public enum FieldLabel {
        REQUIRED,
        REPEATED,
        OPTIONAL
    }

    public enum FieldType {
        BUILT_IN_TYPE,        
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
        SINT64
    }

    public enum FileOption {
        JAVA_PACKAGE,
        JAVA_OUTER_CLASS,
        JAVA_MULTIPLE_FILES,
        OPTIMIZE_FOR,
        CC_GENERIC_SERVICES,
        JAVA_GENERIC_SERVICES,
        PYTHON_GENERIC_SERVICES,
    }

    public enum OptimizeMode {
        SPEED,
        CODE_SIZE,
        LITE_RUNTIME
    }

    public enum MessageOption {
        MESSAGE_SET_WIRE_FORMAT,
        NO_STANDARD_DESCRIPTOR_ACCESSOR
    }

    public enum FieldOption {
        CTYPE,
        PACKED,
        DEPRECATED,
        EXPERIMENTAL_MAP_KEY
    }

    public enum CType {
        STRING,
        CORD,
        STRING_PIECE
    }

    public enum NameTokenType {
        IDENTIFIER,
        KEYWORD        
    }
}
