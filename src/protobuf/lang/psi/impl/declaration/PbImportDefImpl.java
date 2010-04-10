package protobuf.lang.psi.impl.declaration;

import com.intellij.lang.ASTNode;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.declaration.PbImportDef;
import protobuf.lang.psi.api.reference.PbRef;
import protobuf.lang.psi.impl.PbPsiElementImpl;

/**
 * author: Nikolay Matveev
 * Date: Mar 10, 2010
 */
public class PbImportDefImpl extends PbPsiElementImpl implements PbImportDef {

    public PbImportDefImpl(ASTNode node) {
        super(node);
    }

    @Override
    public void accept(ProtobufPsiElementVisitor visitor) {
        visitor.visitImportDefinition(this);
    }

    @Override
    public PbFile getAliasedFile() {
        PbRef ref = this.findChildByClass(PbRef.class);
        return ref != null ? (PbFile) ref.resolve() : null;
    }
}
