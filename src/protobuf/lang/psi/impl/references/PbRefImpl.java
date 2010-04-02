package protobuf.lang.psi.impl.references;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import protobuf.lang.ProtobufElementTypes;
import protobuf.lang.ProtobufTokenTypes;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.PbPsiScopeHolder;
import protobuf.lang.psi.api.definitions.*;
import protobuf.lang.psi.api.members.PbFieldType;
import protobuf.lang.psi.api.members.PbOptionName;
import protobuf.lang.psi.api.references.PbRef;
import protobuf.lang.psi.impl.PbPsiElementImpl;
import protobuf.lang.psi.utils.PsiUtil;
import protobuf.lang.resolve.PbResolveResult;
import protobuf.lang.resolve.ResolveUtil;
import protobuf.lang.util.TextUtil;

import static protobuf.lang.ProtobufElementTypes.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 30, 2010
 */
public class PbRefImpl extends PbPsiElementImpl implements PbRef {

    private final static Logger LOG = Logger.getInstance(PbRefImpl.class.getName());

    private static final ExperimentalResolver expResolver = new ExperimentalResolver();

    @Override
    public String toString() {
        switch (getKind()) {
            case PACKAGE: {
                return "package reference";
            }
            case DIRECTORY: {
                return "directory reference";
            }
            case MESSAGE: {
                return "message reference";
            }
            case MESSAGE_OR_ENUM: {
                return "message or enum reference";
            }
            case MESSAGE_OR_PACKAGE: {
                return "message or package reference";
            }
            case MESSAGE_FIELD: {
                return "message field reference";
            }
            case EXTEND_FIELD: {
                return "extend field reference";
            }
            case EXTEND_FIELD_INSIDE: {
                return "extend field inside reference";
            }
        }
        assert false;
        return null;
    }

    public PbRefImpl(ASTNode node) {
        super(node);
    }

    @Override
    public void accept(ProtobufPsiElementVisitor visitor) {
        visitor.visitRef(this);
    }

    @Override
    public PsiReference getReference() {
        return this;
    }

    @Override
    public boolean isSoft() {
        return false;
    }

    @Override
    public PbRef getQualifier() {
        switch (getKind()) {
            case DIRECTORY: {
                return null;
            }
            case PACKAGE:
            case MESSAGE:
            case MESSAGE_OR_ENUM:
            case MESSAGE_OR_PACKAGE:
            case MESSAGE_FIELD:
            case EXTEND_FIELD_INSIDE:{
                return (PbRef) findChildByType(ProtobufElementTypes.REF);
            }
            case EXTEND_FIELD: {
                //todo check
                return (PbRef) getFirstChild();
            }
        }
        assert false;
        return null;
    }

    /*
    switch (getKind()) {
            case DIRECTORY: {
            }
            case PACKAGE: {
            }
            case MESSAGE: {
            }
            case MESSAGE_OR_ENUM: {

            }
            case MESSAGE_OR_PACKAGE: {

            }
            case MESSAGE_FIELD: {

            }
            case EXTEND_FIELD_INSIDE:
            case EXTEND_FIELD: {

            }
        }
     */


    @Override
    public String getReferenceName() {
        switch (getKind()) {
            case DIRECTORY: {
            }
            case PACKAGE: {
            }
            case MESSAGE: {
            }
            case MESSAGE_OR_ENUM: {

            }
            case MESSAGE_OR_PACKAGE: {

            }
            case MESSAGE_FIELD: {

            }
            case EXTEND_FIELD_INSIDE:
            case EXTEND_FIELD: {

            }
        }
        PsiElement psi = findChildByType(ProtobufTokenTypes.IK);
        return psi != null ? psi.getText() : null;
    }

    @Override
    public PsiElement getElement() {
        return this;
    }

    @Override
    public TextRange getRangeInElement() {
        return new TextRange(0, getTextLength());
    }

    @Override
    public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @Override
    public PsiElement handleElementRename(String s) throws IncorrectOperationException {
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
        ResolveResult[] results = getManager().getResolveCache().resolveWithCaching(this, expResolver, true, false);
        return results.length > 0 ? results[0].getElement() : null;
    }

    @Override
    public String getCanonicalText() {
        if (getKind().equals(ReferenceKind.DIRECTORY)) {
            return TextUtil.trim(getText(), '"');
        }
        return null;
    }


