package protobuf.lang.psi.api.declaration;

import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.PbPsiElement;

/**
 * author: Nikolay Matveev
 * Date: Mar 10, 2010
 */
public interface PbImportDef extends PbPsiElement {
    public PbFile getAliasedFile();
}
