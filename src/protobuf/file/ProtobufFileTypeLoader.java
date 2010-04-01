package protobuf.file;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;
import protobuf.file.ProtobufFileType;

/**
 * author: Nikolay Matveev
 * Date: Mar 5, 2010
 */
public class ProtobufFileTypeLoader extends FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(ProtobufFileType.PROTOBUF_FILE_TYPE,"proto");
    }
}
