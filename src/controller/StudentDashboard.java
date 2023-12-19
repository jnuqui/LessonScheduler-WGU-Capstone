package controller;

import dao.StudentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Student;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentDashboard implements Initializable {

    @FXML
    public TextField searchTextField;
    @FXML
    public Button searchButton;

    @FXML
    public TableView<Student> studentsTable;
    @FXML
    public ObservableList<Student> allStudents = FXCollections.observableArrayList();
    @FXML
    public TableColumn studentIdColumn;
    @FXML
    public TableColumn nameColumn;
    @FXML
    public TableColumn phoneColumn;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            allStudents = StudentDAO.getStudents();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        populateTable();

    }

    public void populateTable(){
        studentsTable.setItems(allStudents);

        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    public void getResultsHandler(ActionEvent actionEvent) throws SQLException {
        String s = searchTextField.getText();
        ObservableList<Student> students = lookupStudent(s);

        studentsTable.setItems(students);
        if (students.size() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Error");
            alert.setContentText("No students found");
        }
        searchTextField.setText("");
    }

    public ObservableList<Student> lookupStudent(String name) throws SQLException {

        ObservableList<Student> namedStudents = FXCollections.observableArrayList();
        ObservableList<Student> allSearchStudents = StudentDAO.getStudents();

        for(Student student : allSearchStudents)
        {
            if(student.getName().contains(name))
            {
                namedStudents.add(student);
            }
        }
        return namedStudents;
    }

    public void deleteStudent() throws SQLException {
        if(studentsTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Error");
            alert.setContentText("Please select a student to delete.");
            return;
        }

        if(StudentDAO.checkStudentLessons(studentsTable.getSelectionModel().getSelectedItem().getStudentID()) > 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Error");
            alert.setContentText("Delete student lessons first.");
            return;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete student (ID:" + studentsTable.getSelectionModel().getSelectedItem().toString() + ") ?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        StudentDAO.deleteStudent(studentsTable.getSelectionModel().getSelectedItem().getStudentID());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    Alert alertDelete = new Alert(Alert.AlertType.INFORMATION);
                    alertDelete.show();
                    alertDelete.setHeaderText("Delete Successful");
                    alertDelete.setContentText("Student ID:" + studentsTable.getSelectionModel().getSelectedItem().toString() + " deleted.");
                    try {
                        allStudents = StudentDAO.getStudents();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            populateTable();
        }
    }

    public void toLessonDashboard(ActionEvent actionEvent) throws IOException, SQLException {

        Parent root = FXMLLoader.load(getClass().getResource("../view/LessonDashboard.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1240, 600);
        stage.setTitle("LessonScheduler Dashboard");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void toAddStudent(ActionEvent actionEvent) throws IOException, SQLException {

        Parent root = FXMLLoader.load(getClass().getResource("../view/AddStudent.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 350, 330);
        stage.setTitle("Add Student");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void toUpdateStudent(ActionEvent actionEvent) throws IOException, SQLException {
        if( studentsTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Error");
            alert.setContentText("Please select a student to update.");
            return;
        }

        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/UpdateStudent.fxml"));
            loader.load();

            UpdateStudent myUpdate = loader.getController();
            myUpdate.setStudent(studentsTable.getSelectionModel().getSelectedItem().getStudentID(),
                    studentsTable.getSelectionModel().getSelectedItem().getName(),
                    studentsTable.getSelectionModel().getSelectedItem().getPhone());

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Update Student");
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.show();
        }


       // Parent root = FXMLLoader.load(getClass().getResource("../view/UpdateStudent.fxml"));
        //Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        //Scene scene = new Scene(root, 350, 330);


    }
}
