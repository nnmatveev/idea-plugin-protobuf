package protobuf.lang.psi.impl.members;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.api.members.PbOptionAssigment;
import protobuf.lang.psi.api.members.PbOptionList;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 16, 2010
 */
public class PbOptionListImpl extends PbPsiElementImpl implements PbOptionList {
    public PbOptionListImpl(ASTNode node){
        super(node);
    }

    public String toString(){
        return "Options list";
    }

    public PbOptionAssigment[] getOptions() {
        return findChildrenByClass(PbOptionAssigmentImpl.class);
    }
}