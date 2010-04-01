package protobuf.lang.psi.api.definitions;

import protobuf.lang.psi.api.definitions.PbBlockDefinition;
import protobuf.lang.psi.api.definitions.PbToplevelDefinition;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public interface PbOptionDef extends PbToplevelDefinition, PbBlockDefinition {

    enum FileOption{
        JAVA_PACKAGE,
        JAVA_OUTER_CLASS,
        JAVA_MULTIPLE_FILES,
        OPTIMIZE_FOR,
        CC_GENERIC_SERVICES,
        JAVA_GENERIC_SERVICES,
        PYTHON_GENERIC_SERVICES,
    }

    enum OptimizeMode{
        SPEED,
        CODE_SIZE,
        LITE_RUNTIME
    }

    enum MessageOption{
        MESSAGE_SET_WIRE_FORMAT,
        NO_STANDARD_DESCRIPTOR_ACCESSOR
    }

    enum FieldOption{
        CTYPE,
        PACKED,
        DEPRECATED,
        EXPERIMENTAL_MAP_KEY
    }

    enum CType{
        STRING,
        CORD,
        STRING_PIECE
    }
}
