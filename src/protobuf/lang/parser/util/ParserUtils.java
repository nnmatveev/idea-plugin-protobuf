package protobuf.lang.parser.util;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;

/**
 * author: Nikolay Matveev
 * Date: Mar 9, 2010
 */
public class ParserUtils {
    public static boolean getToken(PsiBuilder builder, IElementType element) {
        if(element.equals(builder.getTokenType())) {
            builder.advanceLexer();
            return true;
        } else {
            return false;
        }
    }
    public static boolean getToken(PsiBuilder builder, String text) {
        if(text.equals(builder.getTokenText())) {
            builder.advanceLexer();
            return true;
        } else {
            return false;
        }
    }
}
