package protobuf.lang.parser.parsing.definitions;

import com.intellij.lang.PsiBuilder;
import protobuf.lang.ProtobufElementTypes;
import protobuf.lang.parser.util.PatchedPsiBuilder;

/**
 * author: Nikolay Matveev
 * Date: Mar 10, 2010
 */

//  grammar - ok
//  PbMessageDef ::= 'message' IDENTIFIER messageBlock
//  messageBlock ::= '{' (messageStatement)* '}'
//  messageStatement ::= (PbMessageDef | PbEnumDef | PbExtendDef | PbOptionDef | extensions | messageField | ';')*
//  extensions ::= 'extensions' extensionsRange(','extensionsRange)* ';'
//	extensionsRange ::= (NUM_INT 'to' ('max'|NUM_INT))
//  messageField ::= fieldLabel 'group' IDENTIFIER '=' NUM_INT (fieldOptions)* messageBlock ';'    
//  messageField ::= fieldLabel fieldType IDENTIFIER '=' NUM_INT (fieldOptions)? ';'
//  fieldLabel ::= 'required' | 'repeated' | 'optional'
//  fieldType ::= BUILT_IN_TYPE | userDefinedType
//  fieldOptions ::= '[' (defaultAssigment | optionAssigment)(','(defaultAssigment|optionAssigment)) ']'
//  defaultAssigment ::= 'default' = STRING_LITERALS
//  userDefinedType ::= '.'? IDENTIFIER('.'IDENTIFIER)*    

//done
public class MessageDefinition implements ProtobufElementTypes {
    public static boolean parse(PatchedPsiBuilder builder) {
        if (!builder.compareToken(MESSAGE)) {
            return false;
        }
        PsiBuilder.Marker messageMarker = builder.mark();
        builder.match(MESSAGE);
        builder.matchAs(IK,MESSAGE_NAME,"identifier.expected");
        if (!parseMessageBlock(builder)) {
            builder.error("message.block.expected");
        }
        messageMarker.done(MESSAGE_DEF);
        return true;
    }

    //done

    public static boolean parseMessageBlock(PatchedPsiBuilder builder) {
        if (!builder.compareToken(OPEN_BLOCK)) {
            return false;
        }
        PsiBuilder.Marker blockMarker = builder.mark();
        builder.match(); //matching OPEN_BLOCK
        while (!builder.eof() && !builder.compareToken(CLOSE_BLOCK)) {
            if (!parseMessageStatement(builder)) {
                builder.eatError("unexpected.token");
            }
        }
        builder.match(CLOSE_BLOCK, "close.block.expected");
        blockMarker.done(MESSAGE_BLOCK);
        return true;
    }

    //done

    public static boolean parseMessageStatement(PatchedPsiBuilder builder) {
        //PsiBuilder.Marker statementMarker = builder.mark();
        if (builder.match(SEMICOLON)) {
        } else if (MessageDefinition.parse(builder)) {

        } else if (EnumDefinition.parse(builder)) {
            
        } else if (ExtendDefinition.parse(builder)) {

        } else if (OptionDefinition.parse(builder)) {
            
        } else if (parseExtensions(builder)) {

        } else if (FieldDefinition.parse(builder)) {            
        } else {
            //statementMarker.drop();
            return false;
        }
        //statementMarker.done(MESSAGE_STATEMENT);
        return true;
    }

    //done

    public static boolean parseExtensions(PatchedPsiBuilder builder) {
        if (!builder.compareToken(EXTENSIONS)) {
            return false;
        }
        PsiBuilder.Marker extMarker = builder.mark();
        builder.match(EXTENSIONS);
        PsiBuilder.Marker rangeMarker;
        boolean isRangeMatched;

        do {
            isRangeMatched = false;
            rangeMarker = builder.mark();
            if (!builder.match(NUM_INT, "num.integer.expected")) {
                rangeMarker.drop();
                break;
            }
            builder.match(NUM_INT);
            if (!builder.match(TO)) {
            } else if (!builder.match(NUM_INT) && !builder.match(MAX)) {
            } else {
                isRangeMatched = true;
            }
            rangeMarker.done(EXTENSIONS_RANGE);
        } while (!builder.eof() && builder.match(COMMA));

        if (isRangeMatched) builder.match(SEMICOLON, "semicolon.expected");
        extMarker.done(EXTENSIONS_DEF);
        return true;
    }

    //done
}
