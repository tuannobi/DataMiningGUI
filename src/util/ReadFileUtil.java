package util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadFileUtil {
    public List<List<String>> getValues() throws IOException {
        String fileName = "src\\python\\import.xlsx";
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

        workbook.close();
        fis.close();
        return data;
    }
    public File getFolderDownload() {
        File folderUpload = new File(System.getProperty("user.home") + "/Uploads");
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }
    public List<String> readTextFile(String fileName) throws IOException {
        File folderUpload = new File(System.getProperty("user.home") + "/Uploads");
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        String filePath=folderUpload + File.separator + fileName;
        List<String> allLines = Files.readAllLines(Paths.get(filePath));
        return allLines;
    }
}
