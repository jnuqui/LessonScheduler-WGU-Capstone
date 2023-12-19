package controller;

import helper.CollectionLists;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private TextField textfieldUsername;
    @FXML
    private PasswordField pWPasswordField;
    ZoneId myZoneId = ZoneId.systemDefault();

    String error = "Error";
    String errorDetail = "Wrong username or password.";

    public void login(ActionEvent actionEvent) throws IOException {

        if ((textfieldUsername.getText().equals("np") && pWPasswordField.getText().equals("np"))) {
            recordActivity(textfieldUsername.getText(), "successful");
        } else {
            recordActivity(textfieldUsername.getText(), "unsuccessful");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText(error);
            alert.setContentText(errorDetail);
            return;
        }
        Parent root = FXMLLoader.load(getClass().getResource("../view/LessonDashboard.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1240, 600);
        stage.setTitle("NP Dashboard");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void recordActivity(String userAttempt, String attempt) throws IOException {
        LocalDateTime myLDT = LocalDateTime.now();
        String loginAttemptTime = CollectionLists.myFormattedDTF(myLDT);
        String loginActivity = "Login by \"" + userAttempt + "\" was " + attempt + " at " + loginAttemptTime + " " + myZoneId.toString();

        // Filename variable
        String fileName = "login_activity.txt";

        // create filewriter object
        FileWriter fWriter = new FileWriter(fileName, true);

        //create and open file
        PrintWriter outputFile = new PrintWriter(fWriter);

        //write
        outputFile.println(loginActivity);

        // close file
        outputFile.close();
    }
}
