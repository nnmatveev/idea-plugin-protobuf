package protobuf.compiler;

import com.intellij.openapi.compiler.GeneratingCompiler;
import com.intellij.openapi.compiler.ValidityState;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

/**
 * author: Nikolay Matveev
 * Date: Apr 5, 2010
 */
public class PbGenerationItem implements GeneratingCompiler.GenerationItem {

    Module myModule;
    VirtualFile myFile;
    boolean myIsTestSource;

    public PbGenerationItem(VirtualFile file, Module module, boolean isTestSource) {
        myModule = module;       
        myFile = file;
        myIsTestSource = isTestSource;
    }

    @Override
    public String getPath() {
        return myFile.getPath();
    }

    @Override
    public ValidityState getValidityState() {
        return null;
    }

    @Override
    public Module getModule() {
        return myModule;
    }

    @Override
    public boolean isTestSource() {
        return myIsTestSource;
    }
}
