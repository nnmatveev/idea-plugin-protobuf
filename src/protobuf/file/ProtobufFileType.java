package protobuf.file;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import protobuf.ProtobufIcons;
import protobuf.ProtobufLanguage;

import javax.swing.*;

/**
 * author: Nikolay Matveev
 */
public class ProtobufFileType extends LanguageFileType {

    public static final ProtobufFileType PROTOBUF_FILE_TYPE = new ProtobufFileType();
    public static final Language PROTOBUF_LANGUAGE = PROTOBUF_FILE_TYPE.getLanguage();
    public static final String[] DEFAULT_ASSOCIATED_EXTENSIONS = new String[]{"proto"};

    private ProtobufFileType(){
        super(new ProtobufLanguage());
    }

    @NotNull
    public String getName() {
        return "Protobuf";
    }

    @NotNull
    public String getDescription() {
        return "Protocol Buffers file";
    }

    @NotNull
    public String getDefaultExtension() {
        return "proto";
    }

    public Icon getIcon() {
        return ProtobufIcons.FILE_TYPE;
    }
}
