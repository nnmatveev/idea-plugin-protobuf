package protobuf.actions;

import com.intellij.CommonBundle;
import com.intellij.ide.actions.CreateElementActionBase;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.JavaTemplateUtil;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import protobuf.ProtobufIcons;
import protobuf.util.PbBundle;

import java.util.Properties;

/**
 * author: Nikolay Matveev
 * Date: Mar 31, 2010
 */
public class NewFileAction extends CreateElementActionBase {

    private final static Logger LOG = Logger.getInstance(NewFileAction.class.getName());

    public NewFileAction() {
        super(PbBundle.message("action.newfile.text"), PbBundle.message("action.newfile.description"), ProtobufIcons.FILE_TYPE);
    }

    @NotNull
    @Override
    protected PsiElement[] invokeDialog(Project project, PsiDirectory dir) {
        MyInputValidator validator = new MyInputValidator(project, dir);
        Messages.showInputDialog(project, PbBundle.message("action.newfile.dialog.promt"), PbBundle.message("action.newfile.dialog.title"), Messages.getQuestionIcon(), "", validator);
        return validator.getCreatedElements();
    }

    @Override
    protected void checkBeforeCreate(String s, PsiDirectory psiDirectory) throws IncorrectOperationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @NotNull
    @Override
    protected PsiElement[] create(String fileName, PsiDirectory dir) throws Exception {
        final String fullFileName = fileName + ".proto";
        //FileTemplateManager.getInstance().addTemplate("ProtoFile.proto","proto");
        //FileTemplateManager.getInstance().
        final FileTemplate template = FileTemplateManager.getInstance().getTemplate("Proto file[template]");        
        FileTemplate[] templates = FileTemplateManager.getInstance().getAllTemplates();
        for(FileTemplate templ : templates){
            LOG.info("templates:-------");
            LOG.info(templ.getName() + " " + templ.getExtension());
        }
        assert template != null;
        final Properties properties = new Properties(FileTemplateManager.getInstance().getDefaultProperties());
        JavaTemplateUtil.setPackageNameAttribute(properties, dir);
        final PsiFileFactory factory = PsiFileFactory.getInstance(dir.getProject());
        final PsiFile file = factory.createFileFromText(fullFileName, template.getText(properties));
        return new PsiElement[]{dir.add(file)};
    }

    @Override
    protected String getErrorTitle() {
        return CommonBundle.getErrorTitle();
    }

    @Override
    protected String getCommandName() {
        return PbBundle.message("action.newfile.text");
    }

    @Override
    protected String getActionName(PsiDirectory psiDirectory, String s) {
        return PbBundle.message("action.newfile.text");
    }

    //public static FileTemplate protoTemplate = new FileTemplateImpl("package $PACKAGE_NAME$;","empty_proto_file",".proto");


    /*public static final String PROTO_EXTENSION = ".proto";

   public NewFileAction() {
       com.intellij.ide.actions.
       super(PbBundle.message("action.newfile.text"), PbBundle.message("action.newfile.description"), ProtobufIcons.FILE_TYPE);
   }

   @Override
   protected PsiElement getNavigationElement(@NotNull PbFile pbFile) {
       return pbFile;
   }

   @NotNull
   @Override
   protected CreateFileFromTemplateDialog.Builder buildDialog(Project project, PsiDirectory psiDirectory) {
       return CreateFileFromTemplateDialog.createDialog(project, PbBundle.message("action.newfile.dialog.title"));
   }

   @Override
   protected PbFile doCreate(PsiDirectory psiDirectory, String fileName, String templateName) throws IncorrectOperationException {
       final String fullFileName = fileName + PROTO_EXTENSION;
       return PbTemplateFactory.createFromTemplate(psiDirectory, fullFileName, templateName);
   }

   @Override
   protected String getActionName(PsiDirectory psiDirectory, String s, String s1) {
       return PbBundle.message("action.newfile.text");
   }

   @Override
   protected String getErrorTitle() {
       return CommonBundle.getErrorTitle();
   } */
}
