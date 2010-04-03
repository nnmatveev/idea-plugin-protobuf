package protobuf.lang.psi.impl;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiPackage;
import protobuf.file.ProtobufFileType;
import protobuf.lang.psi.PbPsiEnums;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.PbAssignable;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.PbPsiScope;
import protobuf.lang.psi.api.definitions.*;
import protobuf.lang.psi.utils.PbPsiPackageWrapper;
import protobuf.lang.psi.utils.PbPsiScopeBuilder;

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

    @Override
    public PbPsiScope getScope() {
        return new PbFileScope(false, true);
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

    /**
     * public boolean isPackageImported(String packageName) {
     * return getImportedFilesByPackageName(packageName).length > 0 ? true : false;
     * }
     */

    public static PbPsiPackageWrapper getContainingPackage(PbFile protoFile) {
        JavaPsiFacade facade = JavaPsiFacade.getInstance(protoFile.getProject());
        PsiPackage psiPackage = facade.findPackage(protoFile.getPackageName());
        assert psiPackage != null;
        if (psiPackage == null) {
            LOG.info("containing package is null");
            return null;
        }
        LOG.info("containing package:" + psiPackage.getName());
        return new PbPsiPackageWrapper(psiPackage);
    }

    /**
     * public static PbPsiPackageWrapper[] getImportedSubPackages(PsiPackage psiPackage, PbFileImpl protoFile) {
     * PsiPackage[] subPackages = psiPackage.getSubPackages();
     * ArrayList<PbPsiPackageWrapper> importedSubPackages = new ArrayList<PbPsiPackageWrapper>();
     * for (PsiPackage subPackage : subPackages) {
     * if (protoFile.isPackageImported(subPackage.getQualifiedName())) {
     * importedSubPackages.add(new PbPsiPackageWrapper(subPackage));
     * }
     * }
     * LOG.info("subpackages size: " + importedSubPackages.size());
     * return importedSubPackages.toArray(new PbPsiPackageWrapper[importedSubPackages.size()]);
     * }
     * <p/>
     * public static PbPsiPackageWrapper[] getImportedSubPackages(PbFileImpl protoFile) {
     * JavaPsiFacade facade = JavaPsiFacade.getInstance(protoFile.getProject());
     * PsiPackage psiPackage = facade.findPackage(protoFile.getPackageName());
     * if (psiPackage == null) {
     * LOG.info("package null");
     * return new PbPsiPackageWrapper[0];
     * }
     * return getImportedSubPackages(psiPackage, protoFile);
     * }
     */

    public static PbPsiPackageWrapper[] getImportedPackages(PbFile protoFile) {
        PbFile[] importedFiles = protoFile.getImportedFiles(true);
        ArrayList<PbPsiPackageWrapper> importedPackages = new ArrayList<PbPsiPackageWrapper>();
        for (PbFile file : importedFiles) {
            importedPackages.add(getContainingPackage(file));
        }
        return importedPackages.toArray(new PbPsiPackageWrapper[importedPackages.size()]);
    }

    /**
     * public static PbPsiPackageWrapper[] getVisiblePackages(PbFile protoFile) {
     * PbFile[] importedFiles = protoFile.getImportedFiles(true);
     * ArrayList<PbPsiPackageWrapper> importedPackages = new ArrayList<PbPsiPackageWrapper>();
     * for(PbFile file : importedFiles){
     * importedPackages.add(getContainingPackage(file));
     * }
     * return importedPackages.toArray(new PbPsiPackageWrapper[importedPackages.size()]);
     * }
     */

    public PbPsiScope getInnerScope() {
        return new PbFileScope(true, false);
    }

    class PbFileScope implements PbPsiScope {

        boolean myInnerOnly;
        boolean myDirectInvocation;

        public PbFileScope(boolean innerOnly, boolean directInvocation) {
            myInnerOnly = innerOnly;
            myDirectInvocation = directInvocation;
        }

        @Override
        public PbAssignable[] getElementsInScope(PbPsiEnums.ReferenceKind kind) {
            return getElementsInScope(kind, myInnerOnly, myDirectInvocation);
        }


        /*public PbPsiScope getScope(boolean innerOnly) {
           PbPsiScopeBuilder scopeBuilder = new PbPsiScopeBuilder();
           //all inner elements
           scopeBuilder.append(findChildrenByClass(PbAssignable.class));
           //all inner elements in visible part of package
           scopeBuilder.extractAndAppend(getImportedFilesByPackageName(getPackageName()));
           //current package
           scopeBuilder.append(PsiUtil.getContainingPackage(this));
           //imported packages
           scopeBuilder.append(getImportedSubPackages(this));
           //imported packages
           scopeBuilder.append(PsiUtil.getImportedPackages(this));
           return scopeBuilder.getScope();

       } */

        public PbAssignable[] getElementsInScope(PbPsiEnums.ReferenceKind kind, boolean innerOnly, boolean directInvocation) {
            switch (kind) {
                case DIRECTORY:
                case PACKAGE: {
                    assert false;

                }
                break;
                case MESSAGE_OR_GROUP: {
                    PbPsiScopeBuilder scopeBuilder = new PbPsiScopeBuilder();
                    scopeBuilder.append(findChildrenByClass(PbMessageDef.class));
                    if (!innerOnly) {
                        PbFileImpl[] importedFiles = getImportedFilesByPackageName(getPackageName());
                        for (PbFileImpl file : importedFiles) {
                            scopeBuilder.append(((PbFileScope) file.getScope()).getElementsInScope(kind, true, false));
                        }
                    }
                    return scopeBuilder.getElements();
                }
                case MESSAGE_OR_ENUM_OR_GROUP: {
                    PbPsiScopeBuilder scopeBuilder = new PbPsiScopeBuilder();
                    scopeBuilder.append(findChildrenByClass(PbMessageDef.class));
                    scopeBuilder.append(findChildrenByClass(PbEnumDef.class));
                    if (!innerOnly) {
                        PbFileImpl[] importedFiles = getImportedFilesByPackageName(getPackageName());
                        for (PbFileImpl file : importedFiles) {
                            scopeBuilder.append(((PbFileScope) file.getScope()).getElementsInScope(kind, true, false));
                        }
                    }
                    return scopeBuilder.getElements();
                }
                case MESSAGE_OR_PACKAGE_OR_GROUP: {
                    PbPsiScopeBuilder scopeBuilder = new PbPsiScopeBuilder();
                    scopeBuilder.append(findChildrenByClass(PbMessageDef.class));
                    if (directInvocation) {
                        scopeBuilder.append(getContainingPackage(PbFileImpl.this));
                        scopeBuilder.append(getImportedPackages(PbFileImpl.this));
                    }
                    if (!innerOnly) {
                        PbFileImpl[] importedFiles = getImportedFilesByPackageName(getPackageName());
                        for (PbFileImpl file : importedFiles) {
                            scopeBuilder.append(((PbFileScope) file.getScope()).getElementsInScope(kind, true, false));
                        }
                    }
                    return scopeBuilder.getElements();
                }
                case EXTEND_FIELD_INSIDE: {
                    PbExtendDef[] extendDefs = findChildrenByClass(PbExtendDef.class);
                    LOG.info("extendDefs size: " + extendDefs.length);
                    if (extendDefs.length > 0) {
                        PbPsiScopeBuilder scopeBuilder = new PbPsiScopeBuilder();
                        scopeBuilder.extractAndAppend(extendDefs, PbPsiEnums.ReferenceKind.MESSAGE_OR_GROUP_FIELD);
                        return scopeBuilder.getElements();
                    }
                }
                break;
                case EXTEND_FIELD: {
                    assert false;
                }
                break;
            }
            return PbAssignable.EMPTY_ASSIGNABLE_ARRAY;
        }
    }
}
