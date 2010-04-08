package protobuf.lang.psi.impl.definitions;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.definitions.PbServiceDef;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public class PbServiceDefImpl extends PbPsiElementImpl implements PbServiceDef {
    public PbServiceDefImpl(ASTNode node){
        super(node);
    }
}
