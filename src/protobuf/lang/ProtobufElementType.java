package protobuf.lang;

import com.intellij.psi.tree.IElementType;
import protobuf.file.ProtobufFileType;


/**
 * author: Nikolay Matveev
 * Date: Mar 5, 2010
 */
public class ProtobufElementType extends IElementType {    
    public ProtobufElementType(String debugName){
        super(debugName, ProtobufFileType.PROTOBUF_FILE_TYPE.getLanguage());
    }
}
