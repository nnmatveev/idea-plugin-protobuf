package exp3;

import java.io.File;

/**
 * author: Nikolay Matveev
 */

public class FileTest {
    public static void main(String[] args) {
        String path1 = "/src/dir/";
        String path2 = "/src/dir/abc.proto";
        File file1 = new File(path1);
        File file2 = new File(path2);
        
        System.out.println(file1.getName());
        System.out.println(file2.getName());
    }
}
