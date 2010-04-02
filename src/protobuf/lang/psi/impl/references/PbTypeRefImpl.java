package protobuf.lang.psi.impl.references;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import org.jetbrains.annotations.NotNull;
import protobuf.lang.ProtobufElementTypes;
import protobuf.lang.psi.api.*;
import protobuf.lang.psi.utils.PbPsiPackageWrapper;
import protobuf.lang.psi.utils.PsiUtil;
import protobuf.lang.psi.api.references.PbTypeRef;
import protobuf.lang.psi.api.definitions.*;
import protobuf.lang.resolve.PbResolveResult;
import protobuf.lang.resolve.ResolveUtil;

import static protobuf.lang.ProtobufElementTypes.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 22, 2010
 */

public class PbTypeRefImpl extends PbRefImpl implements PbTypeRef {

    private final static Logger LOG = Logger.getInstance(PbTypeRefImpl.class.getName());

    private static final OurResolver ourResolver = new OurResolver();

    public PbTypeRefImpl(ASTNode node) {
        super(node);
    }

    public String toString() {
        return "Reference element";
    }

    public PbTypeRef getQualifier() {
        return (PbTypeRef) findChildByType(ProtobufElementTypes.TYPE_REF);
    }

    public TextRange getRangeInElement() {
        final PsiElement refNameElement = findChildByType(IK);
        if (refNameElement != null) {
            final int offsetInParent = refNameElement.getStartOffsetInParent();
            return new TextRange(offsetInParent, offsetInParent + refNameElement.getTextLength());
        }
        return new TextRange(0, getTextLength());
    }

    public PsiElement resolve() {
        ResolveResult[] results = getManager().getResolveCache().resolveWithCaching(this, ourResolver, true, false);
        return results.length > 0 ? results[0].getElement() : null;
    }

    public String getCanonicalText() {
        return getText();
    }

    @NotNull
    public Object[] getVariants() {
        return new Object[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        return getManager().getResolveCache().resolveWithCaching(this, ourResolver, true, incompleteCode);
    }

    private ReferenceKind getKind() {
        PsiElement parent = getParent();
        if (parent instanceof PbOptionDef) {
            return ReferenceKind.OPTION;
        }
        return ReferenceKind.MESSAGE_OR_PACKAGE_OR_ENUM;
    }

    private enum ReferenceKind {
        MESSAGE_OR_PACKAGE_OR_ENUM,
        OPTION
    }

    public static class OurResolver implements ResolveCache.PolyVariantResolver<PbTypeRefImpl> {

        public PbResolveResult[] resolve(PbTypeRefImpl refElement, boolean b) {
            final ReferenceKind kind = refElement.getKind();
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
                            return PbResolveResult.EMPTY_ARRAY;
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
            }
            return PbResolveResult.EMPTY_ARRAY;
        }
    }
}
