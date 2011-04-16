package protobuf.commenter;

import com.intellij.lang.Commenter;

/**
 * @author Nikolay Matveev
 * Date: Mar 14, 2010
 */
public class PbCommenter implements Commenter {
    public String getLineCommentPrefix() {
        return "//";
    }

    public String getBlockCommentPrefix() {
        return "/*";  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getBlockCommentSuffix() {
        return "*/";  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getCommentedBlockCommentPrefix() {
        return null;
    }

    public String getCommentedBlockCommentSuffix() {
        return null;
    }
}
