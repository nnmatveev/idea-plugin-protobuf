package protobuf.facet;

import com.intellij.facet.Facet;
import com.intellij.facet.FacetManager;
import com.intellij.facet.FacetType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import protobuf.settings.facet.ProtobufFacetConfiguration;

/**
 * A Facet for working with Google Protocol Buffers.
 * @author Travis Cripps
 */
public class ProtobufFacet extends Facet<ProtobufFacetConfiguration> {

    private LocalFileSystem.WatchRequest compilerOutputWatchRequest;

    public ProtobufFacet(@NotNull FacetType facetType, @NotNull Module module, @NotNull String name, @NotNull ProtobufFacetConfiguration configuration) {
        super(facetType, module, name, configuration, null);
    }

    @Nullable
    public static ProtobufFacet getInstance(@NotNull Module module) {
        return FacetManager.getInstance(module).getFacetByType(ProtobufFacetType.ID);
    }

    @Nullable
    public static ProtobufFacet findFacetBySourceFile(@NotNull Project project, @Nullable VirtualFile file) {
        if (file == null) return null;

        final Module module = ModuleUtil.findModuleForFile(file, project);
        if (module == null) { return null; }

        return getInstance(module);
    }

    @Override
    public void initFacet() {
        ProtobufFacetConfiguration configuration = getConfiguration();
        String path = configuration.getCompilerOutputPath();
        if (!StringUtil.isEmpty(path)) {
            compilerOutputWatchRequest = LocalFileSystem.getInstance().addRootToWatch(path, true);
        }
    }

    @Override
    public void disposeFacet() {
        if (compilerOutputWatchRequest != null) {
            LocalFileSystem.getInstance().removeWatchedRoot(compilerOutputWatchRequest);
        }
    }

    public void updateCompilerOutputWatchRequest() {
        String path = getConfiguration().getCompilerOutputPath();
        if (compilerOutputWatchRequest != null && !compilerOutputWatchRequest.getRootPath().equals(path)) {
            LocalFileSystem.getInstance().removeWatchedRoot(compilerOutputWatchRequest);
            compilerOutputWatchRequest = null;
        }
        if (compilerOutputWatchRequest == null && !StringUtil.isEmpty(path)) {
            compilerOutputWatchRequest = LocalFileSystem.getInstance().addRootToWatch(path, true);
        }
    }
    
}
