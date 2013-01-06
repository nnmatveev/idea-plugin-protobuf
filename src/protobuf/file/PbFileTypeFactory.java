package protobuf.file;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * A FileTypeFactory implementation for protobuf files.
 * @author Travis Cripps
 */
public class PbFileTypeFactory extends FileTypeFactory {

    public PbFileTypeFactory() {
        super();
    }

    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(PbFileType.PROTOBUF_FILE_TYPE, PbFileType.PROTOBUF_FILE_TYPE.getDefaultExtension());
    }

}
