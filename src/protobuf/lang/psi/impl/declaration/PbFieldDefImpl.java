package protobuf.lang.psi.impl.declaration;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import protobuf.lang.psi.PbPsiElementVisitor;
import protobuf.lang.psi.api.declaration.PbFieldDef;

import static protobuf.lang.psi.PbPsiEnums.*;
import static protobuf.lang.PbTokenTypes.*;

import protobuf.lang.psi.api.member.PbFieldType;
import protobuf.lang.psi.api.reference.PbRef;
import protobuf.lang.psi.impl.auxiliary.PbNamedElementImpl;
import protobuf.lang.psi.utils.PbPsiUtil;

/**
 * @author Nikolay Matveev
 * Date: Mar 12, 2010
 */
public class PbFieldDefImpl extends PbNamedElementImpl implements PbFieldDef {
    public PbFieldDefImpl(ASTNode node) {
        super(node);
    }

    @Override
    public void accept(PbPsiElementVisitor visitor) {
        visitor.visitFieldDefinition(this);
    }

    @Override
    public FieldLabel getLabel() {
        PsiElement firstChild = getFirstChild();
        IElementType firstChildType = firstChild.getNode().getElementType();
        if (firstChildType.equals(OPTIONAL)) {
            return FieldLabel.OPTIONAL;
        } else if (firstChildType.equals(REPEATED)) {
            return FieldLabel.REPEATED;
        } else {
            return FieldLabel.REQUIRED;
        }
    }

    @Override
    public FieldType getType() {
        PbFieldType fieldType = findChildByClass(PbFieldType.class);
        return fieldType.getType();
    }

    @Override
    public FieldType getConcreteType() {
        FieldType commonType = getType();
        switch (commonType) {
            case CUSTOM_TYPE: {
                return FieldType.CUSTOM_TYPE;
            }
            case BUILT_IN_TYPE: {
                //todo complete
            }
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PbRef getTypeRef() {
        PbFieldType fieldType = findChildByClass(PbFieldType.class);
        if (fieldType != null) {
            return fieldType.getTypeRef();
        }
        return null;
    }       

    @Override
    public PsiElement getNameElement() {        
        return PbPsiUtil.getChild(this,2,true,true,false);
    }
}
