package protobuf.file;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import protobuf.PbIcons;
import protobuf.PbLanguage;

import javax.swing.*;

/**
 * @author Nikolay Matveev
 */
public class PbFileType extends LanguageFileType {

    public static final PbFileType PROTOBUF_FILE_TYPE = new PbFileType();
    public static final Language PROTOBUF_LANGUAGE = PROTOBUF_FILE_TYPE.getLanguage();
    public static final String[] DEFAULT_ASSOCIATED_EXTENSIONS = new String[]{"proto"};

    private PbFileType(){
        super(new PbLanguage());
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
        return PbIcons.FILE_TYPE;
    }
}
