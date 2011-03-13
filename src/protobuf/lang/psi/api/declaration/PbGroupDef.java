package protobuf.lang.psi.api.declaration;

import com.intellij.psi.PsiNamedElement;
import protobuf.lang.psi.api.PbPsiElement;
import protobuf.lang.psi.api.auxiliary.PbBlockHolder;
import protobuf.lang.psi.api.auxiliary.PbNamedElement;

/**
 * @author Nikolay Matveev
 * Date: Apr 3, 2010
 */

public interface PbGroupDef extends PbPsiElement, PbNamedElement, PbBlockHolder{

    String getFieldName();
}
