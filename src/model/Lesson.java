package model;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Lesson {
    private int lessonID;
    private String type;
    private int studentID;
    public LocalDateTime startDateTime;
    public LocalDateTime endDateTime;
    public final int lessonRate = 2;
    private double lessonProfit;
    public DecimalFormat df = new DecimalFormat("0.00");

    public String month;

    public Lesson()
    {

    }

    public DecimalFormat getDf() {
        return df;
    }

    public void setDf(DecimalFormat df) {
        this.df = df;
    }


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Lesson(String type, String month, double lessonProfit)
    {
        this.type = type;
        this.month = month;
        this.lessonProfit = lessonProfit;
    }

    public Lesson(LocalDateTime startDateTime, LocalDateTime endDateTime)
    {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Lesson(int lessonID, String type, int studentID, LocalDateTime startDateTime, LocalDateTime endDateTime, double lessonProfit) {
        this.lessonID = lessonID;
        this.type = type;
        this.studentID = studentID;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.lessonProfit = lessonProfit;
    }

    public int calculateLessonLength(){
        return (int)ChronoUnit.MINUTES.between(this.startDateTime, this.endDateTime);
    }

    public String calculateLessonProfit(){
        df.setRoundingMode(RoundingMode.UP);
        return df.format(this.lessonRate * calculateLessonLength());
    }

    public int getLessonID() {
        return lessonID;
    }

    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getLessonRate() {
        return lessonRate;
    }

    public double getLessonProfit() {
        return lessonProfit;
    }

    public void setLessonProfit(double lessonProfit) {
        this.lessonProfit = lessonProfit;
    }

    @Override
    public String toString()
    {
        return this.type + " Lesson with Student Id:" + this.studentID;
    }

}
