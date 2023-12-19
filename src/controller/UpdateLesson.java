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

public class UpdateLesson implements Initializable {


    @FXML
    public TextField lessonIdTextField;
    @FXML
    public ComboBox<Student> studentComboBox;

    @FXML
    public RadioButton studioLessonRadio;
    @FXML
    public RadioButton virtualLessonRadio;
    @FXML
    public RadioButton visitLessonRadio;
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

    Lesson lesson;
    StudioLesson sLesson;
    VirtualLesson virtualLesson;
    VisitLesson visitLesson;

    LocalDateTime originalStart;
    LocalDateTime originalEnd;
    private Object VirtualLesson;

    LocalDateTime ldtStart = null;
    LocalDateTime ldtEnd = null;
    String meetingCode = null;
    int milesCommute = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            populateComboBoxes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public void populateComboBoxes() throws SQLException
    {
        studentComboBox.setItems(StudentDAO.getStudents());
        startTimeComboBox.setItems(CollectionLists.getTimes());
        endTimeComboBox.setItems(CollectionLists.getTimes());

    }

    public int returnStudentIdIndex(int studentId)
    {
        int studentIndex = 0;
        int currentId = 0;
        for (int i = 0; i < studentComboBox.getItems().size(); i++)
        {
            currentId = studentComboBox.getItems().get(i).getStudentID();
            if (currentId == studentId)
            {
                studentIndex = i;
            }
        }
        return studentIndex;
    }

    public void setStudioLesson(StudioLesson sLesson)
    {
        this.sLesson = sLesson;
        lesson = this.sLesson;
        setStudioLessonRadio();
        lessonIdTextField.setText(String.valueOf(sLesson.getLessonID()));

    setDates((sLesson.getStartDateTime().toLocalDate()), sLesson.getEndDateTime().toLocalDate(),
            sLesson.getStartDateTime().toLocalTime(), sLesson.getEndDateTime().toLocalTime());
        studentComboBox.getSelectionModel().select(returnStudentIdIndex(sLesson.getStudentID()));
        lessonProfitTextfield.setText(String.valueOf(sLesson.getLessonProfit()));
    }

    public void setVirtualLesson(VirtualLesson virtualLesson)
    {
        this.virtualLesson = virtualLesson;
        lesson = this.virtualLesson;
        setVirtualLessonRadio();
        lessonIdTextField.setText(String.valueOf(virtualLesson.getLessonID()));
        studentComboBox.getSelectionModel().select(returnStudentIdIndex(virtualLesson.getStudentID()));

        setDates((virtualLesson.getStartDateTime().toLocalDate()), virtualLesson.getEndDateTime().toLocalDate(),
                virtualLesson.getStartDateTime().toLocalTime(), virtualLesson.getEndDateTime().toLocalTime());

        meetingCodeTextField.setText(virtualLesson.getMeetingCode());

        lessonProfitTextfield.setText(String.valueOf(virtualLesson.getLessonProfit()));
    }

    public void setVisitLesson(VisitLesson visitLesson)
    {
        this.visitLesson = visitLesson;
        lesson = this.visitLesson;
        setVisitLessonRadio();
        lessonIdTextField.setText(String.valueOf(visitLesson.getLessonID()));
        studentComboBox.getSelectionModel().select(returnStudentIdIndex(visitLesson.getStudentID()));

        setDates((visitLesson.getStartDateTime().toLocalDate()), visitLesson.getEndDateTime().toLocalDate(),
                visitLesson.getStartDateTime().toLocalTime(), visitLesson.getEndDateTime().toLocalTime());

        milesCommuteTextField.setText(String.valueOf(visitLesson.getMilesCommute()));
        lessonProfitTextfield.setText(String.valueOf(visitLesson.getLessonProfit()));
    }

    public void setDates(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime)
    {
        startDatePicker.setValue(startDate);
        endDatePicker.setValue(endDate);
        startTimeComboBox.setValue(startTime);
        endTimeComboBox.setValue(endTime);
    }

    public void removeExtra()
    {
        meetingCodeLabel.setVisible(false);
        meetingCodeTextField.setVisible(false);
        milesCommuteLabel.setVisible(false);
        milesCommuteTextField.setVisible(false);
    }

    public void setStudioLessonRadio(){
        studioLessonRadio.setSelected(true);
    }

    public void setVirtualLessonRadio(){
        virtualLessonRadio.setSelected(true);
        meetingVisible();
    }

