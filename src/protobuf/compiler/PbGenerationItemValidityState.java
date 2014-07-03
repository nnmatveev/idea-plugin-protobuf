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
    private long outputLastModifiedTimestamp;
    private boolean filesExist;

    public PbGenerationItemValidityState(long lastModTimestamp, long outputLastModifiedTimestamp, boolean filesExist) {
        this.lastModTimestamp = lastModTimestamp;
        this.outputLastModifiedTimestamp = outputLastModifiedTimestamp;
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
        out.writeLong(outputLastModifiedTimestamp);
        out.writeBoolean(filesExist);
    }

    public boolean valid() {
        return filesExist && lastModTimestamp < outputLastModifiedTimestamp;
    }

    /**
     * Loads the validity state from the specified stream.
     * @param is - the inputStream of serialized data representing a PbGenerationItemValidityState.
     * @return a PbGenerationItemValidityState
     * @throws IOException if the inputStream can't be read
     */
    public static PbGenerationItemValidityState load(DataInput is) throws IOException {
        long lastModTimestamp = is.readLong();
        long outputLastModifiedTimestamp = is.readLong();
        boolean filesExist = is.readBoolean();
        return new PbGenerationItemValidityState(lastModTimestamp, outputLastModifiedTimestamp, filesExist);
    }

}
