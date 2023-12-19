package controller;

import dao.LessonDAO;
import dao.StudentDAO;
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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddLesson implements Initializable {

    @FXML
    public RadioButton studioLessonRadio;
    @FXML
    public RadioButton virtualLessonRadio;
    @FXML
    public RadioButton visitLessonRadio;

    @FXML
    public ComboBox<Student> studentComboBox;

    @FXML
    public DatePicker startDatePicker;
    @FXML
    public ComboBox startTimeComboBox;
    @FXML
    public DatePicker endDatePicker;
    @FXML
    public ComboBox endTimeComboBox;

    @FXML
    public Label milesCommuteLabel;
    @FXML
    public TextField milesCommuteTextField;
    @FXML
    public Label meetingCodeLabel;
    @FXML
    public TextField meetingCodeTextField;
    @FXML
    public TextField lessonProfitTextfield;

    LocalDateTime ldtStart = null;
    LocalDateTime ldtEnd = null;
    String meetingCode = null;
    int milesCommute = 0;

    StudioLesson sLesson = new StudioLesson(ldtStart, ldtEnd);
    VirtualLesson virtualLesson = new VirtualLesson(ldtStart, ldtEnd);
    VisitLesson visitLesson = new VisitLesson(ldtStart, ldtEnd, milesCommute);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            populateComboBoxes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void populateComboBoxes() throws SQLException {

        studentComboBox.setItems(StudentDAO.getStudents());
        startTimeComboBox.setItems(CollectionLists.getTimes());
        endTimeComboBox.setItems(CollectionLists.getTimes());
    }

    public void removeExtra()
    {
        meetingCodeLabel.setVisible(false);
        meetingCodeTextField.setVisible(false);
        milesCommuteLabel.setVisible(false);
        milesCommuteTextField.setVisible(false);
    }

    public void meetingVisible()
    {

        meetingCodeLabel.setVisible(true);
        meetingCodeTextField.setVisible(true);
        milesCommuteLabel.setVisible(false);
        milesCommuteTextField.setVisible(false);
    }

    public void milesVisible()
    {
        meetingCodeLabel.setVisible(false);
        meetingCodeTextField.setVisible(false);
        milesCommuteLabel.setVisible(true);
        milesCommuteTextField.setVisible(true);
    }


    public void insertLesson() throws SQLException {

        try{
            boolean inputWorks = inputCheck();
            boolean timeWorks = goodAppointmentTime();
           if(inputWorks && timeWorks)
           {
            //Start
            LocalDate ldStart = startDatePicker.getValue();
            LocalTime ltStart = LocalTime.parse(startTimeComboBox.getValue().toString());
            ldtStart = LocalDateTime.of(ldStart, ltStart);
            Timestamp tsStart = Timestamp.valueOf(ldtStart);

            //End
            LocalDate ldEnd = endDatePicker.getValue();
            LocalTime ltEnd = LocalTime.parse(endTimeComboBox.getValue().toString());
            ldtEnd = LocalDateTime.of(ldEnd, ltEnd);
            Timestamp tsEnd = Timestamp.valueOf(ldtEnd);
            String type = null;
            double lessonProfit = 0;
            String meetingCode = null;

            if (studioLessonRadio.isSelected())
            {
                type = "Studio";
                sLesson.setStartDateTime(ldtStart);
                sLesson.setEndDateTime(ldtEnd);
                lessonProfit = Double.parseDouble(sLesson.calculateLessonProfit());
            }
            if (virtualLessonRadio.isSelected())
            {
                type = "Virtual";
                virtualLesson.setStartDateTime(ldtStart);
                virtualLesson.setEndDateTime(ldtEnd);
                if (checkMeetingCode(meetingCodeTextField.getText())) {
                    meetingCode = meetingCodeTextField.getText();
                }
                else if(!checkMeetingCode(meetingCodeTextField.getText())){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.show();
                    alert.setHeaderText("Meeting Code Error");
                    alert.setContentText("Meeting Code must be 0-6 characters.");
                    return;
                }
                lessonProfit = Double.parseDouble(virtualLesson.calculateLessonProfit());
            }
            if (visitLessonRadio.isSelected())
            {
                type = "Visit";
                visitLesson.setStartDateTime(ldtStart);
                visitLesson.setEndDateTime(ldtEnd);
                visitLesson.setMilesCommute(Integer.parseInt(milesCommuteTextField.getText()));
                lessonProfit = Double.parseDouble(visitLesson.calculateLessonProfit());
                milesCommute = Integer.parseInt(milesCommuteTextField.getText());
            }

            int studentId = studentComboBox.getSelectionModel().getSelectedItem().getStudentID();

            if (type.equals("Studio"))
            {
                LessonDAO.insertStudioLesson(type, studentId, tsStart, tsEnd, lessonProfit);
            }
            if (type.equals("Virtual"))
            {
                LessonDAO.insertVirtualLesson(type, studentId, tsStart, tsEnd, meetingCode, lessonProfit);
            }
            if (type.equals("Visit"))
            {
                LessonDAO.insertVisitLesson(type, studentId, tsStart, tsEnd, milesCommute, lessonProfit);
            }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.show();
                alert.setHeaderText("Successfully added.");
                alert.setContentText("Lesson for student \"" + studentComboBox.getSelectionModel().getSelectedItem().toString() + "\" successfully added.");
            }
        }
        catch (Exception e)
        {

        }
    }

    public boolean checkMeetingCode(String meetingCode)
    {
        boolean goodCode = true;
        if(meetingCode.length() > 6)
        {
            goodCode = false;
        }

    return goodCode;
    }

    public boolean inputCheck()
    {
        boolean good = true;
        if((studioLessonRadio.isSelected() && (studentComboBox.getValue() == null ||
                startDatePicker.getValue() == null ||
                startTimeComboBox.getValue() == null ||
                endDatePicker.getValue() == null ||
                endTimeComboBox.getValue() == null))

                ||

                virtualLessonRadio.isSelected() && (studentComboBox.getValue() == null ||
                startDatePicker.getValue() == null ||
                startTimeComboBox.getValue() == null ||
                endDatePicker.getValue() == null ||
                endTimeComboBox.getValue() == null ||
                meetingCodeTextField.getText().equals(""))

                ||

                visitLessonRadio.isSelected() && (studentComboBox.getValue() == null ||
                startDatePicker.getValue() == null ||
                startTimeComboBox.getValue() == null ||
                endDatePicker.getValue() == null ||
                endTimeComboBox.getValue() == null ||
                milesCommuteTextField.getText().equals(""))
        )
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText("Check Inputs");
            alert.setContentText("Please complete all fields.");
            good = false;
        }
        return good;
    }

    public boolean goodAppointmentTime() throws SQLException {

        boolean good = true;
        //Start
        LocalDate ldStart = startDatePicker.getValue();
        LocalTime ltStart = LocalTime.parse(startTimeComboBox.getValue().toString());
        LocalDateTime ldtStart = LocalDateTime.of(ldStart, ltStart);

        //End
        LocalDate ldEnd = endDatePicker.getValue();
        LocalTime ltEnd = LocalTime.parse(endTimeComboBox.getValue().toString());
        LocalDateTime ldtEnd = LocalDateTime.of(ldEnd, ltEnd);

        int studentId = studentComboBox.getSelectionModel().getSelectedItem().getStudentID();

        Errors error = (d, m) ->
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setHeaderText(d);
            alert.setContentText(m);
        };

        if(startDatePicker.getValue() == null ||
                startTimeComboBox.getValue() == null ||
                endDatePicker.getValue() == null ||
                endTimeComboBox.getValue() == null )
        {
            good = false;
        }
        else if(ldtEnd.isBefore(ldtStart))
        {
            error.makeAlert("Check Time", "End time must be after Start Time");
            good = false;
        }
        else if (ldtStart.isEqual(ldtEnd))
        {
            error.makeAlert("Check Time", "Start and end time cannot be the same.");
            good = false;
        }
        else if (!LessonDAO.checkLessonOverlap(ldtStart, ldtEnd).equals("No"))
        {
            error.makeAlert("Conflicting Time", LessonDAO.checkLessonOverlap(ldtStart, ldtEnd));
            good = false;
        }
        return good;
    }

    public void resetFields()
    {
        studioLessonRadio.setSelected(true);
        studentComboBox.setValue(null);
        startDatePicker.setValue(null);
        startTimeComboBox.setValue(null);
        endDatePicker.setValue(null);
        endTimeComboBox.setValue(null);
        meetingCodeTextField.setText("");
        milesCommuteTextField.setText("");
        removeExtra();
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
}
