package test;

import util.ReadFileUtil;

import java.io.IOException;
import java.util.List;

public class ReadFile {
    public static void main(String[] args) throws IOException {
        ReadFileUtil readFileUtil=new ReadFileUtil();
        List<List<String>> list =readFileUtil.getValues();
        StringBuilder stringBuilder=new StringBuilder("{");
        for(int i=0;i<list.get(0).size();i++){
            stringBuilder.append("'");
            stringBuilder.append(list.get(0).get(i));
            stringBuilder.append("':");
            if (list.get(1).get(i).matches("^[a-zA-Z]*$")){
                stringBuilder.append("'"+list.get(1).get(i)+"'");
            }else{
                stringBuilder.append(list.get(1).get(i));
            }
            if(i<list.get(0).size()-1){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("}");
        System.out.println(stringBuilder.toString());
    }
}
