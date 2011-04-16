package protobuf.lang.psi.impl.declaration;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.Icons;
import org.jetbrains.annotations.NotNull;
import protobuf.lang.psi.PbPsiElementVisitor;
import protobuf.lang.psi.api.declaration.PbEnumDef;
import protobuf.lang.psi.impl.auxiliary.PbNamedBlockHolderImpl;
import protobuf.lang.psi.utils.PbPsiUtil;

import javax.swing.*;

/**
 * @author Nikolay Matveev
 * Date: Mar 10, 2010
 */
public class PbEnumDefImpl extends PbNamedBlockHolderImpl implements PbEnumDef {
    public PbEnumDefImpl(ASTNode node){
        super(node);
    }

        @Override
    public void accept(@NotNull PbPsiElementVisitor visitor) {
        visitor.visitEnumDefinition(this);
    }

    @Override
    public PsiElement getNameElement() {        
        return PbPsiUtil.getChild(this,1,true,true,false);
    }

    @Override
    public Icon getIcon(int flags) {
        return Icons.ENUM_ICON;
    }
}
