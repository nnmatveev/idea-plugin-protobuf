package protobuf.facet;

import com.intellij.facet.Facet;
import com.intellij.facet.FacetType;
import com.intellij.facet.FacetTypeId;
import com.intellij.facet.autodetecting.FacetDetectorRegistry;
import com.intellij.facet.ui.FacetEditor;
import com.intellij.facet.ui.MultipleFacetSettingsEditor;
import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.PlatformPatterns;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import protobuf.PbIcons;
import protobuf.file.PbFileType;
import protobuf.settings.facet.ProtobufDefaultFacetSettingsEditor;
import protobuf.settings.facet.ProtobufFacetConfiguration;
import protobuf.settings.facet.ProtobufMultipleFacetSettingsEditor;
import protobuf.PbBundle;

import javax.swing.*;

/**
 * The FacetType for Protobuf facets.
 * @author Travis Cripps
 */
public class PbFacetType extends FacetType<PbFacet, ProtobufFacetConfiguration> {

    private static final String NAME = "protobuf";
    public static final FacetTypeId<PbFacet> ID = new FacetTypeId<PbFacet>(NAME);
    public static final PbFacetType INSTANCE = new PbFacetType();

    public PbFacetType() {
        super(ID, NAME, PbBundle.message("facet.type.name.protobuf"));
    }

    public PbFacetType(@NotNull FacetTypeId<PbFacet> protobufFacetFacetTypeId, @NotNull String stringId, @NotNull String presentableName, @Nullable FacetTypeId underlyingFacetType) {
        super(protobufFacetFacetTypeId, stringId, presentableName, underlyingFacetType);
    }

    public PbFacetType(@NotNull FacetTypeId<PbFacet> protobufFacetFacetTypeId, @NotNull String stringId, @NotNull String presentableName) {
        super(protobufFacetFacetTypeId, stringId, presentableName);
    }

    public ProtobufFacetConfiguration createDefaultConfiguration() {
        return new ProtobufFacetConfiguration();
    }

    public PbFacet createFacet(@NotNull Module module, final String name, @NotNull ProtobufFacetConfiguration configuration, @Nullable Facet underlyingFacet) {
        return new PbFacet(this, module, name, configuration);
    }

    public boolean isSuitableModuleType(ModuleType moduleType) {
        return moduleType instanceof JavaModuleType;
    }

    /*public void registerDetectors(final FacetDetectorRegistry<ProtobufFacetConfiguration> detectorRegistry) {
        detectorRegistry.registerUniversalDetector(
                PbFileType.PROTOBUF_FILE_TYPE,
                PlatformPatterns.virtualFile().withExtension(".proto"),
                new PbFrameworkDetector());
    }*/

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

}
