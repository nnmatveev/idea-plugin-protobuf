package protobuf.lang.parser.util;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import protobuf.PbBundle;


/**
 * author: Nikolay Matveev
 * Date: Mar 10, 2010
 */
public class PbPatchedPsiBuilder {
    private PsiBuilder myBuilder;

    public PbPatchedPsiBuilder(PsiBuilder builder) {
        myBuilder = builder;
    }

    public boolean match(IElementType element) {
        if (element.equals(myBuilder.getTokenType())) {
            myBuilder.advanceLexer();
            return true;
        }
        return false;
    }

    public boolean match(IElementType element, String errorMessage) {
        if (element.equals(myBuilder.getTokenType())) {
            myBuilder.advanceLexer();
            return true;
        } else {
            myBuilder.error(PbBundle.message(errorMessage));
        }
        return false;
    }

    public boolean matchAs(IElementType element, IElementType type, String errorMessage) {
        if (element.equals(myBuilder.getTokenType())) {
            PsiBuilder.Marker marker = myBuilder.mark();
            myBuilder.advanceLexer();
            marker.done(type);
            return true;
        } else {
            myBuilder.error(PbBundle.message(errorMessage));
        }
        return false;
    }

    public boolean match(TokenSet set) {
        if (set.contains(myBuilder.getTokenType())) {
            myBuilder.advanceLexer();
            return true;
        }
        return false;
    }

    public boolean match(TokenSet set, String errorMessage) {
        if (set.contains(myBuilder.getTokenType())) {
            myBuilder.advanceLexer();
            return true;
        } else {
            myBuilder.error(PbBundle.message(errorMessage));
        }
        return false;
    }

    public boolean matchAs(TokenSet set, IElementType type, String errorMessage) {
        if (set.contains(myBuilder.getTokenType())) {
            PsiBuilder.Marker marker = myBuilder.mark();
            myBuilder.advanceLexer();
            marker.done(type);
            return true;
        } else {
            myBuilder.error(PbBundle.message(errorMessage));
        }
        return false;
    }

    public boolean match(String tokenText) {
        if (tokenText.equals(myBuilder.getTokenText())) {
            myBuilder.advanceLexer();
            return true;
        }
        return false;
    }

    public boolean match(String tokenText, String errorMessage) {
        if (tokenText.equals(myBuilder.getTokenText())) {
            myBuilder.advanceLexer();
            return true;
        } else {
            myBuilder.error(PbBundle.message(errorMessage));
        }
        return false;
    }

    public void match() {
        myBuilder.advanceLexer();
    }

    public boolean eof() {
        return myBuilder.eof();
    }

    public PsiBuilder getBuilder() {
        return myBuilder;
    }

    public IElementType getTokenType() {
        return myBuilder.getTokenType();
    }

    public String getTokenText() {
        return myBuilder.getTokenText();
    }

    public boolean compareToken(IElementType element) {
        return element.equals(myBuilder.getTokenType());
    }

    public boolean compareToken(TokenSet set) {
        return set.contains(myBuilder.getTokenType());
    }

    public PsiBuilder.Marker mark() {
        return myBuilder.mark();
    }


    public void error(String errorMessage) {
        myBuilder.error(PbBundle.message(errorMessage));
    }

    public boolean lookAhead(IElementType... elements) {
        if (elements.length == 0) return false;
        if (elements.length == 1) return elements[0].equals(myBuilder.getTokenType());
        PsiBuilder.Marker marker = myBuilder.mark();
        int i = 0;
        while (i < elements.length && elements[i].equals(myBuilder.getTokenType()) && !myBuilder.eof()) {
            myBuilder.advanceLexer();
            i++;
        }
        marker.rollbackTo();
        return i == elements.length;
    }

    public boolean lookAhead(TokenSet... sets) {
        if (sets.length == 0) return false;
        if (sets.length == 1) return sets[0].contains(myBuilder.getTokenType());
        PsiBuilder.Marker marker = myBuilder.mark();
        int i = 0;
        while (i < sets.length && sets[i].contains(myBuilder.getTokenType()) && !myBuilder.eof()) {
            myBuilder.advanceLexer();
            i++;
        }
        marker.rollbackTo();
        return i == sets.length;
    }

    public void eatError(String errorMessage) {
        PsiBuilder.Marker marker = myBuilder.mark();
        myBuilder.advanceLexer();
        marker.error(PbBundle.message(errorMessage));
    }

}
