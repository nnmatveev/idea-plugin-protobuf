package protobuf.lang.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import protobuf.file.ProtobufFileElementType;
import protobuf.file.ProtobufFileType;
import protobuf.lang.ProtobufTokenTypes;
import protobuf.lang.lexer.PbMergingLexer;
import protobuf.lang.psi.ProtobufPsiCreator;
import protobuf.lang.psi.impl.PbFileImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 7, 2010
 */
public class ProtobufParserDefinition implements ParserDefinition {
    public static final IFileElementType PROTOBUF_FILE = new ProtobufFileElementType(ProtobufFileType.PROTOBUF_FILE_TYPE.getLanguage());


    public Lexer createLexer(Project project) {
        return new PbMergingLexer();
    }

    public PsiParser createParser(Project project) {
        return new ProtobufParser();
    }

    public IFileElementType getFileNodeType() {
        return PROTOBUF_FILE;
    }


    public TokenSet getWhitespaceTokens() {
        return ProtobufTokenTypes.WHITE_SPACES;
    }

    public TokenSet getCommentTokens() {
        return ProtobufTokenTypes.COMMENTS;
    }


    public TokenSet getStringLiteralElements() {
        return ProtobufTokenTypes.STRING_LITERALS;
    }

    public PsiElement createElement(ASTNode astNode) {
        return ProtobufPsiCreator.createElement(astNode);
    }


    public PsiFile createFile(FileViewProvider fileViewProvider) {
        return new PbFileImpl(fileViewProvider);
    }

    
    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode astNode, ASTNode astNode1) {
        return SpaceRequirements.MAY; //TODO: fix it        
    }
}
