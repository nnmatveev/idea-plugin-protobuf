package protobuf.formatter.util;

import com.intellij.formatting.Spacing;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiComment;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.tree.IElementType;
import protobuf.formatter.PbBlock;
import protobuf.lang.psi.api.PbFile;

import static protobuf.lang.PbElementTypes.*;

/**
 * @author Nikolay Matveev
 * Date: Mar 12, 2010
 */
public class PbSpacingProcessor {

    private final static Logger LOG = Logger.getInstance(PbSpacingProcessor.class.getName());

    private static final Spacing DEFAULT_SPACING = Spacing.createSpacing(1, 1, 0, false, 0);

    private static final Spacing NO_SPACING = Spacing.createSpacing(0, 0, 0, false, 0);
    private static final Spacing ONE_SPACE = Spacing.createSpacing(1, 1, 0, false, 0);
    private static final Spacing SPACING_IN_FILE = Spacing.createSpacing(0, 0, 2, true, 4);
    private static final Spacing SPACING_IN_BLOCK = Spacing.createSpacing(0, 0, 1, true, 3);
    private static final Spacing NO_SPACING_WITH_EXISTED_LINES = Spacing.createSpacing(0, 0, 0, true, 30);
    private static final Spacing ONE_SPACING_WITH_EXISTED_LINES = Spacing.createSpacing(1, 1, 0, true, 30);
    private static final Spacing NO_SPACING_WITH_ONE_NEW_LINE = Spacing.createSpacing(0, 0, 1, true, 1);
    private static final Spacing NO_SPACING_WITH_ONE_EXISTED_LINE = Spacing.createSpacing(0, 0, 0, true, 1);

    public static Spacing getSpacing(PbBlock parent, PbBlock child1, PbBlock child2, CodeStyleSettings settings) {
        /*System.out.println("------------");
        System.out.println("parent: " + parent.getNode().getElementType());
        System.out.println("child1: " + child1.getNode().getElementType());
        System.out.println("child2: " + child2.getNode().getElementType());
        System.out.println("------------");*/

        //Comments
        if (child1.getNode().getPsi() instanceof PsiComment || child2.getNode().getPsi() instanceof PsiComment) {
            //System.out.println("NO_SPACING_WITH_EXISTED_LINES");
            return ONE_SPACING_WITH_EXISTED_LINES;
        }

        //In option list
        if (sameType(parent, OPTION_LIST)) {
            //braces
            if (child1.getNode().getElementType().equals(OPEN_BRACE)) {
              //  System.out.println("NO_SPACING_WITH_ONE_EXISTED_LINE");
                return NO_SPACING;
            }
            if (child2.getNode().getElementType().equals(CLOSE_BRACE)) {
              //  System.out.println("NO_SPACING_WITH_ONE_EXISTED_LINE");
                return NO_SPACING;
            }
            //commma
            if (child2.getNode().getElementType().equals(COMMA)) {
              //  System.out.println("NO_SPACING");
                return NO_SPACING;
            }

            if (child1.getNode().getElementType().equals(COMMA)) {
              //  System.out.println("ONE_SPACE");
                return ONE_SPACE;
            }
        }

        //In option assignment
        if(sameType(parent,VALUE)){
            if(sameType(child1,MINUS)){
                return NO_SPACING;
            }
        }

        //in file
        if (parent.getNode().getPsi() instanceof PbFile) {
            //System.out.println("SPACING_IN_FILE");
            return SPACING_IN_FILE;
        }

        //in block
        if (BLOCKS.contains(getType(parent))) {
            if (sameType(child1, OPEN_BLOCK)) {
            //    System.out.println("NO_SPACING_WITH_ONE_NEW_LINE");
                return NO_SPACING_WITH_ONE_NEW_LINE;
            }
            if (sameType(child2, CLOSE_BLOCK)) {
            //    System.out.println("NO_SPACING_WITH_ONE_NEW_LINE");
                return NO_SPACING_WITH_ONE_NEW_LINE;
            }
            //System.out.println("SPACING_IN_BLOCK");
            return SPACING_IN_BLOCK;
        }

        //'.'
        if (sameType(child1, DOT) || sameType(child2, DOT)) {
            //System.out.println("NO_SPACING");
            return NO_SPACING;
        }

        //';'
        if (child2.getNode().getElementType().equals(SEMICOLON)) {
            //System.out.println("NO_SPACING");
            return NO_SPACING;
        }

        //Braces

        //Parants
        if (child1.getNode().getElementType().equals(OPEN_PARANT)) {
            //System.out.println("NO_SPACING");
            return NO_SPACING;
        }
        if (child2.getNode().getElementType().equals(CLOSE_PARANT)) {
           //System.out.println("NO_SPACING");
            return NO_SPACING;
        }

        //System.out.println("DEFAULT_SPACING");
        return DEFAULT_SPACING;
    }

    private static boolean sameType(PbBlock block, IElementType type) {
        return block.getNode().getElementType().equals(type);
    }

    private static IElementType getType(PbBlock block) {
        return block.getNode().getElementType();
    }
}
