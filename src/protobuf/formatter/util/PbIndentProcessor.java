package protobuf.formatter.util;

import com.intellij.formatting.Indent;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import protobuf.formatter.PbBlock;
import protobuf.lang.psi.impl.PbFileImpl;

import static protobuf.lang.PbElementTypes.*;

/**
 * @author Nikolay Matveev
 * Date: Mar 12, 2010
 */
public class PbIndentProcessor {

    private final static Logger LOG = Logger.getInstance(PbIndentProcessor.class.getName());

    public static Indent getChildIndent(PbBlock block, ASTNode childNode) {
        ASTNode curNode = block.getNode();
        PsiElement curPsi = curNode.getPsi();

        if (curPsi instanceof PbFileImpl) {
            return Indent.getNoneIndent();
        }

        if (curPsi instanceof protobuf.lang.psi.api.block.PbBlock) {
            if (childNode.getElementType().equals(OPEN_BLOCK) || childNode.getElementType().equals(CLOSE_BLOCK)) {
                return Indent.getNoneIndent();
            }
            return Indent.getNormalIndent();
        }
        return Indent.getNoneIndent();
    }
}
