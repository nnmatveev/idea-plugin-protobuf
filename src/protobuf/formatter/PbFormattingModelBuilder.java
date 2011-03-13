package protobuf.formatter;

import com.intellij.formatting.FormattingModel;
import com.intellij.formatting.FormattingModelBuilder;
import com.intellij.formatting.FormattingModelProvider;
import com.intellij.formatting.Indent;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import protobuf.file.PbFileType;

/**
 * @author Nikolay Matveev
 * Date: Mar 12, 2010
 */
public class PbFormattingModelBuilder implements FormattingModelBuilder {

    public FormattingModel createModel(PsiElement psiElement, CodeStyleSettings codeStyleSettings) {
        ASTNode node = psiElement.getNode();
        assert node != null;
        PsiFile containingFile = psiElement.getContainingFile().getViewProvider().getPsi(PbFileType.PROTOBUF_LANGUAGE);
        assert containingFile != null : psiElement.getContainingFile();
        ASTNode fileNode = containingFile.getNode();
        assert fileNode != null;
        PbBlock block = new PbBlock(fileNode, null, Indent.getAbsoluteNoneIndent(), null, codeStyleSettings);
        return FormattingModelProvider.createFormattingModelForPsiFile(containingFile, block, codeStyleSettings);
    }

    public TextRange getRangeAffectingIndent(PsiFile psiFile, int i, ASTNode astNode) {
        return null;
    }
}
