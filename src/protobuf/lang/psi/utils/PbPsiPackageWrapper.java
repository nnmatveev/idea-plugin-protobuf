package protobuf.lang.psi.utils;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPackage;
import protobuf.lang.psi.api.*;
import protobuf.lang.psi.api.references.PbRef;
import protobuf.lang.psi.impl.PbFileImpl;
import protobuf.lang.resolve.ResolveUtil;

/**
 * author: Nikolay Matveev
 * Date: Mar 31, 2010
 */
public class PbPsiPackageWrapper implements PbPackage {
    private final static Logger LOG = Logger.getInstance(PbPsiPackageWrapper.class.getName());

    private PsiPackage myPsiPackage;

    public PbPsiPackageWrapper(PsiPackage psiPackage) {
        myPsiPackage = psiPackage;
    }

    @Override
    public String getName() {
        LOG.info("PbPsiPackageWrapper name: " + myPsiPackage.getName());
        return myPsiPackage.getName();
    }

    @Override
    public String getQualifiedName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PsiElement getAim() {
        return myPsiPackage;
    }

    @Override
    public PbPsiScope getScope() {
        assert false;
        //todo [medium] complete
        //getSubPackages
        //getFilesInPackage
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public PbPsiScope getVisibleScope(PbFileImpl protoFile) {
        /*PbPsiScopeBuilder scopeBuilder = new PbPsiScopeBuilder();
        scopeBuilder.extractAndAppend(protoFile.getImportedFilesByPackageName(myPsiPackage.getQualifiedName()));
        scopeBuilder.append(protoFile.getScope());
        scopeBuilder.append(protoFile.getImportedSubPackages(myPsiPackage, protoFile));
        return scopeBuilder.getScope();*/
        return new PbPsiPackageWrapperScope(protoFile);
    }

    class PbPsiPackageWrapperScope implements PbPsiScope {

        PbFileImpl myFile;

        public PbPsiPackageWrapperScope(PbFileImpl file) {
            myFile = file;
        }

        @Override
        public PbAssignable[] getElementsInScope(PbRef.ReferenceKind kind) {
            switch (kind) {
                case DIRECTORY: {
                    assert false;
                }
                break;
                case PACKAGE: {
                    assert false;
                }
                break;
                case MESSAGE:
                case MESSAGE_OR_ENUM:
                case MESSAGE_OR_PACKAGE: {
                    PbPsiScopeBuilder scopeBuilder = new PbPsiScopeBuilder();
                    if (myPsiPackage.getQualifiedName().equals(myFile.getPackageName())) {
                        scopeBuilder.append(myFile.getScope(), kind);
                    } else {
                        PbFileImpl[] importedFiles = myFile.getImportedFilesByPackageName(myPsiPackage.getQualifiedName());
                        for (PbFileImpl file : importedFiles) {
                            scopeBuilder.append(file.getInnerScope(), kind);
                        }
                    }
                    return scopeBuilder.getElements();
                }
                case EXTEND_FIELD_INSIDE: {
                    if (myPsiPackage.getQualifiedName().equals(myFile.getPackageName())) {
                        PbPsiScopeBuilder scopeBuilder = new PbPsiScopeBuilder();
                        scopeBuilder.append(myFile.getScope(), kind);
                        return scopeBuilder.getElements();
                    }
                }
                break;
                case EXTEND_FIELD: {
                    assert false;
                }
                case MESSAGE_FIELD: {
                    assert false;
                }
            }
            return PbAssignable.EMPTY_ASSIGNABLE_ARRAY;
        }
    }

}
