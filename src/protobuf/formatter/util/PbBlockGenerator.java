package protobuf.formatter.util;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.Wrap;
import com.intellij.lang.ASTNode;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import protobuf.formatter.PbBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikolay Matveev
 * Date: Mar 22, 2010
 */
public class PbBlockGenerator {
    public static List<Block> generateSubBlocks(PbBlock block, Alignment alignment, Wrap wrap, CodeStyleSettings settings) {
        ASTNode curNode = block.getNode();        

        ArrayList<Block> subBlocks = new ArrayList<Block>();
        ASTNode[] children = curNode.getChildren(null);
        for (ASTNode childNode : children) {
            if (canBeCorrectBlock(childNode)) {
                //todo [low] it is not clear with aligment
                subBlocks.add(new PbBlock(childNode, curNode.getPsi() instanceof protobuf.lang.psi.api.block.PbBlock ? null : alignment, PbIndentProcessor.getChildIndent(block, childNode), wrap, settings));
            }
        }
        return subBlocks;
    }

    private static boolean canBeCorrectBlock(ASTNode node) {
        return node.getText().trim().length() > 0;
    }
}
