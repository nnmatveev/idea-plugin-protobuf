package protobuf.lang.psi.api.definitions;

import com.intellij.psi.PsiNamedElement;
import protobuf.lang.psi.api.PbAssignable;
import protobuf.lang.psi.api.definitions.PbBlockDefinition;

/**
 * author: Nikolay Matveev
 * Date: Mar 11, 2010
 */
public interface PbEnumDef extends PbToplevelDefinition, PbBlockDefinition, PsiNamedElement, PbAssignable {    
}
