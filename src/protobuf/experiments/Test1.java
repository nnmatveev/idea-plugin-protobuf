package protobuf.experiments;

import com.intellij.openapi.util.io.StreamUtil;

import java.io.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 15, 2010
 */
public class Test1 {
    public static void main(String[] args) throws IOException {        
        StringBuilder command = new StringBuilder();
        Process process = Runtime.getRuntime().exec("cmd /c help");        
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        /*String line;
        while((line = reader.readLine()) != null){
            System.out.println(line);
        }*/
        System.out.println(StreamUtil.readText(process.getInputStream()));
                
        //command.append("d:\\tools\\protobuf-2.3.0-compiler-only\\protoc.exe");
        //command.append(" d:\\descriptor.proto");
        //command.append(" --java_out=d:\\");
        //command.append("cmd /c start cmd.exe");
        /*ProcessBuilder pBuilder = new ProcessBuilder("cmd /c start cmd.exe");
        pBuilder.redirectErrorStream(true);        
        Process process = pBuilder.start();
        BufferedReader inpStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while(inpStream.ready()){
            System.out.print(inpStream.readLine());
        } */
    }
}
