package protobuf.formatter;

import static protobuf.lang.ProtobufElementTypes.*;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import protobuf.formatter.util.ProtobufBlockGenerator;
import protobuf.formatter.util.ProtobufIndentProcessor;
import protobuf.formatter.util.ProtobufSpacingProcessor;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.blocks.PbBlock;
import protobuf.lang.psi.api.members.PbOptionList;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public class ProtobufBlock implements Block {

    final ASTNode myNode;
    final Alignment myAlignment;
    final Indent myIndent;
    final Wrap myWrap;
    final CodeStyleSettings mySettings;


    private List<Block> mySubBlock;

    public ProtobufBlock(@NotNull final ASTNode node, @Nullable final Alignment alignment, @NotNull final Indent indent, @Nullable final Wrap wrap, final CodeStyleSettings settings) {
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
            mySubBlock = ProtobufBlockGenerator.generateSubBlocks(this, myAlignment, myWrap, mySettings);
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
        return ProtobufSpacingProcessor.getSpacing(this, (ProtobufBlock) block1, (ProtobufBlock) block2, mySettings);
    }

    //  todo: [low] make it more complete

    public ChildAttributes getChildAttributes(int i) {
        if (BLOCKS.contains(myNode.getElementType())) {
            return new ChildAttributes(Indent.getNormalIndent(), null);
        }
        if (OPTION_LIST.equals(myNode.getElementType())) {
            return new ChildAttributes(Indent.getContinuationIndent(), null);
        }
        return new ChildAttributes(Indent.getNoneIndent(), null);
    }

    public boolean isIncomplete() {
        return isIncomplete(myNode);
    }

    private static boolean isIncomplete(ASTNode node) {
        ASTNode lastChild = node.getLastChildNode();
        while (lastChild != null && SKIPED_ELEMENTS.contains(lastChild.getElementType())) {
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
