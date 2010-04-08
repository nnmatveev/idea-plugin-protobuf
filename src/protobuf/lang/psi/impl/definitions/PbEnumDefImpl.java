package protobuf.lang.psi.impl.definitions;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import protobuf.lang.psi.api.blocks.PbBlock;
import protobuf.lang.psi.api.definitions.PbEnumDef;
import protobuf.lang.psi.impl.auxiliary.PbBlockHolderImpl;
import protobuf.lang.psi.impl.members.PbNameImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 10, 2010
 */
public class PbEnumDefImpl extends PbBlockHolderImpl implements PbEnumDef {
    public PbEnumDefImpl(ASTNode node){
        super(node);
    }

    public PsiElement setName(@NonNls String s) throws IncorrectOperationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getName() {
        return findChildByClass(PbNameImpl.class).getText();
    }

}
