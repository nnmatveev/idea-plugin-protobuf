package protobuf.compiler;

import com.intellij.openapi.compiler.ValidityState;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Provides validity state for the Protobuf generating compiler.  It's a combination between a
 * {@link com.intellij.openapi.compiler.TimestampValidityState} and a
 * {@link com.intellij.openapi.compiler.CopyingCompiler.DestinationFileInfo}.
 *
 * @author Travis Cripps
 */
public class PbGenerationItemValidityState implements ValidityState {

    private long lastModTimestamp;
    private boolean filesExist;

    public PbGenerationItemValidityState(long lastModTimestamp, boolean filesExist) {
        this.lastModTimestamp = lastModTimestamp;
        this.filesExist = filesExist;
    }

    public boolean equalsTo(ValidityState otherState) {
        if (!(otherState instanceof PbGenerationItemValidityState)) {
            return false;
        }
        
        // Since this is a generating compiler and files aren't generated when the ValidityState is equal to the previous
        // state, force the generation when the files don't exist.
        if (!filesExist) {
            return false;
        }
        
        PbGenerationItemValidityState otherVState = (PbGenerationItemValidityState)otherState;
        return (lastModTimestamp == otherVState.lastModTimestamp) && (filesExist == otherVState.filesExist);
    }

    public void save(DataOutput out) throws IOException {
        out.writeLong(lastModTimestamp);
        out.writeBoolean(filesExist);
    }

    /**
     * Loads the validity state from the specified stream.
     */
    public static PbGenerationItemValidityState load(DataInput is) throws IOException {
        long lastModTimestamp = is.readLong();
        boolean filesExist = is.readBoolean();
        return new PbGenerationItemValidityState(lastModTimestamp, filesExist);
    }

}
