package model;

import java.math.RoundingMode;
import java.time.LocalDateTime;

public class VisitLesson extends Lesson{
    public VisitLesson(){

    }

    public VisitLesson(String month, int milesCommute)
    {
        this.month = month;
        this.milesCommute = milesCommute;
    }

    public VisitLesson(LocalDateTime startDateTime, LocalDateTime endDateTime, int milesCommute)
    {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public VisitLesson(int lessonID, String type, int studentID, LocalDateTime startDateTime, LocalDateTime endDateTime, double lessonProfit) {
        super(lessonID, type, studentID, startDateTime, endDateTime, lessonProfit);
    }

    public VisitLesson(int lessonID, String type, int studentID, LocalDateTime startDateTime, LocalDateTime endDateTime, double lessonProfit, int milesCommute) {
        super(lessonID, type, studentID, startDateTime, endDateTime, lessonProfit);
        this.milesCommute = milesCommute;
    }

    private int milesCommute;
    private final double visitChargePercent = 1.80;

    @Override
    public String calculateLessonProfit(){
        df.setRoundingMode(RoundingMode.UP);
        return df.format((float) ((float) (this.lessonRate * calculateLessonLength()) + (milesCommute * visitChargePercent)));
    }

    public int getMilesCommute() {
        return milesCommute;
    }

    public void setMilesCommute(int milesCommute) {
        this.milesCommute = milesCommute;
    }

    public double getVisitChargePercent() {
        return visitChargePercent;
    }
}
