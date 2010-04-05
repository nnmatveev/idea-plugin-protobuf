package protobuf.lang.psi.api.definitions;

import com.intellij.psi.PsiNamedElement;
import protobuf.lang.psi.api.auxiliary.PbBlockHolder;
import protobuf.lang.psi.api.references.PbRef;

/**
 * author: Nikolay Matveev
 * Date: Apr 3, 2010
 */

public interface PbGroupDef extends PbBlockDefinition, PsiNamedElement, PbBlockHolder {

    String getLowerCaseName();
}
