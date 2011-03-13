package protobuf.lang.psi.impl.reference;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import protobuf.lang.PbTokenTypes;

import static protobuf.lang.psi.PbPsiEnums.*;

import protobuf.lang.psi.PbPsiElementVisitor;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.declaration.*;
import protobuf.lang.psi.api.member.PbFieldType;
import protobuf.lang.psi.api.member.PbOptionRefSeq;
import protobuf.lang.psi.api.reference.PbRef;
import protobuf.lang.psi.impl.PbPsiElementImpl;
import protobuf.lang.psi.utils.PbPsiUtil;
import protobuf.util.PbFileUtil;
import protobuf.util.PbTextUtil;

import protobuf.lang.resolve.PbResolveUtil;

import java.io.File;

import static protobuf.lang.PbElementTypes.*;

/**
 * @author Nikolay Matveev
 * Date: Mar 30, 2010
 */
public class PbRefImpl extends PbPsiElementImpl implements PbRef {

    private final static Logger LOG = Logger.getInstance(PbRefImpl.class.getName());

    private static final ExperimentalResolver expResolver = new ExperimentalResolver();

    @Override
    public String toString() {
        switch (getRefKind()) {
            case PACKAGE: {
                return "PACKAGE_REF";
            }
            case FILE: {
                return "FILE_REF";
            }
            case MESSAGE_OR_GROUP: {
                return "MESSAGE_OR_GROUP_REF";
            }
            case MESSAGE_OR_ENUM_OR_GROUP: {
                return "MESSAGE_OR_ENUM_GROUP_REF";
            }
            case MESSAGE_OR_PACKAGE_OR_GROUP: {
                return "MESSAGE_OR_PACAKAGE_OR_GROUP_REF";
            }
            case MESSAGE_OR_GROUP_FIELD: {
                return "MESSAGE_OR_GROUP_FIELD_REF";
            }
            case EXTEND_FIELD: {
                return "EXTEND_FIELD_REF";
            }
        }
        assert false;
        return null;
    }

    public PbRefImpl(ASTNode node) {
        super(node);
    }

    @Override
    public void accept(PbPsiElementVisitor visitor) {
        visitor.visitRef(this);
    }

    @Override
    public PsiReference getReference() {
        return this;
    }

    @Override
    public PbFile getContainingFile() {
        return (PbFile) super.getContainingFile();
    }

    @Override
    public boolean isSoft() {
        return false;
    }

    @Override
    public PbRef getQualifier() {
        switch (getRefKind()) {
            case FILE: {
                return null;
            }
            case PACKAGE:
            case MESSAGE_OR_GROUP:
            case MESSAGE_OR_ENUM_OR_GROUP:
            case MESSAGE_OR_PACKAGE_OR_GROUP:
            case MESSAGE_OR_GROUP_FIELD:
            case EXTEND_FIELD: {
                return findChildByClass(PbRef.class);
            }
        }
        assert false;
        return null;
    }

    /*
    switch (getRefKind()) {
            case FILE: {
            }
            case PACKAGE: {
            }
            case MESSAGE_OR_GROUP: {
            }
            case MESSAGE_OR_ENUM_OR_GROUP: {

            }
            case MESSAGE_OR_PACKAGE_OR_GROUP: {

            }
            case MESSAGE_OR_GROUP_FIELD: {

            }
            case EXTEND_FIELD: {

            }
        }
     */


    @Override
    public String getReferenceName() {
        /*switch (getRefKind()) {
            case FILE:
            case PACKAGE:
            case MESSAGE_OR_GROUP:
            case MESSAGE_OR_ENUM_OR_GROUP:
            case MESSAGE_OR_PACKAGE_OR_GROUP:
            case MESSAGE_OR_GROUP_FIELD:            
            case EXTEND_FIELD: {
            }
        } */
        PsiElement psi = findChildByType(PbTokenTypes.IK);
        return psi != null ? psi.getText() : null;
    }

    @Override
    public PsiElement getElement() {
        return this;
    }

