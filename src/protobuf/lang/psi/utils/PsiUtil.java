package protobuf.lang.psi.utils;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.*;
import protobuf.lang.psi.api.PbAssignable;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.PbPackage;
import protobuf.lang.psi.api.PbPsiScope;
import protobuf.lang.psi.api.references.PbRef;
import protobuf.lang.psi.api.references.PbTypeRef;
import protobuf.lang.resolve.PbResolveResult;
import protobuf.lang.resolve.ResolveUtil;

import java.util.ArrayList;

/**
 * author: Nikolay Matveev
 * Date: Mar 9, 2010
 */
public class PsiUtil {

    private final static Logger LOG = Logger.getInstance(PsiUtil.class.getName());

    public static PbAssignable[] EMPTY_ASSIGNABLE = new PbAssignable[0];

    public static String getQualifiedReferenceText(PbRef ref) {
        StringBuilder sbuilder = new StringBuilder();
        if (!appendName(ref, sbuilder)) {
            return null;
        }
        return sbuilder.toString();
    }

    private static boolean appendName(PbRef ref, StringBuilder builder) {
        String refName = ref.getReferenceName();
        if (refName == null) {
            return false;
        }
        PbRef qualifier = (PbRef) ref.getQualifier();
        if (qualifier != null) {
            appendName(qualifier, builder);
            builder.append(".");
        }
        builder.append(refName);
        return true;
    }

    public static PbFile[] getProtoFilesInPackage(PsiPackage psiPackage, PbFile protoFile) {
        PsiElement[] files = psiPackage.getChildren();
        ArrayList<PbFile> fileAccumulator = new ArrayList<PbFile>();
        for (PsiElement file : files) {
            if (file instanceof PbFile) {
                fileAccumulator.add((PbFile) file);
            }
        }
        return fileAccumulator.toArray(new PbFile[fileAccumulator.size()]);
    }

    public static PbPsiPackageWrapper[] getImportedSubPackages(PsiPackage psiPackage, PbFile protoFile) {
        PsiPackage[] subPackages = psiPackage.getSubPackages();
        ArrayList<PbPsiPackageWrapper> importedSubPackages = new ArrayList<PbPsiPackageWrapper>();
        for (PsiPackage subPackage : subPackages) {
            if (protoFile.isPackageImported(subPackage.getQualifiedName())) {
                importedSubPackages.add(new PbPsiPackageWrapper(subPackage));
            }
        }
        LOG.info("subpackages size: " + importedSubPackages.size());
        return importedSubPackages.toArray(new PbPsiPackageWrapper[importedSubPackages.size()]);
    }

    public static PbPsiPackageWrapper[] getImportedSubPackages(PbFile protoFile) {
        JavaPsiFacade facade = JavaPsiFacade.getInstance(protoFile.getProject());
        PsiPackage psiPackage = facade.findPackage(protoFile.getPackageName());
        if (psiPackage == null) {
            LOG.info("package null");
            return new PbPsiPackageWrapper[0];
        }
        return getImportedSubPackages(psiPackage, protoFile);
    }

    public static PbPsiPackageWrapper getCurrentPackage(PbFile protoFile) {
        JavaPsiFacade facade = JavaPsiFacade.getInstance(protoFile.getProject());
        PsiPackage psiPackage = facade.findPackage(protoFile.getPackageName());
        if (psiPackage == null) {
            return null;
        }
        return new PbPsiPackageWrapper(psiPackage);
    }

    public static PbPsiPackageWrapper[] wrapPsiPackages(PsiPackage[] psiPackages) {
        PbPsiPackageWrapper[] wrappedPackages = new PbPsiPackageWrapper[psiPackages.length];
        for (int i = 0; i < psiPackages.length; i++) {
            wrappedPackages[i] = new PbPsiPackageWrapper(psiPackages[i]);
        }
        return wrappedPackages;
    }


}
