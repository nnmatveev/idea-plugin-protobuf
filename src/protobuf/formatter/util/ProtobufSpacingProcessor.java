package protobuf.formatter.util;

import com.intellij.formatting.Spacing;
import com.intellij.psi.PsiComment;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import protobuf.formatter.ProtobufBlock;
import protobuf.lang.psi.api.blocks.PbBlock;
import protobuf.lang.psi.impl.PbFileImpl;

import static protobuf.lang.ProtobufElementTypes.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public class ProtobufSpacingProcessor {

    private static final Spacing NO_SPACING = Spacing.createSpacing(0, 0, 0, false, 0);
    private static final Spacing ONE_SPACE = Spacing.createSpacing(1, 1, 0, false, 0);
    private static final Spacing NO_SPACING_WITH_MAYBE_NEW_LINE = Spacing.createSpacing(0, 0, 0, true, 1);
    private static final Spacing NO_SPACING_WITH_A_NEW_LINE = Spacing.createSpacing(0, 0, 1, true, 1);
    private static final Spacing NO_SPACING_WITH_TWO_NEW_LINES = Spacing.createSpacing(0, 0, 2, true, 2);
    private static final Spacing NO_SPACING_WITH_EXISTED_LINES = Spacing.createSpacing(0, 0, 0, true, 100);   

    //todo [low] add parent to determine context
    public static Spacing getSpacing(ProtobufBlock parent, ProtobufBlock child1, ProtobufBlock child2, CodeStyleSettings settings) {
        //Comments
        if (child1.getNode().getPsi() instanceof PsiComment) {
            return NO_SPACING_WITH_EXISTED_LINES;
        }

        //'.'
        if (child1.getNode().getElementType().equals(DOT) || child2.getNode().getElementType().equals(DOT)) {
            return NO_SPACING;
        }
        
        //';'
        if (child2.getNode().getElementType().equals(SEMICOLON)) {
            return NO_SPACING;
        }

        //Braces
        if (child1.getNode().getElementType().equals(OPEN_BRACE)) {
            return NO_SPACING_WITH_MAYBE_NEW_LINE;
        }
        if (child2.getNode().getElementType().equals(CLOSE_BRACE)) {
            return NO_SPACING_WITH_MAYBE_NEW_LINE;
        }

        //Parants
        if (child1.getNode().getElementType().equals(OPEN_PARANT)) {
            return NO_SPACING;
        }
        if (child2.getNode().getElementType().equals(CLOSE_PARANT)) {
            return NO_SPACING;
        }

        //Comma
        if (child2.getNode().getElementType().equals(COMMA)) {
            return NO_SPACING;
        }

        if (child1.getNode().getElementType().equals(COMMA)) {
            return NO_SPACING_WITH_MAYBE_NEW_LINE;
        }

        //Blocks
        if (child2.getNode().getPsi() instanceof PbBlock) {
            return ONE_SPACE;
        }

        if (child1.getNode().getElementType().equals(OPEN_BLOCK)) {
            return NO_SPACING_WITH_A_NEW_LINE;
        }

        if (child2.getNode().getElementType().equals(CLOSE_BLOCK)) {
            return NO_SPACING_WITH_A_NEW_LINE;
        }

        if(parent.getNode().getPsi() instanceof PbFileImpl && DEFS.contains(child1.getNode().getElementType())){
            return NO_SPACING_WITH_TWO_NEW_LINES;
        }

        if(BLOCKS.contains(parent.getNode().getElementType()) && DEFS.contains(child2.getNode().getElementType())){
                return NO_SPACING_WITH_A_NEW_LINE;
        }
        //todo [low] handle in context
        //Top level statements
        //if (child1.getNode().getPsi() instanceof PbToplevelDefinition) {
        //    return NO_SPACING_WITH_TWO_NEW_LINES;
        //}

        //Block statements
        //if (child2.getNode().getPsi() instanceof PbBlockDefinition) {
        //    return NO_SPACING_WITH_A_NEW_LINE;
        //}
        
        return Spacing.createSpacing(1, 1, 0, false, 100);
    }
}
