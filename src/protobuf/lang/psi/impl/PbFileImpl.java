package protobuf.lang.psi.impl;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import protobuf.file.ProtobufFileType;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.PbAssignable;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.PbPsiScope;
import protobuf.lang.psi.api.definitions.PbImportDef;
import protobuf.lang.psi.api.definitions.PbPackageDef;
import protobuf.lang.psi.utils.PbPsiScopeBuilder;
import protobuf.lang.psi.utils.PsiUtil;

import java.util.ArrayList;

/**
 * author: Nikolay Matveev
 * Date: Mar 9, 2010
 */
public class PbFileImpl extends PsiFileBase implements PbFile {

    private final static Logger LOG = Logger.getInstance(PbFileImpl.class.getName());

    //public static PbImportDef[] EMPTY_IMPORT_DEFS = new PbImportDefImpl[0];
    //public static PbMessageDef[] EMPTY_MESSAGE_DEFS = new PbMessageDefImpl[0];


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

    @Override
    public PbFile[] getImportedFiles(boolean onlyAliased) {
        PbImportDef[] importDefs = getImportDefinitions();
        ArrayList<PbFile> importFiles = new ArrayList<PbFile>(importDefs.length);
        for (PbImportDef importDef : importDefs) {
            PbFile aliasedFile = importDef.getAliasedFile();
            if (aliasedFile != null || !onlyAliased) {
                importFiles.add(aliasedFile);
            }

        }
        return importFiles.toArray(new PbFile[importFiles.size()]);
    }

    @Override
    public PbFile[] getImportedFilesByPackageName(String packageName) {
        PbFile[] importedFiles = getImportedFiles(true);
        ArrayList<PbFile> suitableImportedFiles = new ArrayList<PbFile>();
        for (PbFile file : importedFiles) {
            if (file.getPackageName().equals(packageName)) {
                suitableImportedFiles.add(file);
            }
        }
        return suitableImportedFiles.toArray(new PbFile[suitableImportedFiles.size()]);
    }

    @Override
    public boolean isPackageImported(String packageName) {
        return getImportedFilesByPackageName(packageName).length > 0 ? true : false;
    }

    @Override
    public PbPsiScope getScope() {
        PbPsiScopeBuilder scopeBuilder = new PbPsiScopeBuilder();
        //all inner elements
        scopeBuilder.append(findChildrenByClass(PbAssignable.class));        
        //all inner elements in visible part of package
        scopeBuilder.extractAndAppend(getImportedFilesByPackageName(getPackageName()));
        //current package
        scopeBuilder.append(PsiUtil.getContainingPackage(this));
        //imported packages
        scopeBuilder.append(PsiUtil.getImportedSubPackages(this));
        //imported packages
        scopeBuilder.append(PsiUtil.getImportedPackages(this));
        return scopeBuilder.getScope();

    }
}
