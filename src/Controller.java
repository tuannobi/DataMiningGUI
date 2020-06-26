import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import python.bayes.Bayes;
import util.ReadFileUtil;
import util.ScriptPython;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class  Controller {
    private Alert alert=new Alert(Alert.AlertType.NONE);
    private final Path datasetSavePathCSV= Paths.get("src\\python\\data.csv");
    private final Path datasetSavePathXLSX= Paths.get("src\\python\\data.xlsx");
    private final Path importFileSavePath= Paths.get("src\\python\\import.xlsx");
    private String selectedRadioMenuItem="";
    private List<String> inputList;
    @FXML
    private MenuItem importMenuItem;
    @FXML
    private TextArea resultTextArea;
    @FXML
    private  MenuItem exitMenuItem;
    @FXML
    private Label statusLable;
    @FXML
    private ToggleGroup group;
    @FXML
    private Button chooseButton;
    @FXML
    private CheckBox checkBox;
    @FXML
    private TableView tableView;
    @FXML
    private Button accuracyButton;


    public void importFileAction(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Open dataset");
        if(selectedRadioMenuItem.equals("Bayes")){
            fileChooser.getExtensionFilters().
                    addAll(new FileChooser.ExtensionFilter("CSV file","*.xlsx"));
        }else{
            fileChooser.getExtensionFilters().
                    addAll(new FileChooser.ExtensionFilter("CSV file","*.csv"));
        }
        File file=fileChooser.showOpenDialog(null);
        statusLable.setText("Opening File...");
        if(file!=null){
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Import Successful!");
            alert.show();
            System.out.println(file.toPath());
            if(getFileExtension(file).equals(".xlsx")){
                Files.copy(file.toPath(),datasetSavePathXLSX, StandardCopyOption.REPLACE_EXISTING);
            }else if(getFileExtension(file).equals(".csv")){
                Files.copy(file.toPath(),datasetSavePathCSV, StandardCopyOption.REPLACE_EXISTING);
            }

        }else{
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Import Fail!");
            alert.show();
        }
    }


    public void runAlgoroithmButtonAction(ActionEvent actionEvent) throws IOException {
        if(selectedRadioMenuItem.equals("")){
            statusLable.setText("Error");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please choose Algorithm!");
            alert.show();
        }else{
            resultTextArea.setText("");
            statusLable.setText("Running "+selectedRadioMenuItem);
            System.out.println("Running...");
            switch (selectedRadioMenuItem){
                case "K-nearest Neighbor":
                    ScriptPython scriptPython=new ScriptPython();
                    //
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
                    //
                    int isIndex=0;
                    if(checkBox.isSelected()){
                        isIndex=1;
                    }
                    System.out.println(isIndex);
                    List<String> resultContent=scriptPython.runScript("src\\python\\knn\\Main.py",stringBuilder.toString(),isIndex);
                    StringBuilder resultStringBuilder=new StringBuilder();
                    for(String line:resultContent){
                        resultStringBuilder.append(line);
                        resultStringBuilder.append("\n");
                    }
                    resultTextArea.setText(resultStringBuilder.toString());
                    statusLable.setText("End "+selectedRadioMenuItem);
                    System.out.println("Ket thuc");
                    break;
                case "Decision Tree":
                    break;
                case "Forest Tree":
                    break;
                case "Bayes":
                    Bayes bayes=new Bayes();
                    StringBuilder stringBuilder1 =bayes.runAlgorithm(inputList);
                    resultTextArea.setText(stringBuilder1.toString());
                    System.out.println("ket thuc");
                    statusLable.setText("End "+selectedRadioMenuItem);
                    break;
            }
        }

    }

    public void exitAction(ActionEvent actionEvent){
        Platform.exit();
    }

    public void ratioButtonGroupAction(){
        RadioMenuItem radioMenuItem=(RadioMenuItem) group.getSelectedToggle();
        selectedRadioMenuItem=radioMenuItem.getText();
        statusLable.setText(selectedRadioMenuItem + " is selected");
    }

    public void chooseButtonAction() throws IOException {
        if(selectedRadioMenuItem.equals("Bayes")){
            statusLable.setText("Input...");
            TextInputDialog textInputDialog=new TextInputDialog();
            textInputDialog.setTitle("ArrayList ");
            textInputDialog.setHeaderText("Input:");
            textInputDialog.showAndWait();
            String stringInput=textInputDialog.getEditor().getText();
            String[] inputArray=stringInput.split("-");
            for(String temp:inputArray){
                System.out.println(temp);
            }
            inputList=new ArrayList<>();
            inputList= Arrays.asList(inputArray);
        }else{
            FileChooser fileChooser=new FileChooser();
            fileChooser.setTitle("Open import file");
            fileChooser.getExtensionFilters().
                    addAll(new FileChooser.ExtensionFilter("excel file","*.xlsx"));
            File file=fileChooser.showOpenDialog(null);
            statusLable.setText("Opening File...");
            if(file!=null){
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Import Successful!");
                alert.show();
                System.out.println(file.toPath());
                Files.copy(file.toPath(),importFileSavePath, StandardCopyOption.REPLACE_EXISTING);
            }else{
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Import Fail!");
                alert.show();
            }
        }
    }

    public void accuracyButtonAction(ActionEvent actionEvent){
        statusLable.setText("Calculating Accuracy...");
    }

    private static String getFileExtension(File file) {
        String extension = "";

        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                extension = name.substring(name.lastIndexOf("."));
            }
        } catch (Exception e) {
            extension = "";
        }

        return extension;
    }

}
