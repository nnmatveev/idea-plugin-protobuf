package protobuf.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import protobuf.highlighter.DefaultHighlighter;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.PbPsiElement;
import protobuf.lang.psi.api.auxiliary.PbNamedElement;
import protobuf.lang.psi.api.declaration.*;
import protobuf.lang.psi.api.declaration.PbEnumConstantDef;
import protobuf.lang.psi.api.member.PbExtensionsRange;
import protobuf.lang.psi.api.member.PbOptionAssignment;
import protobuf.lang.psi.api.member.PbValue;
import protobuf.lang.psi.api.reference.PbRef;
import protobuf.util.PbBundle;
import protobuf.util.PbTextUtil;

import static protobuf.lang.psi.utils.PbPsiUtil.sameType;
import static protobuf.lang.ProtobufElementTypes.*;
import static protobuf.lang.psi.PbPsiEnums.*;


/**
 * author: Nikolay Matveev
 * Date: Mar 12, 2010
 */
public class PbAnnotator extends ProtobufPsiElementVisitor implements Annotator {


    private final static Logger LOG = Logger.getInstance(PbAnnotator.class.getName());

    private AnnotationHolder myHolder;

    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        myHolder = annotationHolder;
        if (psiElement instanceof PbPsiElement) {
            ((PbPsiElement) psiElement).accept(this);
        }
        myHolder = null;
    }

    @Override
    public void visitFile(PsiFile file) {
        super.visitFile(file);
    }

    @Override
    public void visitImportDefinition(PbImportDef element) {
    }

    @Override
    public void visitPackageDefinition(PbPackageDef element) {

    }


    //todo: check that the tag value is not in [19000, 19999]
    //todo: check for the biggegst and smallest number: 1 - 536,870,911
    //todo: For historical reasons, repeated fields of basic numeric types aren't encoded as efficiently as they could be. New code should use the special option [packed=true] to get a more efficient encoding
    //inspections: http://code.google.com/intl/ru/apis/protocolbuffers/docs/proto.html#scalar
    // Test message with CamelCase field names.  This violates Protocol Buffer standard style.
    @Override
    public void visitFieldDefinition(PbFieldDef field) {
        fixHighlighting(field);
    }

    @Override
    public void visitServiceDefinition(PbServiceDef element) {
        fixHighlighting(element);
    }

    @Override
    public void visitServiceMethodDefinition(PbServiceMethodDef element) {
        fixHighlighting(element);
    }

    @Override
    public void visitEnumConstantDefinition(PbEnumConstantDef element) {
        PsiElement nameElement = element.getNameElement();
        if (nameElement != null) {
            myHolder.createInfoAnnotation(nameElement.getNode(), null).setTextAttributes(protobuf.highlighter.DefaultHighlighter.ENUM_CONSTANT_ATTR_KEY);
        }
    }

    //todo: fix for group definition http://code.google.com/intl/ru/apis/protocolbuffers/docs/proto.html#nested
    @Override
    public void visitGroupDefinition(PbGroupDef element) {
        fixHighlighting(element);

        //inspection that using group is deprecated
        myHolder.createWarningAnnotation(element.getNode(),PbBundle.message("inspection.groupdecl.deprecated"));

        //inspection that group name should starts with capital letter
        PsiElement nameElement = element.getNameElement();
        if(nameElement != null){
            String name = nameElement.getText();
            if(!PbTextUtil.isStartsWithCapital(name)){
                myHolder.createErrorAnnotation(element.getNode(),PbBundle.message("inspection.groupdecl.name.starts.with.capital"));
            }
        }        
    }


    //todo: add check that all field have unique number tag
    //todo: required: a well-formed message must have exactly one of this field.

    @Override
    public void visitMessageDefinition(PbMessageDef message) {
        fixHighlighting(message);
    }

    @Override
    public void visitEnumDefinition(PbEnumDef element) {
        fixHighlighting(element);
    }

    @Override
    public void visitExtendDefinition(PbExtendDef element) {
        //check fields values(contains in extensions)
    }

    @Override
    public void visitRef(PbRef element) {
        fixHighlighting(element);
        if (element.resolve() == null) {
            if (element.isLastInChainReference()) {
                myHolder.createErrorAnnotation(element.getNode(), PbBundle.message("unresolved.reference")).setTextAttributes(DefaultHighlighter.ERROR_INFO_ATTR_KEY);
            } else {
                myHolder.createInfoAnnotation(element.getNode(), PbBundle.message("unresolved.reference")).setTextAttributes(DefaultHighlighter.ERROR_INFO_ATTR_KEY);
            }
        }
    }

    @Override
    public void visitExtensionsDefinition(PbExtensionsDef element) {
        //check ranges: not intersected
    }

    @Override
    public void visitExtensionsRange(PbExtensionsRange element) {
        //check ranges: first < second        
    }

    @Override
    public void visitOptionAssignment(PbOptionAssignment element) {
        OptionType type = element.getType();
        switch(type){
            case BUILT_IN_OPTION:{
                //highlight name
                //check value and context
            }
            break;
            case CUSTOM_OPTION:{
                //check value and constant
                //replace value for reference if enum
            }
            break;
        }
    }

    @Override
    public void visitValue(PbValue element) {
        //check int for too big
        //making value reference for constant value
        /*if(element.getParent() instanceof PbOptionAssignment){

        }*/
        //fix highlighting
        fixHighlighting(element);
    }

    private void fixHighlighting(PsiElement element) {
        if (element instanceof PbNamedElement) {
            PsiElement nameElement = ((PbNamedElement) element).getNameElement();
            if (nameElement != null) {
                if (!sameType(nameElement, IDENTIFIER)) {                    
                    myHolder.createInfoAnnotation(nameElement.getNode(), null).setTextAttributes(DefaultHighlighter.TEXT_ATTR_KEY);
                }
            }
        } else if (element instanceof PbRef) {
            PsiElement nameElement = ((PbRef) element).getReferenceNameElement();
            if (nameElement != null) {
                if (!sameType(nameElement, IDENTIFIER)) {//i.e it is keyword token
                    myHolder.createInfoAnnotation(nameElement.getNode(), null).setTextAttributes(DefaultHighlighter.TEXT_ATTR_KEY);
                }
            }
        } else if(element instanceof PbValue){
            ValueType type = ((PbValue)element).getType();
            switch(type){
                case NUM_INT:
                case NUM_DOUBLE:{
                    myHolder.createInfoAnnotation(element.getNode(), null).setTextAttributes(DefaultHighlighter.NUMBER_ATTR_KEY);
                }
                break;
                case ENUM_CONSTANT:{
                    myHolder.createInfoAnnotation(element.getNode(), null).setTextAttributes(protobuf.highlighter.DefaultHighlighter.ENUM_CONSTANT_ATTR_KEY);
                }
                break;
            }
        } else {
            //todo [medium] maybe something else needs in fix highlighting
        }

    }
}
