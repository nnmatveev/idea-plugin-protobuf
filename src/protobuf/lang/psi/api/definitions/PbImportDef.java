package protobuf.lang.psi.api.definitions;

import com.intellij.psi.PsiReference;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.definitions.PbDefinition;

/**
 * author: Nikolay Matveev
 * Date: Mar 10, 2010
 */
public interface PbImportDef extends PbDefinition {
    public PbFile getAliasedFile();
}
