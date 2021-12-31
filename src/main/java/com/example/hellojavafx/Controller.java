package com.example.hellojavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class Controller {

    public static String path1, path2;

    @FXML
    TextField textview, textview1;
    @FXML
    private Button exit;
    @FXML
    private AnchorPane scenePane;


    public void Button1Action(ActionEvent btn1 ){
        // todo File choose erstellen
        // todo Path in einem String speichern
        // todo String to Textfield übergeben

        FileChooser fc = new FileChooser();
        File seletedFile = fc.showOpenDialog(null);
        if (seletedFile != null){
            textview.setText(seletedFile.getParent());
            path1 = seletedFile.getParent();
        }else{
            System.out.println("Keine File wurde gefunden");
        }

    }
    public void Button2Action(ActionEvent btn1 ){
        // todo File choose erstellen
        // todo Path in einem String speichern
        // todo String to Textfield übergeben

        FileChooser fc = new FileChooser();
        File seletedFile = fc.showOpenDialog(null);
        if (seletedFile != null){
            textview1.setText(seletedFile.getAbsolutePath());
            path2 = seletedFile.getAbsolutePath();
        }else{
            System.out.println("Keine File wurde gefunden");
        }

    }

    public void Button3Action(ActionEvent btn3) throws IOException {

           Reader.main(new String[1]);

    }

    public void Exit(ActionEvent btn4 ){
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Exit");
          alert.setHeaderText("You're about to exit");
          if(alert.showAndWait().get() == ButtonType.OK){
              Stage stage  = (Stage) exit.getScene().getWindow();
              stage.close();
          }

    }


}
