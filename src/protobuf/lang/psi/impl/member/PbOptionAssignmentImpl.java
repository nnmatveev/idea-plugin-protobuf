package protobuf.lang.psi.impl.member;

import com.intellij.lang.ASTNode;
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
}
