package controller;

import dao.LessonDAO;
import helper.CollectionLists;
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
import model.Lesson;
import model.StudioLesson;
import model.VirtualLesson;
import model.VisitLesson;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class LessonDashboard implements Initializable {

    @FXML
    public ComboBox typeComboBox;
    @FXML
    public ComboBox monthComboBox;



    @FXML
    private TableView<Lesson> lessonsTable;

    @FXML
    public TableColumn lessonIdColumn;
    @FXML
    public TableColumn typeColumn;
    @FXML
    public TableColumn studentIdColumn;
    @FXML
    public TableColumn startColumn;
    @FXML
    public TableColumn endColumn;
    @FXML
    public TableColumn lessonProfitColumn;

    @FXML
    public TableView weekTable;
    @FXML
    public DatePicker weekDatePicker;
    @FXML
    public TableColumn lessonIdWeekColumn;
    @FXML
    public TableColumn typeWeekColumn;
    @FXML
    public TableColumn studentIdWeekColumn;
    @FXML
    public TableColumn startWeekColumn;
    @FXML
    public TableColumn endWeekColumn;
    @FXML
    public TableColumn lessonProfitWeekColumn;

    @FXML
    public TableView<Lesson> typeMonthProfitsTable;
    public TableColumn typeReportColumn;
    public TableColumn monthReportColumn;
    public TableColumn lessonSumColumn;

    @FXML
    public TableView<Lesson> monthMilesTable;
    @FXML
    public TableColumn monthMilesColumn;
    @FXML
    public TableColumn milesColumn;
    @FXML
    public ComboBox monthMilesComboBox;


    public ObservableList<Lesson> allLessons = FXCollections.observableArrayList();
    private VisitLesson test = new VisitLesson();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        typeComboBox.setItems(CollectionLists.getTypes());
        monthComboBox.setItems(CollectionLists.getMonths());
        monthMilesComboBox.setItems(CollectionLists.getMonths());

        try {
            allLessons = LessonDAO.getLessons();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        populateMainTable();

        //Could be for reports later
        /*
        typeComboBox.setItems(CollectionLists.getTypes());
        monthComboBox.setItems(CollectionLists.getMonths());
        try {
            contactComboBox.setItems(ContactDAO.getContactNames());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        locationComboBox.setItems(CollectionLists.getPlaces());
        */


    }

    public void populateMainTable() {

        lessonsTable.setItems(allLessons);

        lessonIdColumn.setCellValueFactory(new PropertyValueFactory<>("lessonID"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        lessonProfitColumn.setCellValueFactory(new PropertyValueFactory<>("lessonProfit"));

        lessonsTable.setVisible(true);
        typeMonthProfitsTable.setVisible(false);
        weekTable.setVisible(false);
        monthMilesTable.setVisible(false);


        //for reports, later
        /*
        appointmentsTable.visibleProperty().setValue(true);
        reportTypeTable.visibleProperty().setValue(false);
        reportContactTable.visibleProperty().setValue(false);
        reportCustomTable.visibleProperty().setValue(false);
        */
    }

    public void deleteLesson() throws SQLException {
        if (lessonsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Error");
            alert.setContentText("Please select an lesson to delete.");
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete lesson Id:" + lessonsTable.getSelectionModel().getSelectedItem().getLessonID() + "?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        LessonDAO.deleteLesson(lessonsTable.getSelectionModel().getSelectedItem().getLessonID());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    Alert alertDeleted = new Alert(Alert.AlertType.INFORMATION);
                    alertDeleted.show();
                    alertDeleted.setHeaderText("Delete Successful");
                    alertDeleted.setContentText(lessonsTable.getSelectionModel().getSelectedItem().toString() + " was deleted");
                }
            });
            try {
                allLessons = LessonDAO.getLessons();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            populateMainTable();
        }
    }


    public void getTypeMonthProfits() throws SQLException {

        try{

            typeMonthProfitsTable.setItems(LessonDAO.getTypeMonthProfits(typeComboBox.getValue().toString(), monthComboBox.getValue().toString()));

            monthReportColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
            typeReportColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            lessonSumColumn.setCellValueFactory(new PropertyValueFactory<>("lessonProfit"));

            lessonsTable.visibleProperty().setValue(false);
            typeMonthProfitsTable.visibleProperty().setValue(true);
            weekTable.setVisible(false);
            monthMilesTable.setVisible(false);

        }        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Select Type/Month");
            alert.setContentText("Select Type and Month first");
        }
    }

    public void weekReport() throws SQLException {

        weekTable.setItems(LessonDAO.getLessonsByWeek(weekDatePicker.getValue()));

        lessonIdWeekColumn.setCellValueFactory(new PropertyValueFactory<>("lessonID"));
        typeWeekColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        studentIdWeekColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        startWeekColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endWeekColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        lessonProfitWeekColumn.setCellValueFactory(new PropertyValueFactory<>("lessonProfit"));

        lessonsTable.setVisible(false);
        typeMonthProfitsTable.setVisible(false);
        weekTable.setVisible(true);
        monthMilesTable.setVisible(false);
    }

    public void milesReport() throws SQLException {
        monthMilesTable.setItems(LessonDAO.getMonthMiles(monthMilesComboBox.getValue().toString()));

        monthMilesColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        milesColumn.setCellValueFactory(new PropertyValueFactory<>("milesCommute"));

        lessonsTable.setVisible(false);
        typeMonthProfitsTable.setVisible(false);
        weekTable.setVisible(false);
        monthMilesTable.setVisible(true);
    }



    public void toStudentDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/StudentDashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 550, 475);
        stage.setTitle("Student Dashboard");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void toAddLesson(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/AddLesson.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 450, 410);
        stage.setTitle("Add Lesson");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void toUpdateLesson(ActionEvent actionEvent) throws IOException, SQLException {
        if (lessonsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Error");
            alert.setContentText("Please select a lesson to update.");
            return;
        } else {

            if (lessonsTable.getSelectionModel().getSelectedItem().getType().equals("Studio")) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../view/UpdateLesson.fxml"));
                loader.load();

                UpdateLesson myUpdate = loader.getController();
                myUpdate.setStudioLesson((StudioLesson) lessonsTable.getSelectionModel().getSelectedItem());

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setTitle("Update Lesson");
                stage.setScene(new Scene(scene));
                stage.centerOnScreen();
                stage.show();
            }
            if (lessonsTable.getSelectionModel().getSelectedItem().getType().equals("Virtual")) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../view/UpdateLesson.fxml"));
                loader.load();

                UpdateLesson myUpdate = loader.getController();
                myUpdate.setVirtualLesson((VirtualLesson) lessonsTable.getSelectionModel().getSelectedItem());

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setTitle("Update Lesson");
                stage.setScene(new Scene(scene));
                stage.centerOnScreen();
                stage.show();
            }
            if (lessonsTable.getSelectionModel().getSelectedItem().getType().equals("Visit")) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../view/UpdateLesson.fxml"));
                loader.load();

                UpdateLesson myUpdate = loader.getController();
                myUpdate.setVisitLesson((VisitLesson) lessonsTable.getSelectionModel().getSelectedItem());

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setTitle("Update Lesson");
                stage.setScene(new Scene(scene));
                stage.centerOnScreen();
                stage.show();
            }
        }
    }
}
