package protobuf.lang.resolve;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;

import static protobuf.lang.psi.PbPsiEnums.*;

import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiPackage;
import protobuf.lang.psi.api.*;
import protobuf.lang.psi.api.block.PbBlock;
import protobuf.lang.psi.api.declaration.*;
import protobuf.lang.psi.api.reference.PbRef;
import protobuf.lang.psi.utils.PbPsiUtil;

/**
 * author: Nikolay Matveev
 * Date: Mar 29, 2010
 */
public abstract class PbResolveUtil {

    private final static Logger LOG = Logger.getInstance(PbResolveUtil.class.getName());    

    public static PsiElement resolveInScope(final PsiElement scope, final PbRef ref) {
        ReferenceKind kind = ref.getRefKind();
        String refName = ref.getReferenceName();
        if(refName == null){
            return null;
        }
        if (scope instanceof PsiPackage) {
            switch (kind) {
                case FILE:
                case PACKAGE: {

                }
                case MESSAGE_OR_GROUP_FIELD: {
                    assert false;
                }
                break;
                case MESSAGE_OR_GROUP:
                case MESSAGE_OR_ENUM_OR_GROUP:
                case EXTEND_FIELD: {
                    //get imported files by package name and invoke this function for this files
                    PbFile containingFile = (PbFile) ref.getContainingFile();
                    if (PbPsiUtil.isSamePackage(containingFile, (PsiPackage) scope)) {
                        PsiElement resolveResult = resolveInScope(containingFile, ref);
                        if (resolveResult != null) {
                            return resolveResult;
                        }
                    }
                    PbFile[] importedFiles = PbPsiUtil.getImportedFilesByPackageName(containingFile, ((PsiPackage) scope).getQualifiedName());
                    for (PbFile importedFile : importedFiles) {
                        PsiElement resolveResult = resolveInScope(importedFile, ref);
                        if (resolveResult != null) {
                            return resolveResult;
                        }
                    }
                }
                break;
                case MESSAGE_OR_PACKAGE_OR_GROUP: {
                    PbFile containingFile = (PbFile) ref.getContainingFile();
                    //resolve in subpackages scope
                    //alg: find subpackage and then find it in subpackages
                    PsiPackage subPackage = resolveInSubPackagesScope((PsiPackage) scope, refName);
                    if (subPackage != null) {
                        //f(subPackage, thisFile) -> boolean
                        //true if this subPackage in visible scope either false
                        if (PbPsiUtil.isVisibleSubPackage(subPackage, containingFile)) {
                            return subPackage;
                        }
                    }
                    //resolve in containing file
                    if (PbPsiUtil.isSamePackage(containingFile, (PsiPackage) scope)) {
                        PsiElement resolveResult = resolveInScope(containingFile, ref);
                        if (resolveResult != null) {
                            return resolveResult;
                        }
                    }
                    //resolve in imported files scope
                    PbFile[] importedFiles = PbPsiUtil.getImportedFilesByPackageName(containingFile, ((PsiPackage) scope).getQualifiedName());
                    for (PbFile importedFile : importedFiles) {
                        PsiElement resolveResult = resolveInScope(importedFile, ref);
                        if (resolveResult != null) {
                            return resolveResult;
                        }
                    }
                }
                break;
            }

        } else if (scope instanceof PbBlock || scope instanceof PbFile) {
            switch (kind) {
                case FILE:
                case PACKAGE: {
                    assert false;
                }
                case MESSAGE_OR_PACKAGE_OR_GROUP:
                case MESSAGE_OR_GROUP: {
                    PsiElement[] children = scope.getChildren();
                    for (PsiElement child : children) {
                        if (child instanceof PbMessageDef || (!(scope instanceof PbFile) && child instanceof PbGroupDef)) {
                            if (refName.equals(((PsiNamedElement) child).getName())) {
                                return child;
                            }
                        }
                    }
                    //LOG.info(refName + " : MESSAGE_OR_PACKAGE_OR_GROUP not resolved in " + (scope instanceof PbFile ? "PbFile" : ("PbBlock: " + ((PbBlock) scope).getParent().getText())));
                }
                break;
                case MESSAGE_OR_ENUM_OR_GROUP: {
                    PsiElement[] children = scope.getChildren();
                    for (PsiElement child : children) {
                        if (child instanceof PbMessageDef || child instanceof PbEnumDef || child instanceof PbGroupDef) {
                            if (refName.equals(((PsiNamedElement) child).getName())) {
                                return child;
                            }
                        }
                    }
                }
                break;
                case EXTEND_FIELD: {
                    PsiElement[] children = scope.getChildren();
                    for (PsiElement child : children) {
                        if (child instanceof PbExtendDef) {
                            PbBlock extendBlock = ((PbExtendDef) child).getBlock();
                            if (extendBlock == null) {
                                return null;
                            }
                            PsiElement[] extendChildren = extendBlock.getChildren();
                            for (PsiElement extendChild : extendChildren) {
                                if (extendChild instanceof PbFieldDef && refName.equals(((PbFieldDef) extendChild).getName())) {
                                    return extendChild;
                                } else if (extendChild instanceof PbGroupDef && refName.equals(((PbGroupDef) extendChild).getFieldName())) {
                                    return extendChild;
                                }
                            }
                        }
                    }
                }
                break;
                case MESSAGE_OR_GROUP_FIELD: {
                    if (scope instanceof PbFile) assert false;
                    PsiElement[] children = scope.getChildren();
                    for (PsiElement child : children) {
                        if (child instanceof PbFieldDef) {
                            if (refName.equals(((PsiNamedElement) child).getName())) {
                                return child;
                            }
                        } else if (child instanceof PbGroupDef && refName.equals(((PbGroupDef) child).getFieldName())) {
                            return child;
                        }
                    }
                }
                break;
            }
        }
        return null;
    }

    public static PsiPackage resolveInSubPackagesScope(final PsiPackage parentPackage, String refName) {
        PsiPackage[] subPackages = parentPackage.getSubPackages();
        for (PsiPackage subPackage : subPackages) {
            if (subPackage.getName().equals(refName)) {
                return subPackage;
            }
        }
        return null;
    }

}
