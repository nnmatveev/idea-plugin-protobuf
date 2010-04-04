package protobuf.lang.psi.impl;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import protobuf.file.ProtobufFileType;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.definitions.*;

import java.util.ArrayList;

/**
 * author: Nikolay Matveev
 * Date: Mar 9, 2010
 */
public class PbFileImpl extends PsiFileBase implements PbFile {

    private final static Logger LOG = Logger.getInstance(PbFileImpl.class.getName());

    public PbFileImpl(FileViewProvider viewProvider) {
        super(viewProvider, ProtobufFileType.PROTOBUF_FILE_TYPE.getLanguage());
    }

    @Override
    public FileType getFileType() {
        return ProtobufFileType.PROTOBUF_FILE_TYPE;
    }

    @Override
    public void accept(ProtobufPsiElementVisitor visitor) {
        visitor.visitFile(this);
    }

    @Override
    public String toString() {
        return "protobuf file";
    }

    @Override
    public PbPackageDef getPackageDefinition() {
        return findChildByClass(PbPackageDef.class);
    }

    @Override
    public String getPackageName() {
        final PbPackageDef packageDef = getPackageDefinition();
        return packageDef != null ? packageDef.getPackageName() : "";
    }

    @Override
    public PbImportDef[] getImportDefinitions() {
        return findChildrenByClass(PbImportDef.class);
    }

    public PbFile[] getImportedFiles(boolean onlyAliased) {
        PbImportDef[] importDefs = getImportDefinitions();
        ArrayList<PbFile> importFiles = new ArrayList<PbFile>(importDefs.length);
        for (PbImportDef importDef : importDefs) {
            PbFile aliasedFile = importDef.getAliasedFile();
            if (aliasedFile != null || !onlyAliased) {
                importFiles.add(aliasedFile);
            }

        }
        return importFiles.toArray(new PbFileImpl[importFiles.size()]);
    }

    public PbFileImpl[] getImportedFilesByPackageName(String packageName) {
        PbFile[] importedFiles = getImportedFiles(true);
        ArrayList<PbFile> suitableImportedFiles = new ArrayList<PbFile>();
        for (PbFile file : importedFiles) {
            if (file.getPackageName().equals(packageName)) {
                suitableImportedFiles.add(file);
            }
        }
        return suitableImportedFiles.toArray(new PbFileImpl[suitableImportedFiles.size()]);
    }
}
