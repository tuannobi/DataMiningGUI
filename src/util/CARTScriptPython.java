package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CARTScriptPython {
    Process mProcess;

    public List<String> runScript(String path, int type, int depth,String tree){
        Process process;
        try{
            process = Runtime.getRuntime().exec("python "+path+" "+type+" "+depth+" "+tree);
//            process = Runtime.getRuntime().exec("python "+path);
            mProcess = process;
        }catch(Exception e) {
            System.out.println("Exception Raised" + e.toString());
        }
        InputStream stdout = mProcess.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout,StandardCharsets.UTF_8));
        String line;
        List<String> stringList=new ArrayList<>();
        try{
            while((line = reader.readLine()) != null){
//                System.out.println("stdout: "+ line);
                stringList.add(line);
            }
        }catch(IOException e){
            System.out.println("Exception in reading output"+ e.toString());
        }
        return stringList;
    }
}