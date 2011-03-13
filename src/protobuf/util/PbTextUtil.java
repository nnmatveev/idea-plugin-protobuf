package protobuf.util;

/**
 * @author Nikolay Matveev
 * Date: Mar 27, 2010
 */

public abstract class PbTextUtil {
    public static String trim(String str, char c) {
        int start = 0;
        int end = str.length() - 1;
        while (str.charAt(start) == c && start < end) {
            start++;
        }
        while (str.charAt(end) == c && start < end) {
            end--;
        }
        if (start < end) return str.substring(start, end + 1);
        return str;
    }

    public static String removeFromEnd(String str, char c) {
        int newend = str.length() - 1;
        while(newend > 0 && str.charAt(newend) == c){
            newend--;
        }
        return str.substring(0,newend);
    }
}
