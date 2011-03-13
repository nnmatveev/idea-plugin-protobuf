package protobuf.lang.psi.utils;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import protobuf.file.PbFileType;
import protobuf.lang.psi.api.*;
import protobuf.lang.psi.api.auxiliary.PbBlockHolder;
import protobuf.lang.psi.api.block.PbBlock;
import protobuf.lang.psi.api.declaration.*;
import protobuf.lang.psi.api.reference.PbRef;

import static protobuf.lang.PbElementTypes.*;

import java.util.ArrayList;

/**
 * author: Nikolay Matveev
 * Date: Mar 9, 2010
 */
public abstract class PbPsiUtil {

    private final static Logger LOG = Logger.getInstance(PbPsiUtil.class.getName());

    public static PsiElement[] EMPTY_PSI_ARRAY = new PsiElement[0];

    public static PbFile[] EMPTY_FILE_ARRAY = new PbFile[0];

    public static PsiPackage[] EMPTY_PACKAGE_ARRAY = new PsiPackage[0];

    public static void treeWalkUp(PsiScopeProcessor processor, PsiElement curElement) {

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

    public static boolean sameType(PsiElement element, IElementType type) {
        return type.equals(element.getNode().getElementType());
    }

    public static PsiElement getChild(final PsiElement parent, int position, boolean ignoreWhiteSpaces, boolean ignoreComments, boolean ignoreErrors) {
        PsiElement curChild = parent.getFirstChild();
        int i = 0;
        while (i <= position && curChild != null) {
            if (WHITE_SPACES.contains(curChild.getNode().getElementType())) {
                if (!ignoreWhiteSpaces) i++;
                curChild = curChild.getNextSibling();
            } else if (COMMENTS.contains(curChild.getNode().getElementType())) {
                if (!ignoreComments) i++;
                curChild = curChild.getNextSibling();
            } else if (curChild instanceof PsiErrorElement) {
                if (!ignoreErrors) {
                    return null;
                }
                i++;
                curChild = curChild.getNextSibling();
            } else {
                if (i == position) return curChild;
                i++;
                curChild = curChild.getNextSibling();
            }
        }
        return null;
    }

    public static ASTNode createSimpleNodeWithText(String text, Project project){
        assert !text.contains(" "):"name cannot contain white spaces";
        PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(project);
        PbFile dummyFile = (PbFile)psiFileFactory.createFileFromText("DUMMY_SET_NAME", PbFileType.PROTOBUF_FILE_TYPE,"message " + text + " {}");
        PbMessageDef dummyMessage = (PbMessageDef)PbPsiUtil.getChild(dummyFile,0,true,true,false);
        PsiElement newNameElement = dummyMessage.getNameElement();
        return newNameElement.getNode();
    }

 /*   public static String getQualifiedName(PsiNamedElement element){
            
    }
    */

    //public static getContext

    //resolving methods

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
            while (scope != null && !(scope instanceof PbFile) && !(scope instanceof PbBlock)) {
                scope = (PbPsiElement) scope.getParent();
            }
            if (scope instanceof PbFile) {
                JavaPsiFacade facade = JavaPsiFacade.getInstance(scope.getManager().getProject());
                return facade.findPackage(((PbFile) scope).getPackageName());
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

    public static PsiElement getTypeScope(final PsiElement element) {
        if (element instanceof PbFieldDef) {
            PbRef typeRef = ((PbFieldDef) element).getTypeRef();
            if (typeRef != null) {
                PsiElement resolvedElement = typeRef.resolve();
                if (resolvedElement != null) {
                    return getScope(resolvedElement);
                }
            }
            return null;
        }
        if (element instanceof PbGroupDef) {
            return ((PbGroupDef) element).getBlock();
        }
        if (element instanceof PbExtendDef) {
            return ((PbExtendDef) element).getBlock();
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
        return importFiles.toArray(new PbFile[importFiles.size()]);
    }

    public static PbFile[] getImportedFilesByPackageName(PbFile file, @NotNull String packageName) {
        PbImportDef[] importDefs = file.getImportDefinitions();
        ArrayList<PbFile> importFiles = new ArrayList<PbFile>(importDefs.length);
        for (PbImportDef importDef : importDefs) {
            PbFile aliasedFile = importDef.getAliasedFile();
            if (aliasedFile != null && packageName.equals(aliasedFile.getPackageName())) {
                importFiles.add(aliasedFile);
            }
        }
        if (importFiles.size() == 0) {
            return EMPTY_FILE_ARRAY;
        }
        return importFiles.toArray(new PbFile[importFiles.size()]);
    }

    public static boolean isVisibleSubPackage(PsiPackage subPackage, PbFile curFile) {
        String qSubPackageName = subPackage.getQualifiedName();
        if (curFile.getPackageName().startsWith(qSubPackageName)) {
            return true;
        }
        PbFile[] importedFiles = getImportedFiles(curFile, true);
        for (PbFile importedFile : importedFiles) {
            if (importedFile.getPackageName().startsWith(qSubPackageName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSamePackage(PbFile file, PsiPackage psiPackage) {
        return psiPackage.getQualifiedName().equals(file.getPackageName());
    }
}
