package protobuf.formatter;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import protobuf.formatter.util.PbBlockGenerator;
import protobuf.formatter.util.PbSpacingProcessor;

import java.util.List;

import static protobuf.lang.PbElementTypes.BLOCKS;
import static protobuf.lang.PbElementTypes.SKIPPED_ELEMENTS;

/**
 * @author Nikolay Matveev
 * Date: Mar 12, 2010
 */
public class PbBlock implements Block {

    final ASTNode myNode;
    final Alignment myAlignment;
    final Indent myIndent;
    final Wrap myWrap;
    final CodeStyleSettings mySettings;


    private List<Block> mySubBlock;

    public PbBlock(@NotNull final ASTNode node, @Nullable final Alignment alignment, @NotNull final Indent indent, @Nullable final Wrap wrap, final CodeStyleSettings settings) {
        myNode = node;
        myAlignment = alignment;
        myIndent = indent;
        myWrap = wrap;
        mySettings = settings;
    }

    public TextRange getTextRange() {
        return myNode.getTextRange();
    }

    public List<Block> getSubBlocks() {
        if (mySubBlock == null) {
            mySubBlock = PbBlockGenerator.generateSubBlocks(this, myAlignment, myWrap, mySettings);
        }
        return mySubBlock;
    }

    public Wrap getWrap() {
        return myWrap;
    }

    public Indent getIndent() {
        return myIndent;
    }

    public Alignment getAlignment() {
        return myAlignment;
    }

    public Spacing getSpacing(Block block1, Block block2) {        
        return PbSpacingProcessor.getSpacing(this, (PbBlock) block1, (PbBlock) block2, mySettings);
    }

    //  todo: [low] make it more complete

    public ChildAttributes getChildAttributes(int i) {
        if (BLOCKS.contains(myNode.getElementType())) {
            return new ChildAttributes(Indent.getNormalIndent(), null);
        }
        return new ChildAttributes(Indent.getNoneIndent(), null);
    }

    public boolean isIncomplete() {
        return isIncomplete(myNode);
    }

    private static boolean isIncomplete(ASTNode node) {
        ASTNode lastChild = node.getLastChildNode();
        while (lastChild != null && SKIPPED_ELEMENTS.contains(lastChild.getElementType())) {
            lastChild = lastChild.getTreePrev();
        }
        return lastChild != null && (lastChild.getPsi() instanceof PsiErrorElement || isIncomplete(lastChild));
    }

    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }


    public ASTNode getNode() {
        return myNode;
    }
}
