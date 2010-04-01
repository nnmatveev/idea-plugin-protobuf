package protobuf.lang.psi;

import com.intellij.psi.PsiElementVisitor;
import protobuf.lang.psi.api.references.PbImportRef;
import protobuf.lang.psi.api.definitions.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 15, 2010
 */
public abstract class ProtobufPsiElementVisitor extends PsiElementVisitor {

    public abstract void visitImportDefinition(PbImportDef element);

    public abstract void visitFieldDefinition(PbFieldDef element);

    public abstract void visitEnumConstantDefinition(PbEnumConstantDefinition element);   

    public abstract void visitMessageDefinition(PbMessageDef element);

    public abstract void visitImportReference(PbImportRef element);
}
