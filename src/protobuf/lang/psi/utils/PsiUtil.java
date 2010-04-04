package protobuf.lang.psi.utils;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.*;
import com.intellij.psi.tree.TokenSet;
import protobuf.lang.psi.api.*;
import protobuf.lang.psi.api.auxiliary.PbBlockHolder;
import protobuf.lang.psi.api.blocks.PbBlock;
import protobuf.lang.psi.api.definitions.*;
import protobuf.lang.psi.api.references.PbRef;
import protobuf.lang.psi.impl.PbFileImpl;

import java.util.ArrayList;

import static protobuf.lang.ProtobufElementTypes.ENUM_DEF;
import static protobuf.lang.ProtobufElementTypes.GROUP_DEF;
import static protobuf.lang.ProtobufElementTypes.MESSAGE_DEF;

/**
 * author: Nikolay Matveev
 * Date: Mar 9, 2010
 */
public class PsiUtil {

    private final static Logger LOG = Logger.getInstance(PsiUtil.class.getName());   

    public static PbFile[] EMPTY_FILE_ARRAY = new PbFile[0];

    public static PsiPackage[] EMPTY_PACKAGE_ARRAY = new PsiPackage[0];

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

    //new methods

    public static PsiElement getRootScope(final PsiElement element) {
        return JavaPsiFacade.getInstance(element.getManager().getProject()).findPackage("");
    }

    public static PsiElement getUpperScope(final PsiElement element) {
        if (element instanceof PsiPackage) {
            return ((PsiPackage) element).getParentPackage();            
        }
        if (element instanceof PbFile) {
            JavaPsiFacade facade = JavaPsiFacade.getInstance(element.getManager().getProject());
            return facade.findPackage(((PbFile) element).getPackageName());
        }
        if (element instanceof PbPsiElement) {
            PbPsiElement scope = (PbPsiElement) element.getParent();
            int i = 0;
            while (scope != null && !(scope instanceof PbFile) && !(scope instanceof PbBlock)) {
                if(i==98){
                    LOG.info("upper scope: \n" + scope.getText());
                    assert false;
                }
                if(scope == scope.getParent()) assert false;
                scope = (PbPsiElement) scope.getParent();
                i++;
            }
            return scope;
        }
        assert false;
        return null;
    }

    public static PsiElement getScope(final PsiElement element) {
        if (element instanceof PbBlockHolder) {
            return ((PbBlockHolder) element).getBlock();
        }
        if (element instanceof PbFile) {
            return element;
        }
        if (element instanceof PsiPackage) {
            return element;
        }
        assert false;
        return null;
    }

    public static PbFile[] getImportedFiles(PbFile file, boolean onlyAliased) {
        PbImportDef[] importDefs = file.getImportDefinitions();
        ArrayList<PbFile> importFiles = new ArrayList<PbFile>(importDefs.length);
        for (PbImportDef importDef : importDefs) {
            PbFile aliasedFile = importDef.getAliasedFile();
            if (aliasedFile != null || !onlyAliased) {
                importFiles.add(aliasedFile);
            }

        }
        if (importFiles.size() == 0) {
            return EMPTY_FILE_ARRAY;
        }
        return importFiles.toArray(new PbFileImpl[importFiles.size()]);
    }

    public static PbFile[] getImportedFiles(PbFile file, String packageName) {
        PbImportDef[] importDefs = file.getImportDefinitions();
        ArrayList<PbFile> importFiles = new ArrayList<PbFile>(importDefs.length);
        for (PbImportDef importDef : importDefs) {
            PbFile aliasedFile = importDef.getAliasedFile();
            if (packageName.equals(aliasedFile)) {
                importFiles.add(aliasedFile);
            }
        }
        if (importFiles.size() == 0) {
            return EMPTY_FILE_ARRAY;
        }
        return importFiles.toArray(new PbFileImpl[importFiles.size()]);
    }           

    public static boolean isSamePackage(PbFile file, PsiPackage psiPackage) {
        return psiPackage.getQualifiedName().equals(file.getPackageName());
    }
}
