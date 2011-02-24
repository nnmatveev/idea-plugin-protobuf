package protobuf.lang.psi.impl;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import protobuf.file.ProtobufFileType;
import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.declaration.PbImportDef;
import protobuf.lang.psi.api.declaration.PbMessageDef;
import protobuf.lang.psi.api.declaration.PbPackageDef;
import protobuf.lang.psi.api.member.PbOptionAssignment;

import java.util.ArrayList;

/**
 * author: Nikolay Matveev
 * Date: Mar 9, 2010
 */
public class PbFileImpl extends PsiFileBase implements PbFile {

    private final static Logger LOG = Logger.getInstance(PbFileImpl.class.getName());

    public PbFileImpl(FileViewProvider viewProvider) {
        super(viewProvider, ProtobufFileType.PROTOBUF_FILE_TYPE.getLanguage());
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return ProtobufFileType.PROTOBUF_FILE_TYPE;
    }

    @Override
    public void accept(ProtobufPsiElementVisitor visitor) {
        visitor.visitFile(this);
    }

    @Override
    public String toString() {
        return "PROTO_FILE";
    }

    /**
     * Gets the package definition declaration.
     * @return the package definition
     */
    @Override
    public PbPackageDef getPackageDefinition() {
        return findChildByClass(PbPackageDef.class);
    }

    /**
     * Gets the package name from the package declaration.
     * @return the package name, if present, or the empty string.
     */
    @Override
    public String getPackageName() {
        final PbPackageDef packageDef = getPackageDefinition();
        return packageDef != null ? packageDef.getPackageName() : "";
    }

    /**
     * Gets the Java package name for this file, looking first for the <code>option java_package...</code> declaration
     * and falling back to the regular package definition.
     * @return the Java package name
     */
    public String getJavaPackageName() {
        String packageName = getPackageName();
        final PbOptionAssignment[] optionAssignments = findChildrenByClass(PbOptionAssignment.class);
        for (PbOptionAssignment assignment : optionAssignments) {
            if ("java_package".equals(assignment.getOptionName())) {
                packageName = assignment.getOptionValue();
                break;
            }
        }
        return packageName;
    }

    /**
     * <p>Gets the Java class name(s) that will be generated from this file.  This must be a List to allow for the behavior
     * when the <code>option java_multiple_files...</code> declaration is used.</p>
     *
     * <p>Mimics the behavior of the protobuf compiler, considering the <code>option java_outer_classname...</code>
     * declaration, and the <code>option java_multiple_files...</code> declaration, falling back to the camel-cased name
     * of the .proto file.</p>
     * @return the Java class name(s)
     */
    @Override
    public ArrayList<String> getJavaClassNames() {
        ArrayList<String> classNames = new ArrayList<String>();
        final PbOptionAssignment[] optionAssignments = findChildrenByClass(PbOptionAssignment.class);
        String name = getName();
        String fileName = name.substring(0, name.indexOf(".proto"));
        
        boolean useMultipleFiles = false;
        for (PbOptionAssignment assignment : optionAssignments) {
            // Note: java_outer_class_name and java_multiple_files are NOT mutually exclusive.
            if ("java_outer_classname".equals(assignment.getOptionName())) {
                fileName = assignment.getOptionValue();
            } else if ("java_multiple_files".equals(assignment.getOptionName())) {
                useMultipleFiles = Boolean.valueOf(assignment.getOptionValue());
            }
        }

        if (useMultipleFiles) {
            PbMessageDef[] messageDefs = findChildrenByClass(PbMessageDef.class);
            for (PbMessageDef messageDef: messageDefs) {
                classNames.add(messageDef.getName());
            }
            // When using multiple files, a class containing the .proto file's descriptor and initialization code is also
            // generated for the file containing the .proto defs.
            classNames.add(StringUtil.capitalizeWords(fileName, "_", true, false));
        } else {
            classNames.add(StringUtil.capitalizeWords(fileName, "_", true, false));
        }

        return classNames;
    }

    @Override
    public PbImportDef[] getImportDefinitions() {
        return findChildrenByClass(PbImportDef.class);
    }

    @Override
    public PsiElement getContext() {
        return super.getContext();
    }

    public PbFile[] getImportedFiles(boolean onlyAliased) {
        PbImportDef[] importDefs = getImportDefinitions();
        ArrayList<PbFile> importFiles = new ArrayList<PbFile>(importDefs.length);
        for (PbImportDef importDef : importDefs) {
            PbFile aliasedFile = importDef.getAliasedFile();
            if (aliasedFile != null || !onlyAliased) {
                importFiles.add(aliasedFile);
            }

        }
        return importFiles.toArray(new PbFile[importFiles.size()]);
    }
}
