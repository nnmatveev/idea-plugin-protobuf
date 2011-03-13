package protobuf.highlighter;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import protobuf.lang.PbTokenTypes;

/**
 * @author Nikolay Matveev
 * Date: Mar 6, 2010
 */
public class PbBraceMatcher implements PairedBraceMatcher {

    //todo [low] what is true and what is false
    private static final BracePair[] PAIRS = {
        new BracePair(PbTokenTypes.OPEN_BRACE, PbTokenTypes.CLOSE_BRACE,false),
        new BracePair(PbTokenTypes.OPEN_BLOCK, PbTokenTypes.CLOSE_BLOCK,false),
        new BracePair(PbTokenTypes.OPEN_PARANT, PbTokenTypes.CLOSE_PARANT,false)
    };


    public BracePair[] getPairs() {
        return PAIRS;
    }

    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType iElementType, @Nullable IElementType iElementType1) {
        return true;
    }

    public int getCodeConstructStart(PsiFile psiFile, int i) {
        return i;
    }
}
