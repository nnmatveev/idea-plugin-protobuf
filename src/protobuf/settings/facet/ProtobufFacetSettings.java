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

    // If the protobuf compiler should compile in a particular given directory, as opposed to where the file is
    public boolean COMPILE_IN_GIVEN_DIRECTORY = true;

    // Which directory to run in
    public String COMPILER_RUN_DIRECTORY = "";

}
