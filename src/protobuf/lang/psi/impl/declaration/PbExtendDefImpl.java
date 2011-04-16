package protobuf.lang.psi.impl.declaration;

import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import protobuf.lang.psi.PbPsiElementVisitor;
import protobuf.lang.psi.api.block.PbBlock;
import protobuf.lang.psi.api.declaration.PbExtendDef;
import protobuf.lang.psi.api.reference.PbRef;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * @author Nikolay Matveev
 */


public class PbExtendDefImpl extends PbPsiElementImpl implements PbExtendDef {
    public PbExtendDefImpl(ASTNode node){
        super(node);
    }

    @Override
    public void accept(@NotNull PbPsiElementVisitor visitor) {
        visitor.visitExtendDefinition(this);
    }

    @Override
    public PbRef getTypeRef() {
        return findChildByClass(PbRef.class);        
    }

    @Override
    public PbBlock getBlock() {
        return findChildByClass(PbBlock.class);
    }
}
