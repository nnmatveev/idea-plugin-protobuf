package protobuf.lang.psi.impl.member;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import protobuf.lang.ProtobufElementTypes;
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
        String text = null;
        PsiElement identifier = this.findChildByType(ProtobufElementTypes.IDENTIFIER);
        if (null == identifier) {
            identifier = this.findChildByType(ProtobufElementTypes.OPTION_REF_SEQ); // Includes the surrounding parenthesis.
        }
        if (identifier != null) {
            text = identifier.getText().trim();
        }
        return text;
    }

    /**
     * Gets the value of the option declaration.
     * @return the option value
     */
    @Override
    public String getOptionValue() {
        String value = null;
        PsiElement val = this.findChildByType(ProtobufElementTypes.VALUE);
        if (val != null) {
            value = StringUtil.stripQuotesAroundValue(val.getText().trim());
        }
        return value;
    }
    
}
