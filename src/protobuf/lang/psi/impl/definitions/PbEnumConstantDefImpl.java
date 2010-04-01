package protobuf.lang.psi.impl.definitions;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.definitions.PbEnumConstantDefinition;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 10, 2010
 */
public class PbEnumConstantDefImpl extends PbPsiElementImpl implements PbEnumConstantDefinition {
    public PbEnumConstantDefImpl(ASTNode node){
        super(node);
    }

    @Override
    public void accept(ProtobufPsiElementVisitor visitor) {
        visitor.visitEnumConstantDefinition(this);
    }

    public String toString(){
        return "enum constant definition";
    }      
}
