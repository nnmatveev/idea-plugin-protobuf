package protobuf.compiler;

import com.intellij.facet.FacetManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.compiler.GeneratingCompiler;
import com.intellij.openapi.compiler.ValidityState;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import protobuf.facet.ProtobufFacet;
import protobuf.facet.ProtobufFacetType;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.impl.PbFileImpl;

import java.io.File;
import java.util.ArrayList;

/**
 * author: Nikolay Matveev
 * Date: Apr 5, 2010
 */
public class PbGenerationItem implements GeneratingCompiler.GenerationItem {

    Module myModule;
    ProtobufFacet facet;
    VirtualFile myFile;
    boolean myIsTestSource;

    public PbGenerationItem(VirtualFile file, Module module, boolean isTestSource) {
        myModule = module;
        facet = FacetManager.getInstance(myModule).getFacetByType(ProtobufFacetType.ID);
        myFile = file;
        myIsTestSource = isTestSource;
    }

    @Override
    public String getPath() {        
        return myFile.getPath();
    }

    /**
     * Gets the base directory for the protoc command, e.g. the --proto_path argument for this item.  Returns the item's
     * parent directory.
     * @return the base directory
     */
    public String getBaseDir() {
        return myFile.getParent().getPath();
    }

    /**
     * Uses a {@link PbGenerationItemValidityState} based on the last modified timestamp of the .proto file and whether
     * the destination files exist, so the compiler only generates the output source file when the .proto file changes
     * or the destination files do not exist.
     */
    @Override
    public ValidityState getValidityState() {
        final PbFile pbFile = ApplicationManager.getApplication().runReadAction(new Computable<PbFile>() {
            public PbFile compute() {
                return (PbFile) PsiManager.getInstance(myModule.getProject()).findFile(myFile);
            }
        });
        String packageName = ((PbFileImpl)pbFile).getJavaPackageName();
        ArrayList<String> fileNames = pbFile.getJavaClassNames();
        String sep = System.getProperty("file.separator");

        boolean outputFilesExist = false;
        String outputPath = getOutputPath() + sep + packageName.replaceAll("\\.", sep);
        for (String fileName : fileNames) {
            String path = outputPath + sep + fileName + ".java";
            outputFilesExist = new File(path).exists();
            if (!outputFilesExist) {
                break;
            }
        }

        return new PbGenerationItemValidityState(myFile.getModificationStamp(), outputFilesExist);
    }

    @Override
    public Module getModule() {
        return myModule;
    }

    public ProtobufFacet getFacet() {
        return facet;
    }

    @Override
    public boolean isTestSource() {
        return myIsTestSource;
    }

    public String getUrl(){
        return myFile.getUrl();
    }

    /**
     * Determines whether generation should occur for this item, based on its module's facet configuration.
     * @return true if compilation is enabled in the facet configuration for the item's containing module
     */
    public boolean shouldGenerate() {
        return facet.getConfiguration().isCompilationEnabled();
    }

    /**
     * Gets the output path for the protobuf-generated file corresponding to this item, e.g. the --java_out argument to
     * protoc, based on the output source directory from the facet configuration for the item's containing module.
     * @return the directory where the generated file should be created
     */
    public String getOutputPath() {
        return facet.getConfiguration().getCompilerOutputPath();
    }


}
