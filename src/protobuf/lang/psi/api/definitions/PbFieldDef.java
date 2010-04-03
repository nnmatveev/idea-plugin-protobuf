package protobuf.lang.psi.api.definitions;

import com.intellij.psi.PsiNamedElement;
import protobuf.lang.psi.api.PbAssignable;

import static protobuf.lang.psi.PbPsiEnums.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public interface PbFieldDef extends PbBlockDefinition, PsiNamedElement, PbAssignable{

    public FieldLabel getLabel();

    public FieldType getType();

}
