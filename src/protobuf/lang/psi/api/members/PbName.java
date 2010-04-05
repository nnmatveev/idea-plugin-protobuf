package protobuf.lang.psi.api.members;

import static protobuf.lang.psi.PbPsiEnums.*;
import protobuf.lang.psi.api.PbPsiElement;

/**
 * author: Nikolay Matveev
 * Date: Mar 29, 2010
 */
public interface PbName extends PbPsiElement {

    NameTokenType getNameTokenType();
}
