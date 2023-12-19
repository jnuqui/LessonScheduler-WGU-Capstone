package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class StudentDAO {

    //INSERT INTO students (name, phone) VALUES('?', '?');

    public static ObservableList<Student> getStudents() throws SQLException {
        ObservableList<Student> allStudents = FXCollections.observableArrayList();
        String sql = "SELECT * FROM lesson_schedule.students;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int studentId = rs.getInt("student_id");
            String name = rs.getString("name");
            String phone = rs.getString("phone");
            Student student = new Student(studentId, name, phone);
            allStudents.add(student);
        }
        return allStudents;
    }

    public static void insertStudent(String name, String phone) throws SQLException {
        String sql = "INSERT INTO STUDENTS (name, phone) VALUES(?, ?);";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, phone);
        ps.executeUpdate();
    }

    public static void updateStudent(String name, String phone, int studentID) throws SQLException {
        String sql = "UPDATE STUDENTS set name = ?, phone = ? where student_id = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, phone);
        ps.setInt(3, studentID);
        ps.executeUpdate();
    }

    public static void deleteStudent(int studentID) throws SQLException {
        String sql = "DELETE FROM STUDENTS where student_id = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, studentID);
        ps.executeUpdate();
    }

    public static int checkStudentLessons(int studentId) throws SQLException
    {
        String sql = "SELECT COUNT(lesson_id) AS RowCount from LESSONS where student_id = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, studentId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt("RowCount");
        return count;
    }

    public static int studentExists(String name, String phone) throws SQLException {
        String sql = "SELECT COUNT(student_id) FROM lesson_schedule.students WHERE name = ? AND phone = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, phone);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt("COUNT(student_id)");
        return count;
    }


}
