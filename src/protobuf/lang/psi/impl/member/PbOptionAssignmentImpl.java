package protobuf.lang.psi.impl.member;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import protobuf.lang.psi.PbPsiEnums;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.block.PbBlock;
import protobuf.lang.psi.api.member.PbOptionAssignment;
import protobuf.lang.psi.api.member.PbOptionList;
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
    // Note that this method gets its information from inspecting the Psi element tree, so must use the runReadAction and
    // Computable because it's not run from the normal thread that deals with them.
    @Override
    public String getOptionName() {
        String text = ApplicationManager.getApplication().runReadAction(new Computable<String>() {
            public String compute() {
                return getText().trim();
            }
        });
        if (text.startsWith("option")) { // This should always be true, but check just in case.
            text = text.substring("option".length());
        }
        return text.substring(0, text.indexOf("=")).trim();
    }

    /**
     * Gets the value of the option declaration.
     * @return the option value
     */
    // Note that this method gets its information from inspecting the Psi element tree, so must use the runReadAction and
    // Computable because it's not run from the normal thread that deals with them.
    @Override
    public String getOptionValue() {
        String text = ApplicationManager.getApplication().runReadAction(new Computable<String>() {
            public String compute() {
                return getText().trim();
            }
        });
        String value = text.substring(text.lastIndexOf("=") + 1).trim();
        if (value.endsWith(";")) {
            value = value.substring(0, value.length() - 1).trim();
        }
        return StringUtil.stripQuotesAroundValue(value);
    }
    
}