    @Override
    public TextRange getRangeInElement() {
        switch (getRefKind()) {
            case FILE: {
            }
            case PACKAGE:
            case MESSAGE_OR_GROUP:
            case MESSAGE_OR_ENUM_OR_GROUP:
            case MESSAGE_OR_PACKAGE_OR_GROUP:
            case MESSAGE_OR_GROUP_FIELD:
            case EXTEND_FIELD: {
                PsiElement refNameElement = findChildByType(IK);
                if (refNameElement != null) {
                    final int offsetInParent = refNameElement.getStartOffsetInParent();
                    return new TextRange(offsetInParent, offsetInParent + refNameElement.getTextLength());
                }

            }
            break;
        }
        return new TextRange(0, getTextLength());
    }

    @Override
    public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @Override
    public PsiElement handleElementRename(final String newName) throws IncorrectOperationException {
        PsiElement nameElement = getReferenceNameElement();
        if (nameElement != null) {
            if(getRefKind() == ReferenceKind.MESSAGE_OR_GROUP_FIELD && (resolve() instanceof PbGroupDef)){
                nameElement.getNode().getTreeParent().replaceChild(nameElement.getNode(),PbPsiUtil.createSimpleNodeWithText(newName.toLowerCase(),getProject()));
            }else{
                nameElement.getNode().getTreeParent().replaceChild(nameElement.getNode(),PbPsiUtil.createSimpleNodeWithText(newName,getProject()));
            }
            return this;
        }
        throw new IncorrectOperationException();
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement psiElement) throws IncorrectOperationException {
        throw new IncorrectOperationException();
    }

    @Override
    public boolean isReferenceTo(PsiElement psiElement) {
        return getManager().areElementsEquivalent(psiElement, resolve());
    }

    @Override
    public PsiElement resolve() {
        return getManager().getResolveCache().resolveWithCaching(this, expResolver, true, false);
    }

    @Override
    public String getCanonicalText() {
        switch (getRefKind()) {
            case FILE: {
            }
            case PACKAGE: {
            }
            case MESSAGE_OR_GROUP: {
            }
            case MESSAGE_OR_ENUM_OR_GROUP: {

            }
            case MESSAGE_OR_PACKAGE_OR_GROUP: {

            }
            case MESSAGE_OR_GROUP_FIELD: {

            }
            case EXTEND_FIELD: {

            }
        }
        if (getRefKind().equals(ReferenceKind.FILE)) {
            return PbTextUtil.trim(getText(), '"');
        }
        return null;
    }

    @Override
    public boolean isLastInChainReference() {
        return !(getParent() instanceof PbRef);
    }

    public PsiElement getReferenceNameElement() {
        switch (getRefKind()) {
            case PACKAGE:
            case MESSAGE_OR_GROUP:
            case MESSAGE_OR_ENUM_OR_GROUP:
            case MESSAGE_OR_PACKAGE_OR_GROUP:
            case MESSAGE_OR_GROUP_FIELD:
            case EXTEND_FIELD: {
                return findChildByType(PbTokenTypes.IK);
            }
        }
        return null;
    }

    public ReferenceKind getRefKind() {
        //todo caching kind
        PsiElement parent = getParent();
        if (parent instanceof PbRef) {
            ReferenceKind parentKind = ((PbRefImpl) parent).getRefKind();
            assert parentKind != null;
            switch (parentKind) {
                case FILE: {
                    return ReferenceKind.FILE;
                }
                case PACKAGE: {
                    return ReferenceKind.PACKAGE;
                }
                case MESSAGE_OR_GROUP: {
                    return ReferenceKind.MESSAGE_OR_PACKAGE_OR_GROUP;
                }
                case MESSAGE_OR_ENUM_OR_GROUP: {
                    return ReferenceKind.MESSAGE_OR_PACKAGE_OR_GROUP;
                }
                case MESSAGE_OR_PACKAGE_OR_GROUP: {
                    return ReferenceKind.MESSAGE_OR_PACKAGE_OR_GROUP;
                }
                case EXTEND_FIELD: {
                    return ReferenceKind.MESSAGE_OR_PACKAGE_OR_GROUP;
                }
                case MESSAGE_OR_GROUP_FIELD: {
                    if (findChildByType(CLOSE_PARANT) != null) {
                        return ReferenceKind.EXTEND_FIELD;
                    }
                    return ReferenceKind.MESSAGE_OR_GROUP_FIELD;
                }
                default: {
                    assert false;
                }
            }

        }
        if (parent instanceof PbExtendDef) {
            return ReferenceKind.MESSAGE_OR_GROUP;
        }
        if (parent instanceof PbFieldType) {
            return ReferenceKind.MESSAGE_OR_ENUM_OR_GROUP;
        }
        if (parent instanceof PbOptionRefSeq) {
            if (findChildByType(CLOSE_PARANT) != null) {
                return ReferenceKind.EXTEND_FIELD;
            }
            return ReferenceKind.MESSAGE_OR_GROUP_FIELD;
        }
        if (parent instanceof PbPackageDef) {
            return ReferenceKind.PACKAGE;
        }
        if (parent instanceof PbImportDef) {
            return ReferenceKind.FILE;
        }
        //todo 'enum constants as value of options and fields', advanced imports

        if (parent instanceof PbServiceMethodDef) {
            return ReferenceKind.MESSAGE_OR_GROUP;
        }
        return null;
    }

