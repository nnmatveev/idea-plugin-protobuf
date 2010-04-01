package protobuf.lang.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.PbPsiElement;

/**
 * author: Nikolay Matveev
 * Date: Mar 9, 2010
 */
public class PbPsiElementImpl extends ASTWrapperPsiElement implements PbPsiElement {
    public PbPsiElementImpl(ASTNode node){
        super(node);        
    }


    public void accept(ProtobufPsiElementVisitor visitor) {
        
    }
}
