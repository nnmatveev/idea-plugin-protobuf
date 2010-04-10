package protobuf.lang.psi;

import com.intellij.psi.PsiElementVisitor;
import protobuf.lang.psi.api.declaration.*;
import protobuf.lang.psi.api.reference.PbRef;

/**
 * author: Nikolay Matveev
 * Date: Mar 15, 2010
 */
public abstract class ProtobufPsiElementVisitor extends PsiElementVisitor {

    public abstract void visitImportDefinition(PbImportDef element);

    public abstract void visitPackageDefinition(PbImportDef element);

    public abstract void visitMessageDefinition(PbMessageDef element);

    public abstract void visitEnumDefinition(PbEnumDef element);

    public abstract void visitExtendDefinition(PbExtendDef element);

    public abstract void visitEnumConstantDefinition(PbEnumConstantDef element);

    public abstract void visitGroupDefinition(PbGroupDef element);

    public abstract void visitFieldDefinition(PbFieldDef element);

    public abstract void visitServiceDefinition(PbServiceDef element);

    public abstract void visitServiceMethodDefinition(PbServiceMethodDef element);

    public abstract void visitRef(PbRef element);    
}
