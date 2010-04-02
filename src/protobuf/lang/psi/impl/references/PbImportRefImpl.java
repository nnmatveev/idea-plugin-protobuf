package protobuf.lang.psi.impl.references;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import protobuf.lang.ProtobufTokenTypes;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.references.PbImportRef;
import protobuf.lang.util.TextUtil;

/**
 * author: Nikolay Matveev
 * Date: Mar 26, 2010
 */
public class PbImportRefImpl extends PbRefImpl implements PbImportRef {

    private final static Logger LOG = Logger.getInstance(PbImportRefImpl.class.getName());

    public PbImportRefImpl(ASTNode node) {
        super(node);
    }

    public String toString() {
        return "Import reference element";
    }

    public PsiElement getElement() {
        return this;
    }

    public TextRange getRangeInElement() {
        return new TextRange(0, getTextLength());
    }

    public PsiElement resolve() {
        VirtualFile vfile = getProject().getBaseDir().findFileByRelativePath(getCanonicalText());
        if (vfile != null) {
            PsiFile pfile = getManager().findFile(vfile);
            return pfile;
        }
        return null;
    }

    public String getCanonicalText() {
        return TextUtil.trim(getText(), '"');
    }

    public boolean isReferenceTo(PsiElement psiElement) {
        if (psiElement instanceof PbFile) {
            return getManager().areElementsEquivalent(psiElement, resolve());
        }
        return false;
    }

    @NotNull
    public Object[] getVariants() {
        return new Object[0];
    }

    @Override
    public PsiElement getQualifier() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getReferenceName() {
        /*List<PsiElement> textChildren = findChildrenByType(ProtobufTokenTypes.IK);
        StringBuilder sbuilder  = new StringBuilder();
        boolean isFirst = true;
        for(PsiElement element : textChildren){
            if(!isFirst)
            sbuilder.append(element.getText());
        }*/
        PsiElement psi = findChildByType(ProtobufTokenTypes.IK);
        return psi != null ? psi.getText() : null;
    }
}
