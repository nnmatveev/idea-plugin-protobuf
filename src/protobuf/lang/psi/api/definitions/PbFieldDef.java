package protobuf.lang.psi.api.definitions;

import com.intellij.psi.PsiNamedElement;
import protobuf.lang.psi.api.PbAssignable;

import static protobuf.lang.psi.api.members.PbFieldType.Type;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public interface PbFieldDef extends PbBlockDefinition, PsiNamedElement, PbAssignable{

    public boolean isGroup();

    public Label getLabel();

    public Type getType();

    enum Label {
        REQUIRED,
        REPEATED,
        OPTIONAL
    }
}
