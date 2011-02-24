package protobuf.lang.psi.impl.member;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.text.StringUtil;
import protobuf.lang.psi.PbPsiEnums;
import protobuf.lang.psi.api.member.PbOptionAssignment;
import protobuf.lang.psi.api.member.PbOptionRefSeq;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public class PbOptionAssignmentImpl extends PbPsiElementImpl implements PbOptionAssignment {
    public PbOptionAssignmentImpl(ASTNode node) {
        super(node);
    }

    @Override
    public PbPsiEnums.OptionType getType() {
        if (this.findChildByClass(PbOptionRefSeq.class) != null) {
            return PbPsiEnums.OptionType.CUSTOM_OPTION; 
        }
        return PbPsiEnums.OptionType.BUILT_IN_OPTION;
    }

    /**
     * Gets the name of the option declaration.
     * @return the name
     */
    @Override
    public String getOptionName() {
        String text = getText().trim();
        if (text.startsWith("option")) { // This should always be true, but check just in case.
            text = text.substring("option".length());
        }
        return text.substring(0, text.indexOf("=")).trim();
    }

    /**
     * Gets the value of the option declaration.
     * @return the option value
     */
    @Override
    public String getOptionValue() {
        String text = getText().trim();
        String value = text.substring(text.lastIndexOf("=") + 1).trim();
        if (value.endsWith(";")) {
            value = value.substring(0, value.length() - 1).trim();
        }
        return StringUtil.stripQuotesAroundValue(value);
    }
    
}
