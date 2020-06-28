import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import python.bayes.Bayes;
import util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
    private final Path importTestFile=Paths.get("src\\python\\test.csv");
    private String selectedRadioMenuItem="";
    private String result="";
    private StringBuilder outputResult;
    private List<String> inputList;
    private int k=0;
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
    @FXML
    private VBox classificationVbox;
    @FXML
    private Button testDataButton;
    @FXML
    private Button classification;

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
                    TextInputDialog textInputDialog10=new TextInputDialog();
                    textInputDialog10.setTitle("Input");
                    textInputDialog10.setHeaderText("Input index of Row :");
                    textInputDialog10.showAndWait();
                    int indexRow=Integer.parseInt(textInputDialog10.getEditor().getText());
                    //
                    ReadFileUtil readFileUtil=new ReadFileUtil();
                    List<List<String>> list =readFileUtil.getValues();
                    StringBuilder stringBuilder=new StringBuilder("{");
                    for(int i=0;i<list.get(0).size();i++){
                        stringBuilder.append("'");
                        stringBuilder.append(list.get(0).get(i));
                        stringBuilder.append("':");
                        System.out.println(list.get(indexRow).get(i));
                        if (isAlpha(list.get(1).get(i))){
                            stringBuilder.append("'"+list.get(indexRow).get(i)+"'");
                        }else{
                            stringBuilder.append(list.get(indexRow).get(i));
                        }
                        if(i<list.get(0).size()-1){
                            stringBuilder.append(",");
                        }
                    }
                    stringBuilder.append("}");
                    //
                    TextInputDialog textInputDialog6=new TextInputDialog();
                    textInputDialog6.setTitle("Input");
                    textInputDialog6.setHeaderText("Input k :");
                    textInputDialog6.showAndWait();
                    this.k=Integer.parseInt(textInputDialog6.getEditor().getText());
                    System.out.println("K="+k);
                    System.out.println(stringBuilder.toString());
                    List<String> resultContent=scriptPython.runScript("src\\python\\knn\\Main.py",stringBuilder.toString(),k);
                    StringBuilder resultStringBuilder=new StringBuilder();
                    for(String line:resultContent){
                        resultStringBuilder.append(line);
                        resultStringBuilder.append("\n");
                    }
                    outputResult=new StringBuilder(resultStringBuilder);
                    resultTextArea.setText(resultStringBuilder.toString());
                    statusLable.setText("End "+selectedRadioMenuItem);
                    System.out.println("Ket thuc");
                    break;
                case "Decision Tree":
                    int type=1;
                    CARTScriptPython cartScriptPython=new CARTScriptPython();
                    TextInputDialog textInputDialog=new TextInputDialog();
                    textInputDialog.setTitle("Input depth ");
                    textInputDialog.setHeaderText("Input:");
                    textInputDialog.showAndWait();
                    int depth=Integer.parseInt(textInputDialog.getEditor().getText());
                    List<String> lists=cartScriptPython.runScript("src\\python\\decisiontree\\cart_main.py",type,depth," ");
                    StringBuilder stringBuilder1=new StringBuilder();
                    for(String temp:lists){
                        stringBuilder1.append(temp);
                    }
                    result=stringBuilder1.toString();
                    outputResult=new StringBuilder(result);
                    result = result.replace(" ", "##");
                    System.out.println("ket qua: "+result );
                    resultTextArea.setText(stringBuilder1.toString());
                    statusLable.setText("End "+selectedRadioMenuItem);
                    break;
                case "Forest Tree":
                    int type2=1;
                    RandomForestScriptPython randomForestScriptPython=new RandomForestScriptPython();
                    TextInputDialog textInputDialog1=new TextInputDialog();
                    textInputDialog1.setTitle("Trees ");
                    textInputDialog1.setHeaderText("Input number of trees:");
                    textInputDialog1.showAndWait();
                    String numOfTree=textInputDialog1.getEditor().getText();

                    TextInputDialog textInputDialog2=new TextInputDialog();
                    textInputDialog2.setTitle("Bootstrap ");
                    textInputDialog2.setHeaderText("Input number row in 1 tree:");
                    textInputDialog2.showAndWait();
                    String numOfRow=textInputDialog2.getEditor().getText();

                    TextInputDialog textInputDialog3=new TextInputDialog();
                    textInputDialog3.setTitle("Features ");
                    textInputDialog3.setHeaderText("Input number of features:");
                    textInputDialog3.showAndWait();
                    String numOfFeatures=textInputDialog3.getEditor().getText();

                    TextInputDialog textInputDialog4=new TextInputDialog();
                    textInputDialog4.setTitle("Depth ");
                    textInputDialog4.setHeaderText("Input max depth:");
                    textInputDialog4.showAndWait();
                    String numOfMaxDepth=textInputDialog4.getEditor().getText();

                    String temp1=numOfTree+"/"+numOfRow+"/"+numOfFeatures+"/"+numOfMaxDepth;

                    List<String> list1=randomForestScriptPython.runScript("src\\python\\randomforest\\random_forest_main.py",type2,0,temp1);
                    StringBuilder stringBuilder2=new StringBuilder();
                    for(String temp:list1){
                        stringBuilder2.append(temp);
                    }
                    result=stringBuilder2.toString();
                    outputResult=new StringBuilder(result);
                    result = result.replace(" ", "##");
                    System.out.println("ket qua: "+result );
                    resultTextArea.setText(stringBuilder2.toString());
                    statusLable.setText("End "+selectedRadioMenuItem);
                    break;
                case "Bayes":
                    Bayes bayes=new Bayes();
                    StringBuilder stringBuilder3=bayes.runAlgorithm(inputList);
                    resultTextArea.setText(stringBuilder3.toString());
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
        //
        if(selectedRadioMenuItem.equals("Bayes") || selectedRadioMenuItem.equals("K-nearest Neighbor")){
            classification.setDisable(true);
        }else{
            classification.setDisable(false);
            testDataButton.setDisable(false);
        }
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
        switch (selectedRadioMenuItem){
            case "K-nearest Neighbor":
                ScriptPython scriptPython8=new ScriptPython();
                if(this.k==0){
                    TextInputDialog textInputDialog6=new TextInputDialog();
                    textInputDialog6.setTitle("Input");
                    textInputDialog6.setHeaderText("Input k :");
                    textInputDialog6.showAndWait();
                    this.k=Integer.parseInt(textInputDialog6.getEditor().getText());
                }
                List<String> lists8=scriptPython8.runScript("src\\python\\knn\\Accuracy_main.py","kkkk",this.k);
                StringBuilder stringBuilder8=new StringBuilder();
                System.out.println(lists8.toString());
                for(String temp:lists8){
                    stringBuilder8.append(temp);
                    stringBuilder8.append("\n");
                }
                resultTextArea.setText(stringBuilder8.toString());
                statusLable.setText("End "+selectedRadioMenuItem);
                break;
            case "Forest Tree":
                int type3=3;
                RandomForestScriptPython randomForestScriptPython=new RandomForestScriptPython();
                List<String> lists5=randomForestScriptPython.runScript("src\\python\\randomforest\\random_forest_main.py",type3,0,result);
                StringBuilder stringBuilder5=new StringBuilder();
                for(String temp:lists5){
                    stringBuilder5.append(temp);
                }
                resultTextArea.setText(stringBuilder5.toString());
                statusLable.setText("End "+selectedRadioMenuItem);
                break;
            case "Decision Tree":
                int type=3;
                CARTScriptPython cartScriptPython=new CARTScriptPython();
                List<String> lists=cartScriptPython.runScript("src\\python\\decisiontree\\cart_main.py",type,0,result);
                StringBuilder stringBuilder1=new StringBuilder();
                for(String temp:lists){
                    stringBuilder1.append(temp);
                }
                resultTextArea.setText(stringBuilder1.toString());
                statusLable.setText("End "+selectedRadioMenuItem);
                break;
        }
        statusLable.setText("End "+selectedRadioMenuItem);
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

    public void testDataButtonAction(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Open dataset");
        fileChooser.getExtensionFilters().
            addAll(new FileChooser.ExtensionFilter("CSV file","*.csv"));
        File file=fileChooser.showOpenDialog(null);
        statusLable.setText("Opening File...");
        if(file!=null){
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Import Successful!");
            alert.show();
            System.out.println(file.toPath());
            Files.copy(file.toPath(),importTestFile, StandardCopyOption.REPLACE_EXISTING);
        }else{
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Import Fail!");
            alert.show();
        }
    }

    public void classificationAction(ActionEvent actionEvent) {
        switch (selectedRadioMenuItem){
            case "K-nearest Neighbor":

                break;
            case "Forest Tree":
                int type=2;
                CARTScriptPython cartScriptPython=new CARTScriptPython();
                TextInputDialog textInputDialog=new TextInputDialog();
                textInputDialog.setTitle("Predict");
                textInputDialog.setHeaderText("Choose row you want to predict");
                textInputDialog.showAndWait();
                int stringInput=Integer.parseInt(textInputDialog.getEditor().getText());
                System.out.println(result);
                List<String> lists=cartScriptPython.runScript("src\\python\\randomforest\\random_forest_main.py",type,stringInput,result);
                StringBuilder stringBuilder1=new StringBuilder();
                for(String temp:lists){
                    stringBuilder1.append(temp);
                }
                resultTextArea.setText(stringBuilder1.toString());
                statusLable.setText("End "+selectedRadioMenuItem);
                break;
            case "Decision Tree":
                int type2=2;
                CARTScriptPython cartScriptPython1=new CARTScriptPython();
                TextInputDialog textInputDialog2=new TextInputDialog();
                textInputDialog2.setTitle("Predict");
                textInputDialog2.setHeaderText("Choose row you want to predict");
                textInputDialog2.showAndWait();
                int stringInput1=Integer.parseInt(textInputDialog2.getEditor().getText());
                List<String> lists3=cartScriptPython1.runScript("src\\python\\decisiontree\\cart_main.py",type2,stringInput1,result);
                StringBuilder stringBuilder2=new StringBuilder();
                for(String temp:lists3){
                    stringBuilder2.append(temp);
                }
                resultTextArea.setText(stringBuilder2.toString());
                statusLable.setText("End "+selectedRadioMenuItem);
                break;
        }
    }

    public void exportButtonAction(ActionEvent actionEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter=new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Save result");
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(outputResult.toString());
            writer.close();
        }
    }

    public boolean isAlpha(String s){
        return s.chars().allMatch(Character::isLetter);
    }
}
