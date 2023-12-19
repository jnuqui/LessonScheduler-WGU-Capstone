package controller;

import dao.StudentDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddStudent implements Initializable {

    @FXML
    public TextField studentIdTextField;
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField phoneTextField;

    String name = null;
    String phone = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void insertStudent() throws SQLException {
        name = nameTextField.getText();
        phone = phoneTextField.getText();
        try {
            if (inputCheck() && nameCheck(name) && phoneCheck(phone) && noExistingStudent())
            {

                StudentDAO.insertStudent(name, phone);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.show();
                alert.setHeaderText("Success.");
                alert.setContentText("Student \"" + name + "\" successfully added.");
            }
        }

        catch(Exception e)
            {

            }
        }

        public boolean noExistingStudent() throws SQLException {
        boolean noExist;
            name = nameTextField.getText();
            phone = phoneTextField.getText();
        if(StudentDAO.studentExists(name, phone) == 0)
        {
            noExist = true;
        }
        else {
            noExist = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Student");
            alert.setContentText("Student already exists!");
        }
        return noExist;
        }

    public boolean inputCheck()
    {
        boolean good = true;
        if(nameTextField.getText() == "" ||
                phoneTextField.getText() == "")
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Check Inputs");
            alert.setContentText("Please complete all fields.");
            good = false;
        }
        return good;
    }

    public boolean nameCheck(String name)
    {
        boolean legitName = true;
        if(legitName == name.chars().allMatch(Character::isLetter))
        {
            legitName = true;
        }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.show();
                alert.setHeaderText("Check name");
                alert.setContentText("Name must contain only letters.");
                legitName = false;
            }
        return legitName;
        }

    public boolean phoneCheck(String phone)
    {
        boolean good = true;
        if (!phone.matches("\\d{10}"))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Phone Number Error");
            alert.setContentText("Enter 10 numbers without any spaces.");
            good = false;
        }
        return good;
    }

    public void resetFields()
    {
        nameTextField.setText("");
        phoneTextField.setText("");
    }

    public void toStudentDashboard(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../view/StudentDashboard.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 550, 475);
        stage.setTitle("Student Dashboard");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
