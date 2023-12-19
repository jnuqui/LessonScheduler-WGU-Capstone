package dao;

import helper.CollectionLists;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Lesson;
import model.StudioLesson;
import model.VirtualLesson;
import model.VisitLesson;

import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class LessonDAO {

    public static ObservableList<Lesson> getLessons() throws SQLException {
        ObservableList<Lesson> allLessons = FXCollections.observableArrayList();
        String sql = "SELECT * FROM lessons ORDER BY start_date_time ASC;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int lessonID = rs.getInt("lesson_id");
            String type = rs.getString("type");
            int studentId = rs.getInt("student_id");
            LocalDateTime startDateTime = rs.getTimestamp("start_date_time").toLocalDateTime();
            LocalDateTime endDateTime = rs.getTimestamp("end_date_time").toLocalDateTime();
            double lessonProfit = rs.getDouble("lesson_profit");
            String meetingCode = rs.getString("meeting_code");
            int milesCommute = rs.getInt("miles_commute");
            if(type.equals("Studio")){
                StudioLesson sLesson = new StudioLesson(lessonID, type, studentId, startDateTime, endDateTime, lessonProfit);
                allLessons.add(sLesson);
            }
            if(type.equals("Virtual")){
                VirtualLesson vLesson = new VirtualLesson(lessonID, type, studentId, startDateTime, endDateTime, lessonProfit, meetingCode);
                allLessons.add(vLesson);
            }
            if(type.equals("Visit")){
                VisitLesson visitLesson = new VisitLesson(lessonID, type, studentId, startDateTime, endDateTime, lessonProfit, milesCommute);
                allLessons.add(visitLesson);
            }
        }
        return allLessons;
    }

    public static void insertStudioLesson(String type, int studentId, Timestamp tsStart, Timestamp tsEnd, double lessonProfit) throws SQLException {

        String sql = "INSERT INTO LESSONS (type, student_id, start_date_time, end_date_time, lesson_profit) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, type);
        ps.setInt(2,  studentId);
        ps.setTimestamp(3, tsStart);
        ps.setTimestamp(4, tsEnd);
        ps.setDouble(5, lessonProfit);
        ps.executeUpdate();
    }

    public static void insertVirtualLesson(String type, int studentId, Timestamp tsStart, Timestamp tsEnd, String meetingCode, double lessonProfit) throws SQLException {
        String sql = "INSERT INTO LESSONS (type, student_id, start_date_time, end_date_time, lesson_profit, meeting_code) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, type);
        ps.setInt(2,  studentId);
        ps.setTimestamp(3, tsStart);
        ps.setTimestamp(4, tsEnd);
        ps.setDouble(5, lessonProfit);
        ps.setString(6, meetingCode);

        ps.executeUpdate();
    }

    public static void insertVisitLesson(String type, int studentId, Timestamp tsStart, Timestamp tsEnd, int milesCommute, double lessonProfit) throws SQLException {
        String sql = "INSERT INTO LESSONS (type, student_id, start_date_time, end_date_time, lesson_profit, miles_commute) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, type);
        ps.setInt(2,  studentId);
        ps.setTimestamp(3, tsStart);
        ps.setTimestamp(4, tsEnd);
        ps.setInt(6,  milesCommute);
        ps.setDouble(5, lessonProfit);
        ps.executeUpdate();
    }

    public static void updateStudioLesson(String type, int studentId, Timestamp tsStart, Timestamp tsEnd, double lessonProfit, int lessonId) throws SQLException {
        String sql = "UPDATE LESSONS set type= ?, student_id = ?, start_date_time = ?, end_date_time = ?, lesson_profit = ? where lesson_id = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, type);
        ps.setInt(2,  studentId);
        ps.setTimestamp(3, tsStart);
        ps.setTimestamp(4, tsEnd);
        ps.setDouble(5, lessonProfit);
        ps.setInt(6, lessonId);
        ps.executeUpdate();
    }

    public static void updateVirtualLesson(String type, int studentId, Timestamp tsStart, Timestamp tsEnd, String meetingCode, double lessonProfit, int lessonId) throws SQLException {
        String sql = "UPDATE LESSONS set type = ?, student_id = ?, start_date_time = ?, end_date_time = ?, lesson_profit = ?, meeting_code = ? where lesson_id = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, type);
        ps.setInt(2,  studentId);
        ps.setTimestamp(3, tsStart);
        ps.setTimestamp(4, tsEnd);
        ps.setDouble(5, lessonProfit);
        ps.setString(6, meetingCode);
        ps.setInt(7, lessonId);

        ps.executeUpdate();
    }

    public static void updateVisitLesson(String type, int studentId, Timestamp tsStart, Timestamp tsEnd, int milesCommute, double lessonProfit, int lessonId) throws SQLException {
        String sql = "UPDATE LESSONS set type = ?, student_id = ?, start_date_time = ?, end_date_time = ?, lesson_profit = ?, miles_commute = ? where lesson_id = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, type);
        ps.setInt(2,  studentId);
        ps.setTimestamp(3, tsStart);
        ps.setTimestamp(4, tsEnd);
        ps.setInt(6,  milesCommute);
        ps.setDouble(5, lessonProfit);
        ps.setInt(7, lessonId);
        ps.executeUpdate();
    }

    public static void deleteLesson(int lessonId) throws SQLException {
        String sql = "DELETE FROM LESSONS WHERE lesson_id = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, lessonId);
        ps.executeUpdate();
    }

    public static String checkLessonOverlap(LocalDateTime startLDT, LocalDateTime endLDT) throws SQLException {
        String sql = "SELECT student_id, start_date_time, end_date_time FROM lessons WHERE ((start_date_time < ?) AND (end_date_time > ?));";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(endLDT));
        ps.setTimestamp(2, Timestamp.valueOf(startLDT));
        ResultSet rs = ps.executeQuery();
        if (!rs.isBeforeFirst()) {
            return "No";
        } else {
            rs.next();
            LocalDateTime startConflictDateTime = rs.getTimestamp("start_date_time").toLocalDateTime();
            LocalDateTime endConflictDateTime = rs.getTimestamp("end_date_time").toLocalDateTime();
            int studentIdFound = rs.getInt("student_id");
            LocalTime startConflictTime = startConflictDateTime.toLocalTime();
            LocalTime endConflictTime = endConflictDateTime.toLocalTime();
            String overlapReport = "No";
            if(endLDT.equals(startConflictDateTime) || startLDT.equals(endConflictDateTime))
            {
                return overlapReport;
            }
            return "Overlaps with lesson (" + CollectionLists.myFormattedTF(startConflictTime) + " - " + CollectionLists.myFormattedTF(endConflictTime) + ") for student_id: " + studentIdFound + ".";
        }
    }

    public static String checkLessonUpdateOverlap(LocalDateTime startLDT, LocalDateTime endLDT, int studentId, int lessonId) throws SQLException {
        String sql = "SELECT student_id, start_date_time, end_date_time FROM lessons WHERE ((start_date_time < ?) AND (end_date_time > ?)) AND student_id = ? AND NOT lesson_id = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(endLDT));
        ps.setTimestamp(2, Timestamp.valueOf(startLDT));
        ps.setInt(3, studentId);
        ps.setInt(4, lessonId);
        ResultSet rs = ps.executeQuery();
        if (!rs.isBeforeFirst()) {
            return "No";
        } else {
            rs.next();
            LocalDateTime startConflictDateTime = rs.getTimestamp("start_date_time").toLocalDateTime();
            LocalDateTime endConflictDateTime = rs.getTimestamp("end_date_time").toLocalDateTime();
            int studentIdFound = rs.getInt("student_id");
            LocalTime startConflictTime = startConflictDateTime.toLocalTime();
            LocalTime endConflictTime = endConflictDateTime.toLocalTime();
            String overlapReport = "No";
            if(endLDT.equals(startConflictDateTime) || startLDT.equals(endConflictDateTime))
            {
                return overlapReport;
            }
            return "Overlaps with lesson (" + CollectionLists.myFormattedTF(startConflictTime) + " - " + CollectionLists.myFormattedTF(endConflictTime) + ") for student_id: " + studentIdFound + ".";
        }
    }

    public static String checkLessonUpdateOverlap(LocalDateTime startLDT, LocalDateTime endLDT, int lessonId) throws SQLException {
        String sql = "SELECT student_id, start_date_time, end_date_time FROM lessons WHERE ((start_date_time < ?) AND (end_date_time > ?)) AND NOT lesson_id = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(endLDT));
        ps.setTimestamp(2, Timestamp.valueOf(startLDT));
        ps.setInt(3, lessonId);
        ResultSet rs = ps.executeQuery();
        if (!rs.isBeforeFirst()) {
            return "No";
        } else {
            rs.next();
            LocalDateTime startConflictDateTime = rs.getTimestamp("start_date_time").toLocalDateTime();
            LocalDateTime endConflictDateTime = rs.getTimestamp("end_date_time").toLocalDateTime();
            int studentIdFound = rs.getInt("student_id");
            LocalTime startConflictTime = startConflictDateTime.toLocalTime();
            LocalTime endConflictTime = endConflictDateTime.toLocalTime();
            String overlapReport = "No";
            if(endLDT.equals(startConflictDateTime) || startLDT.equals(endConflictDateTime))
            {
                return overlapReport;
            }
            return "Overlaps with lesson (" + CollectionLists.myFormattedTF(startConflictTime) + " - " + CollectionLists.myFormattedTF(endConflictTime) + ") for student_id: " + studentIdFound + ".";
        }
    }

    public static ObservableList<Lesson> getTypeMonthProfits(String type, String month) throws SQLException {
        ObservableList<Lesson> typeMonthProfitsLessons = FXCollections.observableArrayList();
        String sql = "SELECT SUM(lesson_profit) FROM Lessons WHERE monthname(start_date_time)=? AND type=?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, month);
        ps.setString(2, type);
        ResultSet rs = ps.executeQuery();
        rs.next();
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.UP);

        double lessonProfit = rs.getDouble("SUM(lesson_profit)");
        Lesson typeMonthLesson = new Lesson(type, month, lessonProfit);
        typeMonthProfitsLessons.add(typeMonthLesson);
        return typeMonthProfitsLessons;
    }

    public static ObservableList<Lesson> getLessonsByWeek(LocalDate pickDay) throws SQLException {
        ObservableList<Lesson> allLessons = FXCollections.observableArrayList();
        LocalTime now = LocalTime.of(0, 0);
        LocalDateTime today = LocalDateTime.of(pickDay, now);
        String sql = "SELECT * FROM lessons WHERE DATE(`start_date_time`) >= ? AND DATE(`end_date_time`) <= (?) ORDER BY start_date_time ASC;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(today));
        ps.setTimestamp(2, Timestamp.valueOf(today.plusDays(7)));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int lessonID = rs.getInt("lesson_id");
            String type = rs.getString("type");
            int studentId = rs.getInt("student_id");
            LocalDateTime startDateTime = rs.getTimestamp("start_date_time").toLocalDateTime();
            LocalDateTime endDateTime = rs.getTimestamp("end_date_time").toLocalDateTime();
            double lessonProfit = rs.getDouble("lesson_profit");
            String meetingCode = rs.getString("meeting_code");
            int milesCommute = rs.getInt("miles_commute");
            if(type.equals("Studio")){
                StudioLesson sLesson = new StudioLesson(lessonID, type, studentId, startDateTime, endDateTime, lessonProfit);
                allLessons.add(sLesson);
            }
            if(type.equals("Virtual")){
                VirtualLesson vLesson = new VirtualLesson(lessonID, type, studentId, startDateTime, endDateTime, lessonProfit, meetingCode);
                allLessons.add(vLesson);
            }
            if(type.equals("Visit")){
                VisitLesson visitLesson = new VisitLesson(lessonID, type, studentId, startDateTime, endDateTime, lessonProfit, milesCommute);
                allLessons.add(visitLesson);
            }
        }
        return allLessons;
    }

    public static ObservableList<Lesson> getMonthMiles(String month) throws SQLException {
        ObservableList<Lesson> milesLessons = FXCollections.observableArrayList();
        String sql = "SELECT SUM(miles_commute) FROM Lessons WHERE monthname(start_date_time)=? AND type='Visit';";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, month);
        ResultSet rs = ps.executeQuery();
        rs.next();
            int milesCommute = rs.getInt("SUM(miles_commute)");
            VisitLesson milesLesson = new VisitLesson(month, milesCommute);
            milesLessons.add(milesLesson);
        return milesLessons;
    }

    public static int getLessonCount() throws SQLException {
        int count = 0;
        String sql = "SELECT * FROM lessons;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ++count;
            // Get data from the current row and use it
        }

        if (count == 0) {
            System.out.println("No records found");
        }
        return count;
    }
}
