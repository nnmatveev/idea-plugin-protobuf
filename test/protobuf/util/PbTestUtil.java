package protobuf.util;

import com.intellij.openapi.application.PluginPathManager;
import com.intellij.openapi.util.io.FileUtil;
import static protobuf.util.TestPath.*;

/**
 * author: Nikolay Matveev
 * Date: Apr 7, 2010
 */
public abstract class PbTestUtil {

    public static String getTestDataPath(){        
        return PLUGIN_PATH+TEST_DATA_PATH;
    }

    public static String loadFile(){
        return "testdata";
    }
}
