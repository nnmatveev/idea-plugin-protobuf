package protobuf.util;

import com.intellij.openapi.application.PluginPathManager;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.LocalTimeCounter;
import protobuf.file.ProtobufFileType;

import java.io.File;
import java.io.IOException;

import static protobuf.util.TestPath.*;

/**
 * author: Nikolay Matveev
 * Date: Apr 7, 2010
 */
public abstract class PbTestUtil { 

    public static String getTestDataPath() {
        return PLUGIN_PATH + TEST_DATA_PATH;
    }

    public static Pair<String, String> getSimpleTestMaterialsFromFile(final String filePath) throws IOException {
        Pair<String, String> results;
        String content = new String(FileUtil.loadFileText(new File(filePath)));
        String[] temp = content.split("------");        
        results = new Pair<String, String>(temp[0].trim(), temp[1].trim());
        return results;
    }

    public static PsiFile createPseudoProtoFile(final Project project, final String fileName, final String text) throws IncorrectOperationException {        
        return PsiFileFactory.getInstance(project).createFileFromText(
                "temp.proto",                
                ProtobufFileType.PROTOBUF_FILE_TYPE,
                text,
                LocalTimeCounter.currentTime(),
                true);
    }

    public static String loadFromFile(final String filePath) throws IOException {        
        return StringUtil.convertLineSeparators(new String(FileUtil.loadFileText(new File(filePath))));
    }
}
