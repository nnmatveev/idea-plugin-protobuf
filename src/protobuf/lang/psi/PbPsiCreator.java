package protobuf.lang.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import protobuf.lang.PbElementTypes;
import protobuf.lang.psi.impl.PbPsiElementImpl;
import protobuf.lang.psi.impl.block.PbBlockImpl;
import protobuf.lang.psi.impl.declaration.*;
import protobuf.lang.psi.impl.member.*;
import protobuf.lang.psi.impl.reference.PbRefImpl;

/**
 * @author Nikolay Matveev
 */
public class PbPsiCreator implements PbElementTypes {

    public static PsiElement createElement(ASTNode node) {
        final IElementType type = node.getElementType();
        if (type == CUSTOM_TYPE_REF) {
            return new PbRefImpl(node);
        }
        if (type == IMPORT_DECL) {
            return new PbImportDefImpl(node);
        }
        if (type == IMPORT_REF) {
            return new PbRefImpl(node);
        }
        if (type == SYNTAX_DECL) {
            return new PbSyntaxDefImpl(node);
        }
        if (type == PACKAGE_DECL) {
            return new PbPackageDefImpl(node);
        }
        if (type == PACKAGE_REF) {
            return new PbRefImpl(node);
        }
        if (type == OPTION_LIST) {
            return new PbOptionListImpl(node);
        }
        if (type == OPTION_REF) {
            return new PbRefImpl(node);
        }
        if (type == OPTION_REF_SEQ) {
            return new PbOptionRefSeqImpl(node);
        }
        if (type == OPTION_ASSIGNMENT) {
            return new PbOptionAssignmentImpl(node);
        }
        if (type == MESSAGE_DECL) {
            return new PbMessageDefImpl(node);
        }
        if (type == MESSAGE_BLOCK) {
            return new PbBlockImpl(node);
        }
        if (type == FIELD_DECL) {
            return new PbFieldDefImpl(node);
        }
        if (type == FIELD_TYPE) {
            return new PbFieldTypeImpl(node);
        }
        if (type == GROUP_DECL) {
            return new PbGroupDefImpl(node);
        }
        if (type == ENUM_DECL) {
            return new PbEnumDefImpl(node);
        }
        if (type == ENUM_BLOCK) {
            return new PbBlockImpl(node);
        }
        if (type == ENUM_CONST_DECL) {
            return new PbEnumConstantDefImpl(node);
        }
        if (type == ONEOF_DECL) {
            return new PbOneofDefImpl(node);
        }
        if (type == ONEOF_BLOCK) {
            return new PbBlockImpl(node);
        }
        if (type == ONEOF_MEMBER_TYPE) {
            return new PbFieldTypeImpl(node);
        }
        if (type == ONEOF_MEMBER_DECL) {
            return new PbFieldDefImpl(node);
        }
        if (type == SERVICE_DECL) {
            return new PbServiceDefImpl(node);
        }
        if (type == SERVICE_BLOCK) {
            return new PbBlockImpl(node);
        }
        if (type == SERVICE_METHOD_DECL) {
            return new PbServiceMethodDefImpl(node);
        }
        if (type == SERVICE_METHOD_BLOCK) {
            return new PbBlockImpl(node);
        }
        if (type == EXTEND_DECL) {
            return new PbExtendDefImpl(node);
        }
        if (type == EXTEND_BLOCK) {
            return new PbBlockImpl(node);
        }
        if (type == VALUE) {
            return new PbValueImpl(node);
        }
        return new PbPsiElementImpl(node);
    }
}
