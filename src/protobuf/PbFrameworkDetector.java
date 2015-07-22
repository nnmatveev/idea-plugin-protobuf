package protobuf;

import com.intellij.facet.FacetType;
import com.intellij.facet.ProjectFacetManager;
import com.intellij.framework.detection.FacetBasedFrameworkDetector;
import com.intellij.framework.detection.FileContentPattern;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ui.configuration.ProjectStructureConfigurable;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.StandardPatterns;
import com.intellij.util.indexing.FileContent;
import org.jetbrains.annotations.NotNull;
import protobuf.facet.PbFacet;
import protobuf.file.PbFileType;
import protobuf.settings.facet.ProtobufFacetConfiguration;

import javax.swing.event.HyperlinkEvent;

/**
* A detector for {@PbFacet protobuf facets}.
* @author Travis Cripps
*/
class PbFrameworkDetector extends FacetBasedFrameworkDetector<PbFacet, ProtobufFacetConfiguration> {

    public PbFrameworkDetector() {
        super("protobuf-detector");
    }

    @Override
    public FacetType<PbFacet, ProtobufFacetConfiguration> getFacetType() {
        return PbFacet.getFacetType();
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return PbFileType.PROTOBUF_FILE_TYPE;
    }

    @NotNull
    @Override
    public ElementPattern<FileContent> createSuitableFilePattern() {
        return FileContentPattern.fileContent().withName(StandardPatterns.string().endsWith(".proto"));
    }

    @Override
    public void setupFacet(@NotNull final PbFacet facet, final ModifiableRootModel model) {
        final PbFacet localFacet = facet;
        final Module module = facet.getModule();
        final Project project = module.getProject();
        final ProtobufFacetConfiguration config = facet.getConfiguration();
        ProtobufFacetConfiguration defaultConfiguration = (ProtobufFacetConfiguration)ProjectFacetManager.getInstance(project).createDefaultConfiguration(facet.getType());
        config.setIsCompilationEnabled(defaultConfiguration.isCompilationEnabled());
        if (StringUtil.isEmpty(config.getCompilerOutputPath())) {
            if (!StringUtil.isEmpty(defaultConfiguration.getCompilerOutputPath())) {
                config.setCompilerOutputPath(defaultConfiguration.getCompilerOutputPath());
            } else {
                final String text = PbBundle.message("facet.protobuf.configuration.missing_output_dir", module.getName());
                NotificationGroup ng = NotificationGroup.balloonGroup("Framework Detection");
                ng.createNotification("Framework configuration incomplete", text, NotificationType.INFORMATION, new NotificationListener() {
                    @Override
                    public void hyperlinkUpdate(@NotNull Notification notification, @NotNull HyperlinkEvent event) {
                        if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                            ShowSettingsUtil.getInstance().editConfigurable(project, ProjectStructureConfigurable.getInstance(project), new Runnable() {
                                @Override
                                public void run() {
                                ProjectStructureConfigurable.getInstance(project).select(localFacet, true);
                                }
                            });
                        }
                    }
                }).notify(project);
            }

        }
        if (StringUtil.isEmpty(config.getAdditionalProtoPaths())) {
            if (!StringUtil.isEmpty(defaultConfiguration.getAdditionalProtoPaths())) {
                config.setAdditionalProtoPaths(defaultConfiguration.getAdditionalProtoPaths());
            }
        }
    }

}
