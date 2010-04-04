package protobuf.lang.psi.impl.blocks;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ArrayUtil;
import protobuf.lang.psi.PbPsiEnums;
import protobuf.lang.psi.api.blocks.PbBlock;
import protobuf.lang.psi.api.definitions.*;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Apr 2, 2010
 */
public class PbBlockImpl extends PbPsiElementImpl implements PbBlock {

    private final static Logger LOG = Logger.getInstance(PbBlockImpl.class.getName());    

    public PbBlockImpl(ASTNode node) {
        super(node);
    }

    @Override
    public String toString() {
        return "Block";
    }    
}
