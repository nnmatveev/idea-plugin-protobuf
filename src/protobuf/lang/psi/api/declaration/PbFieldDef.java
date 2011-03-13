package protobuf.lang.psi.api.declaration;

import com.intellij.psi.PsiNamedElement;
import protobuf.lang.psi.api.PbPsiElement;
import protobuf.lang.psi.api.auxiliary.PbNamedElement;
import protobuf.lang.psi.api.reference.PbRef;

import static protobuf.lang.psi.PbPsiEnums.*;

/**
 * @author Nikolay Matveev
 */
public interface PbFieldDef extends PbPsiElement, PbNamedElement {

    public FieldLabel getLabel();

    public FieldType getType();

    public FieldType getConcreteType();

    public PbRef getTypeRef();

}
