package protobuf.findUsages;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiPackage;
import org.jetbrains.annotations.NotNull;
import protobuf.lang.lexer.PbMergingLexer;
import protobuf.lang.psi.api.declaration.*;

import static protobuf.lang.ProtobufElementTypes.*;

/**
 * author: Nikolay Matveev
 */
public class PbFindUsagesProvider implements FindUsagesProvider {

    private final static Logger LOG = Logger.getInstance(PbFindUsagesProvider.class.getName());

    @Override
    public WordsScanner getWordsScanner() {
        return new DefaultWordsScanner(new PbMergingLexer(),IK,COMMENTS,STRING_LITERALS);
    }

    @Override
    public boolean canFindUsagesFor(@NotNull PsiElement element) {
        return element instanceof PbMessageDef  ||
                element instanceof PbEnumDef    ||
                element instanceof PbFieldDef   ||
                element instanceof PbGroupDef;                
    }

    @Override
    public String getHelpId(@NotNull PsiElement psiElement) {
        return null;
    }

    @NotNull
    @Override
    public String getType(@NotNull PsiElement element) {
        if(element instanceof PbMessageDef) return "message";
        if(element instanceof PbEnumDef) return "enum";
        if(element instanceof PbFieldDef) return "field";
        if(element instanceof PbGroupDef) return "group";        
        return null;
    }

    @NotNull
    @Override
    public String getDescriptiveName(@NotNull PsiElement element) {         
        return ((PsiNamedElement)element).getName();
    }

    @NotNull
    @Override
    public String getNodeText(@NotNull PsiElement psiElement, boolean useFullName) {
        if(!useFullName){
            return ((PsiNamedElement)psiElement).getName();
        }
        //todo [medium] complete
        return ((PsiNamedElement)psiElement).getName();
    }

}
