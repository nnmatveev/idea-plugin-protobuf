package protobuf.lang.psi.impl.references;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import protobuf.lang.psi.api.references.PbOptionRef;

/**
 * author: Nikolay Matveev
 * Date: Apr 1, 2010
 */
public class PbOptionRefImpl extends PbRefImpl implements PbOptionRef {

    private final static Logger LOG = Logger.getInstance(PbOptionRefImpl.class.getName());

    //private static final OurResolver ourResolver = new OurResolver();

    public PbOptionRefImpl(ASTNode node) {
        super(node);
    }

    public String toString() {
        return "Option reference element";
    }

    @Override
    public PsiElement getQualifier() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TextRange getRangeInElement() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PsiElement resolve() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getCanonicalText() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
