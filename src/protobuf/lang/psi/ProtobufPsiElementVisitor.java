package protobuf.lang.psi;

import com.intellij.psi.PsiElementVisitor;
import protobuf.lang.psi.api.definitions.*;
import protobuf.lang.psi.api.members.PbName;
import protobuf.lang.psi.api.references.PbRef;

/**
 * author: Nikolay Matveev
 * Date: Mar 15, 2010
 */
public abstract class ProtobufPsiElementVisitor extends PsiElementVisitor {

    public abstract void visitImportDefinition(PbImportDef element);

    public abstract void visitFieldDefinition(PbFieldDef element);

    public abstract void visitEnumConstantDefinition(PbEnumConstantDefinition element);   

    public abstract void visitMessageDefinition(PbMessageDef element);

    public abstract void visitRef(PbRef element);

    public abstract void visitName(PbName element);
}
