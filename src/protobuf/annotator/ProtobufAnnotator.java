package protobuf.annotator;

import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import protobuf.highlighter.DefaultHighlighter;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.PbPsiElement;
import protobuf.lang.psi.api.definitions.*;
import protobuf.lang.psi.api.definitions.PbEnumConstantDefinition;
import protobuf.lang.psi.api.references.PbRef;
import protobuf.util.PbBundle;
//import protobuf.lang.psi.impl.PbPsiElement;

import java.util.ArrayList;

/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public class ProtobufAnnotator extends ProtobufPsiElementVisitor implements Annotator {

    private final static Logger LOG = Logger.getInstance(ProtobufAnnotator.class.getName());

    private AnnotationHolder myHolder;

    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        myHolder = annotationHolder;
        if (psiElement instanceof PbPsiElement) {
            ((PbPsiElement) psiElement).accept(this);
        }
        myHolder = null;
    }

    //todo: check that the tag value is not in [19000, 19999]
    //todo: check for the biggegst and smallest number: 1 - 536,870,911
    //todo: For historical reasons, repeated fields of basic numeric types aren't encoded as efficiently as they could be. New code should use the special option [packed=true] to get a more efficient encoding
    //todo: fix for group definition http://code.google.com/intl/ru/apis/protocolbuffers/docs/proto.html#nested
    //inspections: http://code.google.com/intl/ru/apis/protocolbuffers/docs/proto.html#scalar

    @Override
    public void visitImportDefinition(PbImportDef element) {
    }

    @Override
    public void visitFieldDefinition(PbFieldDef field) {
        if (field.isGroup()) {
            myHolder.createWarningAnnotation(field, "this feature is deprecated and should not be used when creating new message types – use nested message types instead");
        }
    }

    public void visitEnumConstantDefinition(PbEnumConstantDefinition element) {
        ASTNode nameNode = element.getNode().getFirstChildNode();
        if (nameNode != null) {
            myHolder.createInfoAnnotation(nameNode, null).setTextAttributes(DefaultHighlighter.ENUM_CONSTANT_ATTR_KEY);
        }
    }


    //todo: add check that all field have unique number tag
    //todo: required: a well-formed message must have exactly one of this field.

    @Override
    public void visitMessageDefinition(PbMessageDef message) {
        restoreNameHighlighting(message);
        checkForWellformed(message);
    }

    @Override
    public void visitRef(PbRef element) {
        if(element.resolve() == null){
            myHolder.createInfoAnnotation(element.getNode(), PbBundle.message("unresolved.reference")).setTextAttributes(DefaultHighlighter.ERROR_INFO_ATTR_KEY);
        }        
    }

    private void checkForWellformed(PbMessageDef message) {
        PbFieldDef[] fields = message.getFields();
        ArrayList<PbFieldDef> requiredFields = new ArrayList<PbFieldDef>();
        ArrayList<PbFieldDef> optionalFields = new ArrayList<PbFieldDef>();
        for (PbFieldDef field : fields) {
            switch (field.getLabel()) {
                case REQUIRED: {
                    requiredFields.add(field);
                    break;
                }
                case OPTIONAL: {
                    optionalFields.add(field);
                    break;
                }
            }
        }
        if (requiredFields.size() > 1) {
            for (PbFieldDef field : requiredFields) {
                myHolder.createWarningAnnotation(field, "a well-formed message must have exactly one of this field");
            }
        }
        if (optionalFields.size() > 1) {
            for (PbFieldDef field : optionalFields) {
                myHolder.createWarningAnnotation(field, "a well-formed message can have zero or one of this field (but not more than one)");
            }
        }
    }

    private void restoreNameHighlighting(PsiElement element) {
        //ASTNode nameNode = element.getNode().findChildByType(NAME);
        //if (nameNode != null) {
        //    ASTNode nameNodeChild = nameNode.findChildByType(IK);
        //    if(nameNodeChild!=null && !nameNodeChild.getElementType().equals(IDENTIFIER)){
        //        myHolder.createInfoAnnotation(nameNodeChild,null).setTextAttributes(DefaultHighlighter.TEXT_ATTR_KEY);
        //    }
        //}
    }

    @Override
    public void visitFile(PsiFile file) {
        super.visitFile(file);    //To change body of overridden methods use File | Settings | File Templates.
        PbFile pbFile = (PbFile)file;
        LOG.info("package name: " + pbFile.getPackageName());
        //todo: complete
    }
}
