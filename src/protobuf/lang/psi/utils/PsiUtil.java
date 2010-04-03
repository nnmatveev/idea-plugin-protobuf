package protobuf.lang.psi.utils;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.*;
import protobuf.lang.psi.PbPsiEnums;
import protobuf.lang.psi.api.*;
import protobuf.lang.psi.api.definitions.PbExtendDef;
import protobuf.lang.psi.api.references.PbRef;

import java.util.ArrayList;

/**
 * author: Nikolay Matveev
 * Date: Mar 9, 2010
 */
public class PsiUtil {

    private final static Logger LOG = Logger.getInstance(PsiUtil.class.getName());

    public static PbAssignable[] EMPTY_ASSIGNABLE = new PbAssignable[0];

    public static PbPsiScope EMPTY_SCOPE = new EmptyScope(); 

    private static class EmptyScope implements PbPsiScope{
        @Override
        public PbAssignable[] getElementsInScope(PbPsiEnums.ReferenceKind kind) {
            return PbAssignable.EMPTY_ASSIGNABLE_ARRAY;
        }
    }

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

    public static PbPsiPackageWrapper[] wrapPsiPackages(PsiPackage[] psiPackages) {
        PbPsiPackageWrapper[] wrappedPackages = new PbPsiPackageWrapper[psiPackages.length];
        for (int i = 0; i < psiPackages.length; i++) {
            wrappedPackages[i] = new PbPsiPackageWrapper(psiPackages[i]);
        }
        return wrappedPackages;
    }

    public static PbPsiScope getScopeByElement(final PsiElement element){
        PsiElement scope = element;
        while(!(scope instanceof PbPsiScope)){
            scope = scope.getParent();
        }
        return (PbPsiScope)scope;
    }

    public static PbPsiScopeHolder getScopeHolderByElement(final PsiElement element){
        PsiElement scopeHolder = element;
        while(!(scopeHolder instanceof PbPsiScopeHolder) && scopeHolder != null){
            if(scopeHolder instanceof PbExtendDef){
                scopeHolder = scopeHolder.getParent(); //todo [hack] maybe there is a better way to avoid such problem
            }
            scopeHolder = scopeHolder.getParent();
        }
        assert scopeHolder != null;
        return (PbPsiScopeHolder)scopeHolder;
    }


}
