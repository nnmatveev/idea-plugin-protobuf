package protobuf.lang.psi.api.definitions;

import com.intellij.psi.PsiNamedElement;
import protobuf.lang.psi.api.definitions.PbToplevelDefinition;

/**
 * author: Nikolay Matveev
 * Date: Mar 9, 2010
 */
public interface PbPackageDef extends PbToplevelDefinition{

    String getPackageName();
}
