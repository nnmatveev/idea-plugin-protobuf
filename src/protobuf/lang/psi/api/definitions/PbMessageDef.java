package protobuf.lang.psi.api.definitions;

import com.intellij.psi.PsiNamedElement;
import protobuf.lang.psi.api.PbAssignable;
import protobuf.lang.psi.api.PbPsiScopeHolder;
import protobuf.lang.psi.api.definitions.PbBlockDefinition;
import protobuf.lang.psi.api.definitions.PbToplevelDefinition;

/**
 * author: Nikolay Matveev
 * Date: Mar 11, 2010
 */
public interface PbMessageDef extends PbToplevelDefinition, PbBlockDefinition, PsiNamedElement, PbPsiScopeHolder, PbAssignable {
    @Override
    String getName();
}
