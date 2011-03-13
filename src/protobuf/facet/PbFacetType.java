package protobuf.facet;

import com.intellij.facet.Facet;
import com.intellij.facet.FacetType;
import com.intellij.facet.FacetTypeId;
import com.intellij.facet.autodetecting.FacetDetector;
import com.intellij.facet.autodetecting.FacetDetectorRegistry;
import com.intellij.facet.ui.FacetEditor;
import com.intellij.facet.ui.MultipleFacetSettingsEditor;
import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.patterns.PlatformPatterns;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import protobuf.PbIcons;
import protobuf.file.PbFileType;
import protobuf.settings.facet.ProtobufDefaultFacetSettingsEditor;
import protobuf.settings.facet.ProtobufFacetConfiguration;
import protobuf.settings.facet.ProtobufMultipleFacetSettingsEditor;
import protobuf.util.PbBundle;

import javax.swing.*;
import java.util.Collection;

/**
 * The FacetType for Protobuf facets.
 * @author Travis Cripps
 */
public class PbFacetType extends FacetType<PbFacet, ProtobufFacetConfiguration> {

    public static final FacetTypeId<PbFacet> ID = new FacetTypeId<PbFacet>("protobuf");
    public static final PbFacetType INSTANCE = new PbFacetType();

    private PbFacetType() {
        super(ID, "protobuf", PbBundle.message("facet.type.name.protobuf"));
    }

    public ProtobufFacetConfiguration createDefaultConfiguration() {
        return new ProtobufFacetConfiguration();
    }

    public PbFacet createFacet(@NotNull Module module, final String name, @NotNull ProtobufFacetConfiguration configuration, @Nullable Facet underlyingFacet) {
        return new PbFacet(this, module, name, configuration);
    }

    public PbFacetType(@NotNull FacetTypeId<PbFacet> protobufFacetFacetTypeId, @NotNull String stringId, @NotNull String presentableName, @Nullable FacetTypeId underlyingFacetType) {
        super(protobufFacetFacetTypeId, stringId, presentableName, underlyingFacetType);
    }

    public PbFacetType(@NotNull FacetTypeId<PbFacet> protobufFacetFacetTypeId, @NotNull String stringId, @NotNull String presentableName) {
        super(protobufFacetFacetTypeId, stringId, presentableName);
    }

    public boolean isSuitableModuleType(ModuleType moduleType) {
        return moduleType instanceof JavaModuleType;
    }

    public void registerDetectors(final FacetDetectorRegistry<ProtobufFacetConfiguration> detectorRegistry) {
        detectorRegistry.registerUniversalDetector(
                PbFileType.PROTOBUF_FILE_TYPE,
                PlatformPatterns.virtualFile().withExtension(".proto"),
                new ProtobufFacetDetector());
    }

    @Override
    public String getHelpTopic() {
        return "IntelliJ.IDEA.Procedures.Java.EE.Development.Managing.Facets";
    }

    public Icon getIcon() {
        return PbIcons.FILE_TYPE;
    }

    public ProtobufDefaultFacetSettingsEditor createDefaultConfigurationEditor(@NotNull final Project project, @NotNull final ProtobufFacetConfiguration configuration) {
        return new ProtobufDefaultFacetSettingsEditor(project, configuration);
    }

    public MultipleFacetSettingsEditor createMultipleConfigurationsEditor(@NotNull final Project project, @NotNull final FacetEditor[] editors) {
        return new ProtobufMultipleFacetSettingsEditor(project, editors);
    }

    private static class ProtobufFacetDetector extends FacetDetector<VirtualFile, ProtobufFacetConfiguration> {

        public ProtobufFacetDetector() {
            super("protobuf-detector");
        }

        public ProtobufFacetConfiguration detectFacet(final VirtualFile source, final Collection<ProtobufFacetConfiguration> existingFacetConfigurations) {
            if (!existingFacetConfigurations.isEmpty()) {
                return existingFacetConfigurations.iterator().next();
            }
            return new ProtobufFacetConfiguration();
        }
        
    }
    
}