    public CompletionKind getCompletionKind() {
        //todo complete
        return null;
    }

    public static class ExperimentalResolver implements ResolveCache.Resolver {

        public PsiElement resolve(PsiReference refElement, boolean b) {
            final PbRefImpl ref = (PbRefImpl) refElement;
            final ReferenceKind refKind = ref.getRefKind();
            final PbRef qualifier = ref.getQualifier();
            switch (refKind) {
                case FILE: {
                    //todo
                    final String relativePath = PbTextUtil.trim(ref.getText(),'\"');                    
                    if (relativePath.length() == 0) {
                        return null;
                    }
                    if (PbFileUtil.isPathToDirectory(relativePath)) {
                        return null;
                    }
                    final String fileName = new File(relativePath).getName();

                    //hack for test code
                    PsiFile psiFile = null;
                    PsiFile[] foundFiles = FilenameIndex.getFilesByName(ref.getProject(), fileName, ref.getResolveScope());
                    for (PsiFile foundFile : foundFiles) {
                        if (foundFile.getVirtualFile().getPath().equals("/" + relativePath)) {
                            psiFile = foundFile;
                        }
                    }
                    if (psiFile != null) return psiFile; //todo avoid such hacking

                    //real code
                    VirtualFile vfile = ref.getProject().getBaseDir().findFileByRelativePath(relativePath);
                    if (vfile != null) {
                        return ref.getManager().findFile(vfile);
                    } else {
                        return null;
                    }
                }
                case PACKAGE: {
                    String qualifiedName = PbPsiUtil.getQualifiedReferenceText(ref);
                    JavaPsiFacade facade = JavaPsiFacade.getInstance(ref.getManager().getProject());
                    return facade.findPackage(qualifiedName);
                }
                case MESSAGE_OR_GROUP:
                case MESSAGE_OR_ENUM_OR_GROUP:
                case MESSAGE_OR_PACKAGE_OR_GROUP:
                case EXTEND_FIELD: {
                    if (qualifier != null) {    //foo.bar                        
                        final PsiElement resolvedElement = qualifier.resolve();
                        if (resolvedElement != null) {
                            return PbResolveUtil.resolveInScope(PbPsiUtil.getScope(resolvedElement), ref);
                        }
                    } else if (ref.findChildByType(DOT) != null) {  //.foo
                        return PbResolveUtil.resolveInScope(PbPsiUtil.getRootScope(ref), ref);
                    } else {    // foo
                        PsiElement upperScope = PbPsiUtil.getUpperScope(ref);
                        while (upperScope != null) {
                            PsiElement resolveResult = PbResolveUtil.resolveInScope(upperScope, ref);
                            if (resolveResult != null) {
                                return resolveResult;
                            }
                            upperScope = PbPsiUtil.getUpperScope(upperScope);
                        }
                    }
                }
                break;
                case MESSAGE_OR_GROUP_FIELD: {
                    if (qualifier != null) {
                        final PsiElement resolvedElement = qualifier.resolve();
                        if (resolvedElement != null) {
                            return PbResolveUtil.resolveInScope(PbPsiUtil.getTypeScope(resolvedElement), ref);
                        }
                    }
                }
                break;
            }
            return null;
        }
    }
}
