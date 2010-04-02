package protobuf.lang.psi.utils;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPackage;
import protobuf.lang.psi.api.*;
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
        //getSubPackages
        //getFilesInPackage
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public PbPsiScope getVisibleScope(PbFile protoFile) {
        PbPsiScopeBuilder scopeBuilder = new PbPsiScopeBuilder();        
        scopeBuilder.extractAndAppend(protoFile.getImportedFilesByPackageName(myPsiPackage.getQualifiedName()));
        scopeBuilder.append(protoFile.getScope());
        scopeBuilder.append(PsiUtil.getImportedSubPackages(myPsiPackage, protoFile));        
        return scopeBuilder.getScope();
    }
}
