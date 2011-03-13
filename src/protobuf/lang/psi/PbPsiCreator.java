package protobuf.lang.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import protobuf.lang.PbElementTypes;
import protobuf.lang.psi.impl.*;
import protobuf.lang.psi.impl.block.PbBlockImpl;
import protobuf.lang.psi.impl.declaration.*;
import protobuf.lang.psi.impl.member.*;
import protobuf.lang.psi.impl.member.PbOptionAssignmentImpl;
import protobuf.lang.psi.impl.reference.PbRefImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 9, 2010
 */
public class PbPsiCreator implements PbElementTypes {

    public static PsiElement createElement(ASTNode node){
        IElementType element = node.getElementType();

        if(element.equals(CUSTOM_TYPE_REF)) return new PbRefImpl(node);
        
        if(element.equals(IMPORT_DECL)) return new PbImportDefImpl(node);
        if(element.equals(IMPORT_REF)) return new PbRefImpl(node);

        if(element.equals(PACKAGE_DECL)) return new PbPackageDefImpl(node);
        if(element.equals(PACKAGE_REF)) return new PbRefImpl(node);

        if(element.equals(OPTION_LIST)) return new PbOptionListImpl(node);
        if(element.equals(OPTION_REF)) return new PbRefImpl(node);
        if(element.equals(OPTION_REF_SEQ)) return new PbOptionRefSeqImpl(node);
        if(element.equals(OPTION_ASSIGNMENT)) return new PbOptionAssignmentImpl(node);

        if(element.equals(MESSAGE_DECL)) return new PbMessageDefImpl(node);
        if(element.equals(MESSAGE_BLOCK)) return new PbBlockImpl(node);
        
        if(element.equals(FIELD_DECL)) return new PbFieldDefImpl(node);
        if(element.equals(FIELD_TYPE)) return new PbFieldTypeImpl(node);

        if(element.equals(GROUP_DECL)) return new PbGroupDefImpl(node);

        if(element.equals(ENUM_DECL)) return new PbEnumDefImpl(node);
        if(element.equals(ENUM_BLOCK)) return new PbBlockImpl(node);
        if(element.equals(ENUM_CONST_DECL)) return new PbEnumConstantDefImpl(node);

        if(element.equals(SERVICE_DECL)) return new PbServiceDefImpl(node);
        if(element.equals(SERVICE_BLOCK)) return new PbBlockImpl(node);
        if(element.equals(SERVICE_METHOD_DECL)) return new PbServiceMethodDefImpl(node);
        if(element.equals(SERVICE_METHOD_BLOCK)) return new PbBlockImpl(node);

        if(element.equals(EXTEND_DECL)) return new PbExtendDefImpl(node);
        if(element.equals(EXTEND_BLOCK)) return new PbBlockImpl(node);
        
        if(element.equals(VALUE)) return new PbValueImpl(node);

        
        //---
        return new PbPsiElementImpl(node);
    }
}
