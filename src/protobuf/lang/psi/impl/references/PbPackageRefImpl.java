package protobuf.lang.psi.impl.references;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import protobuf.lang.ProtobufElementTypes;
import protobuf.lang.psi.utils.PsiUtil;
import protobuf.lang.psi.api.references.PbPackageRef;

/**
 * author: Nikolay Matveev
 * Date: Mar 30, 2010
 */
public class PbPackageRefImpl extends PbRefImpl implements PbPackageRef {

    private final static Logger LOG = Logger.getInstance(PbPackageRefImpl.class.getName());

    private static final OurResolver ourResolver = new OurResolver();

    public PbPackageRefImpl(ASTNode node) {
        super(node);
    }

    public String toString() {
        return "Package reference element";
    }       

    @Override
    public TextRange getRangeInElement() {
        return new TextRange(0, getTextLength());
    }

    @Override
    public PsiElement resolve() {
        return getManager().getResolveCache().resolveWithCaching(this, ourResolver, true, false);
    }

    @Override
    public String getCanonicalText() {
        return null;
    }

    @Override
    public PsiElement getQualifier() {
        return findChildByType(ProtobufElementTypes.PACKAGE_REF);
    }

    public static class OurResolver implements ResolveCache.Resolver {
        @Override
        public PsiElement resolve(PsiReference refElement, boolean b) {
            PbPackageRef ref = (PbPackageRef) refElement;
            String qualifiedName = PsiUtil.getQualifiedReferenceText(ref);
            LOG.info("package qualified name: "+qualifiedName);
            JavaPsiFacade facade = JavaPsiFacade.getInstance(ref.getManager().getProject());            
            return facade.findPackage(qualifiedName);
        }
    }
}