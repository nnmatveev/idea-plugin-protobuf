package protobuf.lang.psi;

import com.intellij.psi.PsiElementVisitor;
import protobuf.lang.psi.api.PbPsiElement;
import protobuf.lang.psi.api.declaration.*;
import protobuf.lang.psi.api.member.PbValue;
import protobuf.lang.psi.api.reference.PbRef;

/**
 * @author Nikolay Matveev
 * Date: Mar 15, 2010
 */
public abstract class PbPsiElementVisitor extends PsiElementVisitor {

    public void visitPbElement(PbPsiElement element){
        visitElement(element);
    }

    public void visitImportDefinition(PbImportDef element){
        visitPbElement(element);
    }

    public void visitPackageDefinition(PbPackageDef element){
        visitPbElement(element);
    }

    public void visitMessageDefinition(PbMessageDef element){
        visitPbElement(element);
    }

    public void visitEnumDefinition(PbEnumDef element){
        visitPbElement(element);
    }

    public void visitExtendDefinition(PbExtendDef element){
        visitPbElement(element);
    }

    public void visitEnumConstantDefinition(PbEnumConstantDef element){
        visitPbElement(element);
    }

    public void visitGroupDefinition(PbGroupDef element){
        visitPbElement(element);
    }

    public void visitFieldDefinition(PbFieldDef element){
        visitPbElement(element);
    }

    public void visitServiceDefinition(PbServiceDef element){
        visitPbElement(element);
    }

    public void visitServiceMethodDefinition(PbServiceMethodDef element){
        visitPbElement(element);
    }

    public void visitRef(PbRef element){
    }

    public void visitExtensionsDefinition(PbExtensionsDef element){
        visitPbElement(element);
    }

    public void visitValue(PbValue element){
        visitPbElement(element);
    }
}
