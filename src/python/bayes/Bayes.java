package python.bayes;

import com.sun.security.jgss.GSSUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Bayes {
    private ResultAttribute resultAttribute;
    private int columnNum;
    private int rowNum;
    private List<List<String>> data;
    private StringBuilder resultOutput;
    private List<String> tenCotList;
    private List<Structure> tenCotThuocTinh;

    private List<Map<String,String>> thuocTinhKetQuaList;
    private List<Integer> xacSuatList;
    private List<Double> tiLeList;


    public Bayes() throws IOException {
        data=getData("src\\python\\data.xlsx");
        rowNum=data.size();
        columnNum=data.get(0).size();
//        System.out.println("Row: "+rowNum+" Column: "+columnNum);
        resultOutput=new StringBuilder();
    }

    public List<List<String>> getData(String filePath) throws IOException {
        String fileName = filePath;
        File excelFile = new File(fileName);
        FileInputStream fis = new FileInputStream(excelFile);

        // we create an XSSF Workbook object for our XLSX Excel File
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        // we get first sheet
        XSSFSheet sheet = workbook.getSheetAt(0);

        // we iterate on rows
        Iterator<Row> rowIt = sheet.iterator();
        List<List<String>> data=new ArrayList<>();
        while(rowIt.hasNext()) {
            Row row = rowIt.next();

            // iterate on cells for the current row
            Iterator<Cell> cellIterator = row.cellIterator();

            List<String> tempRow=new ArrayList<>();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                tempRow.add(cell.toString());
            }
            data.add(tempRow);
        }
        //Lấy tên các cột
        tenCotList=new ArrayList<>();
        tenCotList.addAll(data.get(0));
        //
        data.remove(0);
        workbook.close();
        fis.close();
        return data;
    }
    //Tim kiem cac gia tri phan biet co/khong o cot result
    void findProbabilityResultValue(){
        //Lay gia tri phan biet // Yes vs No
        HashSet<String> hashSet=new HashSet<>();
        hashSet=new HashSet<>();
        for(int i=0;i<rowNum;i++){
            hashSet.add(data.get(i).get(columnNum-1));
        }
        resultAttribute=new ResultAttribute();
        List<String> name=new ArrayList<>();
        List<Integer> tanSuat=new ArrayList<>();
        List<Double> phanTram=new ArrayList<>();
        //Dem xac xuat xuat hien cua tung gia tri phan biet
        for(String temp:hashSet){
            int dem=0;
            for(int i=0;i<rowNum;i++){
                if(data.get(i).get(columnNum-1).equals(temp)){
                    dem++;
                }
            }
            name.add(temp);
            tanSuat.add(dem);
            phanTram.add((double)dem/rowNum);
        }
        resultAttribute.setName(name);
        resultAttribute.setTanSuat(tanSuat);
        resultAttribute.setPhanTram(phanTram);
//        System.out.println(resultAttribute.toString());
    }

    //Tim cac gia tri phan biet o moi cot tru cot result va tinh ti le voi tung yes/no
    void findDiscriminantValuePerColumn(){
        tenCotThuocTinh=new ArrayList<>();
        //duyet tung cot
        for(int i=0;i<columnNum-1;i++){
            Structure structure=new Structure();
            structure.setTenCot(tenCotList.get(i));
            //duyet tung dong cua tung cot
            HashSet<String> hashSet=new HashSet<>();
            for(int j=0;j<rowNum;j++){
                hashSet.add(data.get(j).get(i));
            }
            List<String> tempThuocTinh=new ArrayList<>();
            tempThuocTinh.addAll(hashSet);
            structure.setThuocTinh(tempThuocTinh);
            tenCotThuocTinh.add(structure);
        }
        List<String> ketQua=resultAttribute.getName();
        List<Map<String,String>> list=new ArrayList<>();
        HashSet<Map<String,String>> temp=new HashSet<>();
        List<Map<String,String>> listPhanBiet=new ArrayList<>();
        //duyet tung cot
        for(int i=0;i<columnNum-1;i++){
            //duyet tung dong
            for(int j=0;j<rowNum;j++){
               //o moi cot duyet theo so lan cua thuoc tinh
                for(int h=0;h<tenCotThuocTinh.get(i).getThuocTinh().size();h++){
                    for(int g=0;g<ketQua.size();g++){
//                        System.out.println("Cot thu "+i+" gia tri "+tenCotThuocTinh.get(i).getTenCot()
//                                +" voi dong thu "+j+" gia tri "+data.get(j).get(i)+
//                                " so sanh no voi thuoc tinh "+tenCotThuocTinh.get(i).getThuocTinh().get(h)
//                                +" voi thuoc tinh quyet dinh "+ketQua.get(g)
//                        );
                        if(data.get(j).get(i).equals(tenCotThuocTinh.get(i).getThuocTinh().get(h)) && data.get(j).get(columnNum-1).equals(ketQua.get(g))){
//                            System.out.println("Gia tri nay o cot : "+i+" va dong "+j+" voi du lieu " +data.get(j).get(i));
                            Map map=new HashMap();
                            map.put(data.get(j).get(i),data.get(j).get(columnNum-1));
                            list.add(map);
                            temp.add(map);
                        }
                    }
                }
            }
        }
        listPhanBiet.addAll(temp);
        List<Integer> xacSuats=new ArrayList<>();
        List<Double> tiLes=new ArrayList<>();
        //
        for(int i=0;i<listPhanBiet.size();i++){
            int dem=0;
            for(int j=0;j<list.size();j++){
                if(listPhanBiet.get(i).equals(list.get(j))){
                    dem++;
                }
            }
            xacSuats.add(dem);
            Set<String> set = listPhanBiet.get(i).keySet();
            for (String key : set) {
                for(int gg=0;gg<resultAttribute.getName().size();gg++){
                    if(listPhanBiet.get(i).get(key).equals(resultAttribute.getName().get(gg))){
                        tiLes.add((double)dem/resultAttribute.getTanSuat().get(gg));
                    }
                }
            }
        }
        //Sap xep vao Structure
        thuocTinhKetQuaList=new ArrayList<>();
        xacSuatList=new ArrayList<>();
        tiLeList=new ArrayList<>();
        thuocTinhKetQuaList.addAll(listPhanBiet);
        xacSuatList.addAll(xacSuats);
        tiLeList.addAll(tiLes);
//        System.out.println(tenCotThuocTinh.toString());
//        System.out.println(xacSuatList);
//        System.out.println(tiLeList);
//        System.out.println(listPhanBiet.toString());
//        System.out.println(list.toString());
//        System.out.println(thuocTinhKetQuaList.toString());
//        System.out.println(xacSuatList.toString());
//        System.out.println(tiLes.toString());
    }

    public StringBuilder runAlgorithm(List<String> inputList) throws IOException {
        //
        findProbabilityResultValue();
        resultOutput.append("Ước lượng P(Ci) với ");
        for(int i=0;i<resultAttribute.getName().size();i++){
            resultOutput.append("C"+(i+1)+" = "+resultAttribute.getName().get(i)+"; ");
        }
        //
        findDiscriminantValuePerColumn();

        resultOutput.append("\n");

        resultOutput.append("\nTa tính ");
        resultOutput.append("P(");
        for(int i=0;i<tenCotThuocTinh.size();i++){
            resultOutput.append(tenCotThuocTinh.get(i).getTenCot());
            resultOutput.append("|Ci); ");
        }
        for(int i=0;i<thuocTinhKetQuaList.size();i++){
            resultOutput.append("\nP(");
            resultOutput.append(thuocTinhKetQuaList.get(i));
            resultOutput.append(") = ");
            resultOutput.append(tiLeList.get(i));
            resultOutput.append(" với tần suất xuất hiện ");
            resultOutput.append(xacSuatList.get(i)+" lần.");
        }
        //Input giả dụ nhập vào là một List
//        List<String> inputList=new ArrayList<>();
//        inputList.add("Nắng ");
//        inputList.add("Nóng ");
        //Nắng nóng thì có đi chơi hay không?
        //Bắt đầu
        List<Map<String,String>> tempThuocTinhKetQuaList=new ArrayList<>();
        List <Double> tempTiLeList=new ArrayList<>();
        resultOutput.append("\n\nTa có bảng: ");
        for(int i=0;i<thuocTinhKetQuaList.size();i++){
            Set<String> set = thuocTinhKetQuaList.get(i).keySet();
            for (String key : set) {
//                System.out.println(key + " " + map.get(key));
                for(int j=0;j<inputList.size();j++){
                    if(inputList.get(j).equals(key)){
                        resultOutput.append("\nP(");
                        resultOutput.append(thuocTinhKetQuaList.get(i));
                        tempThuocTinhKetQuaList.add(thuocTinhKetQuaList.get(i));
                        resultOutput.append(" ) = ");
                        resultOutput.append(tiLeList.get(i));
                        tempTiLeList.add(tiLeList.get(i));
                    }
                }
            }
        }
        resultOutput.append("\nTa có tỉ lệ như sau: \n");
        List<String> ketQuaLeft=new ArrayList<>();
        List<Double> ketQuaRight=new ArrayList<>();
                for(int j=0;j<resultAttribute.getName().size();j++){
                    double tich=1;
                    for(int i=0;i<tempThuocTinhKetQuaList.size();i++) {
                        Set<String> set = tempThuocTinhKetQuaList.get(i).keySet();
                        for (String key : set) {
                            if(resultAttribute.getName().get(j).equals(tempThuocTinhKetQuaList.get(i).get(key))){
                                tich*=tempTiLeList.get(i);
                            }
                        }
                    }
                    tich*=resultAttribute.getPhanTram().get(j);
                    resultOutput.append("P("+resultAttribute.getName().get(j)+"|"+inputList.toString()+")="+tich+"\n");
                    ketQuaLeft.add("P("+resultAttribute.getName().get(j)+"|"+inputList.toString()+") = "+tich);
                    ketQuaRight.add(tich);
                }
        resultOutput.append("=> Chọn "+ketQuaLeft.get(ketQuaRight.indexOf(Collections.max(ketQuaRight))));
        return resultOutput;
    }

    public StringBuilder getResultOutput() {
        return resultOutput;
    }

    public void setResultOutput(StringBuilder resultOutput) {
        this.resultOutput = resultOutput;
    }
}