    public ReferenceKind getKind() {
        //todo caching kind
        PsiElement parent = getParent();
        if (parent instanceof PbRef) {
            ReferenceKind parentKind = ((PbRefImpl) parent).getKind();
            assert parentKind != null;
            switch (parentKind) {
                case DIRECTORY: {
                    return ReferenceKind.DIRECTORY;
                }
                case PACKAGE: {
                    return ReferenceKind.PACKAGE;
                }
                case MESSAGE: {
                    return ReferenceKind.MESSAGE_OR_PACKAGE;
                }
                case MESSAGE_OR_ENUM: {
                    return ReferenceKind.MESSAGE_OR_PACKAGE;
                }
                case MESSAGE_OR_PACKAGE: {
                    return ReferenceKind.MESSAGE_OR_PACKAGE;
                }
                case EXTEND_FIELD: {
                    if (findChildByType(CLOSE_PARANT) != null) {
                        return ReferenceKind.EXTEND_FIELD_INSIDE;
                    }
                    if (findChildByType(IK) == null) {
                        return ReferenceKind.EXTEND_FIELD;
                    }
                    return ReferenceKind.MESSAGE_FIELD;
                }
                case MESSAGE_FIELD: {
                    if (findChildByType(CLOSE_PARANT) != null) {
                        return ReferenceKind.EXTEND_FIELD_INSIDE;
                    }
                    if (findChildByType(IK) == null) {
                        return ReferenceKind.EXTEND_FIELD;
                    }
                    return ReferenceKind.MESSAGE_FIELD;
                }
                case EXTEND_FIELD_INSIDE: {
                    return ReferenceKind.MESSAGE_OR_PACKAGE;
                }
            }

        }
        if (parent instanceof PbExtendDef) {
            return ReferenceKind.MESSAGE;
        }
        if (parent instanceof PbFieldType) {
            return ReferenceKind.MESSAGE_OR_ENUM;
        }
        if (parent instanceof PbOptionName) {
            if (findChildByType(IK) != null) {
                return ReferenceKind.MESSAGE_FIELD;
            }
            return ReferenceKind.EXTEND_FIELD;
        }
        if (parent instanceof PbPackageDef) {
            return ReferenceKind.PACKAGE;
        }
        if (parent instanceof PbImportDef) {
            return ReferenceKind.DIRECTORY;
        }
        //todo 'service rpc', 'enum constants as value of options and fields', imports

        if (parent instanceof PbServiceMethodDef) {
            return ReferenceKind.MESSAGE;
        }
        LOG.info("reference: " + getText());
        assert false;
        return null;
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean b) {
        return PbResolveResult.EMPTY_RESULT;
    }

    public static class ExperimentalResolver implements ResolveCache.PolyVariantResolver<PbRefImpl> {

        public PbResolveResult[] resolve(PbRefImpl ref, boolean b) {
            final ReferenceKind refKind = ref.getKind();
            final String refName = ref.getName();
            final PbRef qualifier = ref.getQualifier();
            switch (refKind) {
                case DIRECTORY: {
                    LOG.info("directory: " + TextUtil.trim(ref.getText(),'"'));
                    VirtualFile vfile = ref.getProject().getBaseDir().findFileByRelativePath(TextUtil.trim(ref.getText(),'"'));
                    if (vfile != null) {
                        PsiFile pfile = ref.getManager().findFile(vfile);
                        ResolveUtil.wrapInResolveResult(pfile);
                    }
                    return null;
                }
                case PACKAGE: {
                    String qualifiedName = PsiUtil.getQualifiedReferenceText(ref);                    
                    JavaPsiFacade facade = JavaPsiFacade.getInstance(ref.getManager().getProject());
                    return ResolveUtil.wrapInResolveResult(facade.findPackage(qualifiedName));
                }
                case MESSAGE: {
                    if (qualifier != null) {
                        PsiElement resolvedElement = qualifier.resolve();
                        if(resolvedElement instanceof PbPsiScopeHolder){
                            //todo add kind parameter
                            return ResolveUtil.resolveInScopeByName((PbPsiScopeHolder)resolvedElement,refName);
                        }
                        assert false;
                    }
                }
                case MESSAGE_OR_ENUM: {

                }
                case MESSAGE_OR_PACKAGE: {

                }
                case MESSAGE_FIELD: {

                }
                case EXTEND_FIELD_INSIDE:
                case EXTEND_FIELD: {

                }

            }
            /*final ReferenceKind kind = refElement.getKind();
            final PbTypeRef qualifiedElement = refElement.getQualifier();
            final String refName = refElement.getReferenceName();
            final String qualifiedName = PsiUtil.getQualifiedReferenceText(refElement);
            switch (kind) {
                case MESSAGE_OR_PACKAGE_OR_ENUM: {
                    if (qualifiedElement != null) {
                        final PsiElement resolvedElement = qualifiedElement.resolve();
                        //bar.foo
                        if (resolvedElement != null) {
                            if (resolvedElement instanceof PsiPackage) {
                                PbPsiPackageWrapper pbPackage = new PbPsiPackageWrapper((PsiPackage) resolvedElement);
                                PbResolveResult[] results = ResolveUtil.resolveInScopeByName(pbPackage.getVisibleScope((PbFile) refElement.getContainingFile()), refName);
                                if (results != null) return results;
                            }
                            if (resolvedElement instanceof PbPsiScopeHolder) {
                                PbResolveResult[] results = ResolveUtil.resolveInScopeByName(((PbPsiScopeHolder) resolvedElement).getScope(), refName);
                                if (results != null) return results;
                            }
                        } else {
                            return PbResolveResult.EMPTY_RESULT;
                        }

                    } else if (refElement.findChildByType(DOT) != null) {
                        //.bar
                        //outerscope search
                        PbResolveResult[] results = ResolveUtil.resolveInScopeByName(((PbFile) refElement.getContainingFile()).getScope(), refName);
                        if (results != null) return results;
                    } else {
                        //bar
                        //innerscope and then outerscope search
                        final PbPsiScopeHolder scopeHolder = PsiUtil.getScopeHolderByElement(refElement);
                        PbResolveResult[] results = ResolveUtil.resolveInScopeByName(scopeHolder.getScope(), refName);
                        if (results != null) return results;
                        if (!(scopeHolder instanceof PbFile)) {
                            results = ResolveUtil.resolveInScopeByName(((PbFile) refElement.getContainingFile()).getScope(), refName);
                            if (results != null) return results;
                        }
                    }
                }
                break;
            } */
            return PbResolveResult.EMPTY_RESULT;
        }
    }
}
