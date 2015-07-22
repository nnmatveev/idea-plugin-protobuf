package protobuf.settings.facet;

/**
 * Settings for the Protobuf facet.
 * @author Travis Cripps
 */
public class ProtobufFacetSettings {

    // Is compilation of .proto files enabled?
    public boolean COMPILE_PROTO = true;

    // The directory where the files resulting from protoc invocation will be created.
    public String COMPILER_OUTPUT_SOURCE_DIRECTORY = "";

    // Semi-colon separated list of additional proto path directories.
    public String COMPILER_ADDITIONAL_PROTO_PATHS = "";

}
