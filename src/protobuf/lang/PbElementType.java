package protobuf.lang;

import com.intellij.psi.tree.IElementType;
import protobuf.file.PbFileType;


/**
 * @author Nikolay Matveev
 * Date: Mar 5, 2010
 */
public class PbElementType extends IElementType {
    public PbElementType(String debugName){
        super(debugName, PbFileType.PROTOBUF_FILE_TYPE.getLanguage());
    }
}