    public void setVisitLessonRadio(){
        visitLessonRadio.setSelected(true);
        milesVisible();
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

    public boolean checkMeetingCode(String meetingCode)
    {
        boolean goodCode = true;
        if(meetingCode.length() > 6)
        {
            goodCode = false;
        }
        return goodCode;
    }

    public boolean checkMilesCommute(String milesCommute) throws NumberFormatException
    {
        boolean goodMiles = true;

        try {
            Integer.parseInt(milesCommute);
        }
        catch(NumberFormatException e)
        {
            goodMiles = false;
        }
        return goodMiles;
    }

    public boolean miles() throws NumberFormatException
    {
        String milesCommute = milesCommuteTextField.getText();
        boolean goodMiles = true;

        try {
            Integer.parseInt(milesCommute);
            System.out.println("try success");
        }
        catch(NumberFormatException e)
        {
            goodMiles = false;
            System.out.println("test");
        }
        System.out.println(goodMiles);
        return goodMiles;
    }

public void updateLesson()
{
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
            StudioLesson sL = new StudioLesson();
            sL.setStartDateTime(ldtStart);
            sL.setEndDateTime(ldtEnd);
            lessonProfit = Double.parseDouble(sL.calculateLessonProfit());
        }
        if (virtualLessonRadio.isSelected())
        {
            type = "Virtual";
            VirtualLesson vL = new VirtualLesson();
            vL.setStartDateTime(ldtStart);
            vL.setEndDateTime(ldtEnd);
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
            lessonProfit = Double.parseDouble(vL.calculateLessonProfit());
        }
        if (visitLessonRadio.isSelected())
        {
            type = "Visit";
            VisitLesson vL = new VisitLesson();
            vL.setStartDateTime(ldtStart);
            vL.setEndDateTime(ldtEnd);
            vL.setMilesCommute(Integer.parseInt(milesCommuteTextField.getText()));
            //milesCommute = Integer.parseInt(milesCommuteTextField.getText());
            if (checkMilesCommute(milesCommuteTextField.getText())) {
                milesCommute = Integer.parseInt(milesCommuteTextField.getText());
            }
            else if(!checkMilesCommute(milesCommuteTextField.getText())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.show();
                alert.setHeaderText("Miles Commute Error");
                alert.setContentText("Enter a whole number for miles.");
            }
            lessonProfit = Double.parseDouble(vL.calculateLessonProfit());
        }

        int studentId = studentComboBox.getSelectionModel().getSelectedItem().getStudentID();
        int lessonId = Integer.parseInt(lessonIdTextField.getText());

        if (type.equals("Studio"))
        {
            LessonDAO.updateStudioLesson(type, studentId, tsStart, tsEnd, lessonProfit, lessonId);
            lessonProfitTextfield.setText(String.valueOf(lessonProfit));
        }
        if (type.equals("Virtual"))
        {
            LessonDAO.updateVirtualLesson(type, studentId, tsStart, tsEnd, meetingCode, lessonProfit, lessonId);
            lessonProfitTextfield.setText(String.valueOf(lessonProfit));
        }
        if (type.equals("Visit"))
        {
            LessonDAO.updateVisitLesson(type, studentId, tsStart, tsEnd, milesCommute, lessonProfit, lessonId);
            lessonProfitTextfield.setText(String.valueOf(lessonProfit));
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.show();
        alert.setHeaderText("Successfully updated.");
        alert.setContentText("Lesson for student \"" + studentComboBox.getSelectionModel().getSelectedItem().toString() + "\" successfully updated.");
        // }
    }}
    catch (Exception e)
    {

    }
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
        /*else if (updateTimeSame() == true)
        {
            good = true;
        }*/
        else if (!LessonDAO.checkLessonUpdateOverlap(ldtStart, ldtEnd, Integer.parseInt(lessonIdTextField.getText())).equals("No"))
        {
            error.makeAlert("Conflicting Time", LessonDAO.checkLessonUpdateOverlap(ldtStart, ldtEnd, Integer.parseInt(lessonIdTextField.getText())));
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
        lessonProfitTextfield.setText("");
        lessonProfitTextfield.setPromptText("(Automatically calculated)");
        removeExtra();
    }

    public boolean updateTimeSame()
    {
        boolean good = false;
        //Start
        LocalDate ldStart = startDatePicker.getValue();
        LocalTime ltStart = LocalTime.parse(startTimeComboBox.getValue().toString());
        LocalDateTime ldtStart = LocalDateTime.of(ldStart, ltStart);

        //End
        LocalDate ldEnd = endDatePicker.getValue();
        LocalTime ltEnd = LocalTime.parse(endTimeComboBox.getValue().toString());
        LocalDateTime ldtEnd = LocalDateTime.of(ldEnd, ltEnd);
        if(ldtStart.isEqual(originalStart) && ldtEnd.isEqual(originalEnd))
        {
            good = true;
        }
        return good;
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
