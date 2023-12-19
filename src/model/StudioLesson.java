package model;

import java.math.RoundingMode;
import java.time.LocalDateTime;

public class StudioLesson extends Lesson{
    public StudioLesson(int lessonID, String type, int studentID, LocalDateTime startDateTime, LocalDateTime endDateTime, double lessonProfit) {
        super(lessonID, type, studentID, startDateTime, endDateTime, lessonProfit);
    }

    public StudioLesson(){

    }

    public StudioLesson(LocalDateTime startDateTime, LocalDateTime endDateTime)
    {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
    private final double studioRentalPercent = .85;

    @Override
    public String calculateLessonProfit(){
        df.setRoundingMode(RoundingMode.UP);
        return df.format((float) ((float) (this.lessonRate * calculateLessonLength()) * studioRentalPercent));
    }
}
