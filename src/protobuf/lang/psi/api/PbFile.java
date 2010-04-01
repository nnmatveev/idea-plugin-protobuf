package protobuf.lang.psi.api;

import com.intellij.psi.PsiFile;
import protobuf.lang.psi.api.PbPsiElement;
import protobuf.lang.psi.api.definitions.PbImportDef;
import protobuf.lang.psi.api.definitions.PbPackageDef;

/**
 * author: Nikolay Matveev
 * Date: Mar 9, 2010
 */
public interface PbFile extends PsiFile, PbPsiElement, PbPsiScopeHolder {

    PbPackageDef getPackageDefinition();

    String getPackageName();

    PbImportDef[] getImportDefinitions();

    PbFile[] getImportedFiles(boolean checkForAliased);   

    PbFile[] getImportedFilesByPackageName(String packageName);

    boolean isPackageImported(String packageName);   

}
