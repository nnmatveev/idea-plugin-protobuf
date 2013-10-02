package protobuf.structureView;

import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.declaration.PbEnumConstantDef;
import protobuf.lang.psi.api.declaration.PbFieldDef;

/**
 * @author Nikolay Matveev
 */
public class PbStructureViewModel extends StructureViewModelBase
        implements StructureViewModel.ElementInfoProvider, StructureViewModel.ExpandInfoProvider
{
    public PbStructureViewModel(@NotNull PsiFile psiFile) {
        super(psiFile, new PbStructureViewTreeElement((PbFile) psiFile));
    }

    @Override
    public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
        return false;
    }

    @Override
    public boolean isAlwaysLeaf(StructureViewTreeElement element) {
        Object value = element.getValue();
        return value instanceof PbEnumConstantDef || value instanceof PbFieldDef;
    }

    @Override
    public boolean isAutoExpand(StructureViewTreeElement element) {
        return element.getValue() instanceof PbFile;
    }

    @Override
    public boolean isSmartExpand() {
        return false;
    }
}
