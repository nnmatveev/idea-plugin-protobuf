package protobuf.lang.psi.impl.blocks;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.ArrayUtil;
import protobuf.lang.psi.PbPsiEnums;
import protobuf.lang.psi.api.PbAssignable;
import protobuf.lang.psi.api.blocks.PbBlock;
import protobuf.lang.psi.api.definitions.PbEnumDef;
import protobuf.lang.psi.api.definitions.PbExtendDef;
import protobuf.lang.psi.api.definitions.PbFieldDef;
import protobuf.lang.psi.api.definitions.PbMessageDef;
import protobuf.lang.psi.impl.PbPsiElementImpl;
import protobuf.lang.psi.utils.PbPsiScopeBuilder;

/**
 * author: Nikolay Matveev
 * Date: Apr 2, 2010
 */
public class PbBlockImpl extends PbPsiElementImpl implements PbBlock {

    private final static Logger LOG = Logger.getInstance(PbBlockImpl.class.getName());

    public PbBlockImpl(ASTNode node) {
        super(node);
    }

    @Override
    public String toString() {
        return "Block";
    }

    @Override
    public PbAssignable[] getElementsInScope(PbPsiEnums.ReferenceKind kind) {
        switch (kind) {
            case MESSAGE_OR_PACKAGE_OR_GROUP:
            case MESSAGE_OR_GROUP: {
                LOG.info("looking for message or package, candidates: " + findChildrenByClass(PbMessageDef.class).length);
                return findChildrenByClass(PbMessageDef.class);
            }
            case MESSAGE_OR_ENUM_OR_GROUP: {
                LOG.info("looking for message or enum, candidates: " + (findChildrenByClass(PbMessageDef.class).length+findChildrenByClass(PbEnumDef.class).length));
                return ArrayUtil.mergeArrays(findChildrenByClass(PbMessageDef.class), findChildrenByClass(PbEnumDef.class), PbAssignable.class);
            }
            case EXTEND_FIELD:
            case EXTEND_FIELD_INSIDE: {
                PbExtendDef[] extendDefs = findChildrenByClass(PbExtendDef.class);
                PbPsiScopeBuilder sbuilder = new PbPsiScopeBuilder();
                sbuilder.extractAndAppend(extendDefs, PbPsiEnums.ReferenceKind.MESSAGE_OR_GROUP_FIELD);
                return sbuilder.getElements();
            }
            case MESSAGE_OR_GROUP_FIELD: {
                return findChildrenByClass(PbFieldDef.class);
            }
        }
        return PbAssignable.EMPTY_ASSIGNABLE_ARRAY;
    }
}
