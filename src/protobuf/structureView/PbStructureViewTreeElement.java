package protobuf.structureView;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase;
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.annotations.NotNull;
import protobuf.PbIcons;
import protobuf.lang.psi.PbPsiElementVisitor;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.PbPsiElement;
import protobuf.lang.psi.api.auxiliary.PbNamedElement;
import protobuf.lang.psi.api.declaration.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Nikolay Matveev
 */
public class PbStructureViewTreeElement extends PsiTreeElementBase<PbPsiElement> {

    public PbStructureViewTreeElement(@NotNull PbFile psiElement) {
        super(psiElement);
    }

    public PbStructureViewTreeElement(@NotNull PbNamedElement psiElement) {
        super(psiElement);
    }

    @NotNull
    @Override
    public Collection<StructureViewTreeElement> getChildrenBase() {
        final Collection<StructureViewTreeElement> children = new ArrayList<StructureViewTreeElement>();
        //noinspection ConstantConditions
        getElement().acceptChildren(new PbPsiElementVisitor() {
            public void visitPbElement(final PbPsiElement element) {
                if (element instanceof PbNamedElement) {
                    children.add(new PbStructureViewTreeElement((PbNamedElement)element));
                } else {
                    element.acceptChildren(this);
                }
            }
        });
        return children;
    }

    @Override
    public String getPresentableText() {
        final PbPsiElement element = getElement();
        if (element instanceof PsiNamedElement) {
            return ((PsiNamedElement) element).getName();
        }
        assert false;
        return null;
    }

    @Override
    public Icon getIcon(boolean open) {
        final PbPsiElement element = getElement();
        if (element instanceof PbMessageDef) {
            return PbIcons.MESSAGE;
        } else if (element instanceof PbEnumDef) {
            return PbIcons.ENUM;
        } else if (element instanceof PbServiceDef) {
            return PbIcons.SERVICE;
        } else if (element instanceof PbServiceMethodDef) {
            return PbIcons.SERVICE_METHOD;
        } else if (element instanceof PbFieldDef) {
            return PbIcons.FIELD;
        } else if (element instanceof PbGroupDef) {
            return PbIcons.GROUP;
        } else if(element instanceof PbEnumConstantDef){
            return PbIcons.ENUM_CONSTANT;
        }
        return super.getIcon(open);
    }
}
