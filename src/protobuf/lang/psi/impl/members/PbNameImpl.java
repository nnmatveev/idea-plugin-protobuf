package protobuf.lang.psi.impl.members;

import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.IElementType;
import protobuf.lang.psi.PbPsiEnums;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.members.PbName;
import protobuf.lang.psi.impl.PbPsiElementImpl;
import static protobuf.lang.psi.PbPsiEnums.*;
import static protobuf.lang.ProtobufElementTypes.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 29, 2010
 */
public class PbNameImpl extends PbPsiElementImpl implements PbName {
    public PbNameImpl(ASTNode node){
        super(node);
    }

    @Override
    public void accept(ProtobufPsiElementVisitor visitor) {
        visitor.visitName(this);
    }

    @Override
    public NameTokenType getNameTokenType() {
        ASTNode nameNode = getNode().findChildByType(IK);
        if(nameNode != null){
            IElementType tokenType = nameNode.getElementType();
            if(tokenType.equals(IDENTIFIER)){
                return NameTokenType.IDENTIFIER;
            }
            return NameTokenType.KEYWORD;
        }
        return null;
    }
}
