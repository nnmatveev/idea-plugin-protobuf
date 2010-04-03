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
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.PbPsiElement;
import protobuf.lang.psi.api.PbPsiScopeHolder;
import protobuf.lang.psi.api.definitions.*;
import protobuf.lang.psi.api.members.PbFieldType;
import protobuf.lang.psi.api.members.PbOptionName;
import protobuf.lang.psi.api.references.PbRef;
import protobuf.lang.psi.impl.PbFileImpl;
import protobuf.lang.psi.impl.PbPsiElementImpl;
import protobuf.lang.psi.utils.PbPsiPackageWrapper;
import protobuf.lang.psi.utils.PbPsiScopeBuilder;
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
    public PbFile getContainingFile() {
        return (PbFile) super.getContainingFile();
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
            case MESSAGE_FIELD: {
                return findChildByClass(PbRef.class);
            }
            case EXTEND_FIELD_INSIDE: {
                return findChildByClass(PbRef.class);
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
            case EXTEND_FIELD_INSIDE:{
            }
            case EXTEND_FIELD: {

            }
        }
     */


    @Override
    public String getReferenceName() {
        switch (getKind()) {
            case DIRECTORY:
            case PACKAGE:
            case MESSAGE:
            case MESSAGE_OR_ENUM:
            case MESSAGE_OR_PACKAGE:
            case MESSAGE_FIELD:
            case EXTEND_FIELD_INSIDE: {
                PsiElement psi = findChildByType(ProtobufTokenTypes.IK);
                return psi != null ? psi.getText() : null;
            }
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
        switch (getKind()) {
            case DIRECTORY: {
            }
            case PACKAGE:
            case MESSAGE:
            case MESSAGE_OR_ENUM:
            case MESSAGE_OR_PACKAGE: {
                PsiElement refNameElement = findChildByType(IK);
                if (refNameElement != null) {
                    final int offsetInParent = refNameElement.getStartOffsetInParent();
                    return new TextRange(offsetInParent, offsetInParent + refNameElement.getTextLength());
                }

            }
            break;
            case MESSAGE_FIELD: {
                PsiElement refNameElement = findChildByType(IK);
                if (refNameElement != null) {
                    final int offsetInParent = refNameElement.getStartOffsetInParent();
                    return new TextRange(offsetInParent, offsetInParent + refNameElement.getTextLength());
                }
            }
            case EXTEND_FIELD_INSIDE: {

            }
            case EXTEND_FIELD: {

            }
        }
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
        return getManager().getResolveCache().resolveWithCaching(this, expResolver, true, false);
    }

    @Override
    public String getCanonicalText() {
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
            case EXTEND_FIELD_INSIDE: {
            }
            case EXTEND_FIELD: {

            }
        }
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
                    if (findChildByType(OPEN_PARANT) != null) {
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
            if (findChildByType(OPEN_PARANT) != null) {
                return ReferenceKind.EXTEND_FIELD_INSIDE;
            }
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
        //todo 'enum constants as value of options and fields', imports

        if (parent instanceof PbServiceMethodDef) {
            return ReferenceKind.MESSAGE;
        }
        LOG.info("reference: " + getText());
        assert false;
        return null;
    }

    public static class ExperimentalResolver implements ResolveCache.Resolver {

        public PsiElement resolve(PsiReference refElement, boolean b) {
            final PbRefImpl ref = (PbRefImpl) refElement;
            final ReferenceKind refKind = ref.getKind();
            final String refName = ref.getReferenceName();
            final PbRef qualifier = ref.getQualifier();
            switch (refKind) {
                case DIRECTORY: {
                    LOG.info("directory: " + TextUtil.trim(ref.getText(), '"'));
                    VirtualFile vfile = ref.getProject().getBaseDir().findFileByRelativePath(TextUtil.trim(ref.getText(), '"'));
                    if (vfile != null) {
                        PsiFile pfile = ref.getManager().findFile(vfile);
                        return pfile;
                    }
                    return null;
                }
                case PACKAGE: {
                    String qualifiedName = PsiUtil.getQualifiedReferenceText(ref);
                    JavaPsiFacade facade = JavaPsiFacade.getInstance(ref.getManager().getProject());
                    return facade.findPackage(qualifiedName);
                }
                case MESSAGE:
                case MESSAGE_OR_ENUM:
                case MESSAGE_OR_PACKAGE: {
                    if (qualifier != null) {
                        LOG.info("message or enum or package reference and qulifier != null: " + refName);
                        final PsiElement resolvedElement = qualifier.resolve();
                        if (resolvedElement != null) {
                            LOG.info("message or enum or package reference and qulifier != null and resolved: " + refName);
                            if (resolvedElement instanceof PbPsiScopeHolder) {
                                return ResolveUtil.resolveInScopeByName(((PbPsiScopeHolder) resolvedElement).getScope(), refName, refKind);
                            }
                            if (resolvedElement instanceof PsiPackage) {
                                PbPsiPackageWrapper pbPackage = new PbPsiPackageWrapper((PsiPackage) resolvedElement);
                                return ResolveUtil.resolveInScopeByName(pbPackage.getVisibleScope(((PbFileImpl) ref.getContainingFile())), refName, refKind);
                            }
                            assert false;
                        }
                    } else if (ref.findChildByType(DOT) != null) {
                        LOG.info("message or enum or package reference and qulifier == null and contained DOT: " + refName);
                        //only outerscope
                        return ResolveUtil.resolveInScopeByName(ref.getContainingFile().getScope(), refName, refKind);
                    } else {
                        LOG.info("message or enum or package reference and qulifier == null and not contained DOT: " + refName);
                        //innerscope and then outerscope
                        PbPsiScopeBuilder scopeBuilder = new PbPsiScopeBuilder();
                        final PbPsiScopeHolder scopeHolder = PsiUtil.getScopeHolderByElement(ref);
                        LOG.info(ref.getText());
                        scopeBuilder.append(scopeHolder.getScope(), refKind);
                        if (!(scopeHolder instanceof PbFile)) {
                            LOG.info("scopeHolder not instanceof PbFile");
                            scopeBuilder.append(ref.getContainingFile().getScope(), refKind);
                        }
                        return ResolveUtil.resolveInScopeByName(scopeBuilder.getElements(), refName);
                    }
                }
                break;
                case MESSAGE_FIELD: {
                    if (qualifier != null) {
                        LOG.info("message field and qualifier!=null: " + refName);
                        final PsiElement resolvedElement = qualifier.resolve();
                        //change resolving rules
                        if (resolvedElement != null) {
                            LOG.info("message field and qualifier resolved: " + refName);
                            if (resolvedElement instanceof PbPsiScopeHolder) {
                                return ResolveUtil.resolveInScopeByName(((PbPsiScopeHolder) resolvedElement).getScope(), refName, refKind);
                            }
                        }
                    } else {
                        LOG.info("message field and qualifier==null: " + refName);
                    }
                }
                break;
                case EXTEND_FIELD_INSIDE: {
                    if (qualifier != null) {
                        LOG.info("extend field inside and qualifier!=null: " + refName);
                        final PsiElement resolvedElement = qualifier.resolve();
                        if (resolvedElement != null) {
                            LOG.info("extend field inside and qualifier resolved: " + refName);
                            if (resolvedElement instanceof PsiPackage) {
                                PbPsiPackageWrapper pbPackage = new PbPsiPackageWrapper((PsiPackage)resolvedElement);
                                return ResolveUtil.resolveInScopeByName(pbPackage.getVisibleScope((PbFileImpl)ref.getContainingFile()), refName, refKind);

                            }
                            if (resolvedElement instanceof PbPsiScopeHolder) {
                                return ResolveUtil.resolveInScopeByName(((PbPsiScopeHolder) resolvedElement).getScope(), refName, refKind);
                            }
                            /*if(resolvedElement instanceof PbFieldDef){
                                
                            } */
                            //assert false;
                        }
                    } else {
                        LOG.info("extend field inside field and qualifier==null: " + refName);
                        return ResolveUtil.resolveInScopeByName(ref.getContainingFile().getScope(), refName, refKind);
                    }
                }
                break;
                case EXTEND_FIELD: {

                }
                break;

            }
            return null;
        }
    }
}
