package protobuf.settings.facet;

/**
 * Settings for the Protobuf facet.
 * @author Travis Cripps
 */
public class ProtobufFacetSettings {

    // Is compilation of .proto files enabled?
    public boolean COMPILE_PROTO = true;

    // The directory where the files resulting from protoc invocation.
    public String COMPILER_OUTPUT_SOURCE_DIRECTORY = "";

}
