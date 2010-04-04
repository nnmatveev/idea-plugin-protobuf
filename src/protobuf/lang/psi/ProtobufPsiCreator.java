package protobuf.lang.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import protobuf.lang.ProtobufElementTypes;
import protobuf.lang.psi.impl.*;
import protobuf.lang.psi.impl.blocks.PbBlockImpl;
import protobuf.lang.psi.impl.definitions.*;
import protobuf.lang.psi.impl.members.*;
import protobuf.lang.psi.impl.members.PbOptionAssigmentImpl;
import protobuf.lang.psi.impl.references.PbRefImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 9, 2010
 */
public class ProtobufPsiCreator implements ProtobufElementTypes {

    public static PsiElement createElement(ASTNode node){
        IElementType element = node.getElementType();

        if(element.equals(REF)) return new PbRefImpl(node);

        if(element.equals(TYPE_REF)) return new PbRefImpl(node);
        
        if(element.equals(IMPORT_DEF)) return new PbImportDefImpl(node);
        if(element.equals(IMPORT_REF)) return new PbRefImpl(node);

        if(element.equals(PACKAGE_DEF)) return new PbPackageDefImpl(node);
        if(element.equals(PACKAGE_REF)) return new PbRefImpl(node);

        if(element.equals(OPTION_DEF)) return new PbOptionDefImpl(node);
        if(element.equals(OPTION_NAME)) return new PbOptionNameImpl(node);
        if(element.equals(OPTION_REF)) return new PbRefImpl(node);
        if(element.equals(OPTION_REF_SEQ)) return new PbOptionRefSeqImpl(node);
        if(element.equals(OPTION_ASSIGMENT)) return new PbOptionAssigmentImpl(node);

        if(element.equals(MESSAGE_DEF)) return new PbMessageDefImpl(node);
        if(element.equals(MESSAGE_NAME)) return new PbNameImpl(node);
        if(element.equals(MESSAGE_BLOCK)) return new PbBlockImpl(node);
        
        if(element.equals(FIELD_DEF)) return new PbFieldDefImpl(node);
        //if(element.equals(FIELD_LABEL)) return new PbLabelImpl(node);
        if(element.equals(FIELD_TYPE)) return new PbFieldTypeImpl(node);
        if(element.equals(OPTION_LIST)) return new PbOptionListImpl(node);

        if(element.equals(GROUP_DEF)) return new PbGroupDefImpl(node);
        if(element.equals(GROUP_BLOCK)) return new PbBlockImpl(node);

        if(element.equals(ENUM_DEF)) return new PbEnumDefImpl(node);
        if(element.equals(ENUM_NAME)) return new PbNameImpl(node);
        if(element.equals(ENUM_BLOCK)) return new PbBlockImpl(node);
        if(element.equals(ENUM_CONST_DEF)) return new PbEnumConstantDefImpl(node);

        if(element.equals(SERVICE_DEF)) return new PbServiceDefImpl(node);
        if(element.equals(SERVICE_BLOCK)) return new PbBlockImpl(node);
        if(element.equals(SERVICE_METHOD_DEF)) return new PbServiceMethodDefImpl(node);
        if(element.equals(SERVICE_METHOD_BLOCK)) return new PbBlockImpl(node);

        if(element.equals(EXTEND_DEF)) return new PbExtendDefImpl(node);
        if(element.equals(EXTEND_BLOCK)) return new PbBlockImpl(node);

        if(element.equals(NAME)) return new PbNameImpl(node);
        //if(element.equals(VALUE)) return new ValueImpl(node);

        
        //---
        return new PbPsiElementImpl(node);
    }
}
