package protobuf.lang.util;

/**
 * author: Nikolay Matveev
 * Date: Mar 27, 2010
 */
public class TextUtil {
    public static String trim(String str, char c){
        int start = 0;
        int end = str.length()-1;
        while(str.charAt(start) == c && start < end){
            start++;
        }
        while(str.charAt(end) == c && start < end){
            end--;
        }
        if(start < end) return str.substring(start,end+1);
        return str;
    }
}
