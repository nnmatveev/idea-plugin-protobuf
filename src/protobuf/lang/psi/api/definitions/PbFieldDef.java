package protobuf.lang.psi.api.definitions;

import com.intellij.psi.PsiNamedElement;

import static protobuf.lang.psi.PbPsiEnums.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public interface PbFieldDef extends PbBlockDefinition, PsiNamedElement{

    public FieldLabel getLabel();

    public FieldType getType();

}
