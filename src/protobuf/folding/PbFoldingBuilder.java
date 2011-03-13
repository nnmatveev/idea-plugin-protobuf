package protobuf.folding;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static protobuf.lang.PbElementTypes.*;

/**
 * @author Nikolay Matveev
 * Date: Mar 15, 2010
 */
public class PbFoldingBuilder implements FoldingBuilder {
    @NotNull
    public FoldingDescriptor[] buildFoldRegions(@NotNull ASTNode astNode, @NotNull Document document) {
        List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();
        appendDescriptors(astNode, descriptors);
        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    public String getPlaceholderText(@NotNull ASTNode curNode) {
        if (BLOCKS.contains(curNode.getElementType())) {
            return "{...}";
        }
        if(C_STYLE_COMMENT.equals(curNode.getElementType())){
            return "/*...*/";
        }        
        return null;
    }

    public boolean isCollapsedByDefault(@NotNull ASTNode astNode) {
        return C_STYLE_COMMENT.equals(astNode.getElementType());
    }

    private void appendDescriptors(ASTNode curNode, List<FoldingDescriptor> descriptors) {
        if (BLOCKS.contains(curNode.getElementType()) && isMultiline(curNode)) {
            descriptors.add(new FoldingDescriptor(curNode, curNode.getTextRange()));
        }
        if (C_STYLE_COMMENT.equals(curNode.getElementType()) && isMultiline(curNode)) {    //todo [low] check for well ended
            descriptors.add(new FoldingDescriptor(curNode, curNode.getTextRange()));
        }

        ASTNode child = curNode.getFirstChildNode();
        while (child != null) {
            appendDescriptors(child, descriptors);
            child = child.getTreeNext();
        }
    }

    private boolean isMultiline(ASTNode node) {
        String text = node.getText();
        return text.contains("\n") || text.contains("\r") || text.contains("\r\n");
    }
}
