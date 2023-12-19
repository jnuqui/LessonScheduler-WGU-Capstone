package model;

import java.math.RoundingMode;
import java.time.LocalDateTime;

public class VirtualLesson extends Lesson{

    public VirtualLesson(){

    }

    public VirtualLesson(int lessonID, String type, int studentID, LocalDateTime startDateTime, LocalDateTime endDateTime, double lessonProfit, String meetingCode) {
        super(lessonID, type, studentID, startDateTime, endDateTime, lessonProfit);
        this.meetingCode = meetingCode;
    }

    public VirtualLesson(LocalDateTime startDateTime, LocalDateTime endDateTime)
    {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    private final double virtualDiscountPercent = .75;
    private String meetingCode;

    public String getMeetingCode() {
        return meetingCode;
    }

    public void setMeetingCode(String meetingCode) {
        this.meetingCode = meetingCode;
    }



    @Override
    public String calculateLessonProfit(){
        df.setRoundingMode(RoundingMode.UP);
        return df.format((float) (this.lessonRate * calculateLessonLength()) * virtualDiscountPercent);
    }
}
