package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.time.format.DateTimeFormatter;

public abstract class CollectionLists {
    private static ObservableList<LocalTime> myLT = FXCollections.observableArrayList();
    public static LocalTime [] time = new LocalTime[48];
    public static boolean timesLoaded = false;

    //Types of lessons for report
    private static ObservableList<String> types = FXCollections.observableArrayList();
    public static String[] typeLessons = {"Studio", "Virtual", "Visit"};
    public static boolean typesLoaded = false;

    //List of months for report
    private static ObservableList <String> allMonths = FXCollections.observableArrayList();
    public static String [] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public static boolean monthsLoaded = false;


    public static boolean checkTimeRange(LocalDateTime startLDT, LocalDateTime endLDT)
    {
        boolean withinTime = false;
        ZoneId myBusinessZone = ZoneId.of("America/Los_Angeles");
        ZoneId myZoneId = ZoneId.systemDefault();
        LocalDateTime appointmentDateTime = startLDT;
        LocalDate appointmentDate = appointmentDateTime.toLocalDate();

        //Open hour at ET office, perceived from any timezone
        LocalTime openHour = LocalTime.of(8, 0);
        LocalDateTime businessOpenDT = LocalDateTime.of(appointmentDate, openHour);
        ZonedDateTime zonedBusinessOpenDT = businessOpenDT.atZone(myBusinessZone);
        ZonedDateTime myZonedOpenDT = zonedBusinessOpenDT.withZoneSameInstant(myZoneId);
        LocalDateTime myOpenLDT = myZonedOpenDT.toLocalDateTime();

        //Closed hour at ET office, perceived from any timezone
        LocalTime closedHour = LocalTime.of(22, 0);
        LocalDateTime businessClosedDT = LocalDateTime.of(appointmentDate, closedHour);
        ZonedDateTime zonedBusinessClosedDT = businessClosedDT.atZone(myBusinessZone);
        ZonedDateTime myZonedClosedDT = zonedBusinessClosedDT.withZoneSameInstant(myZoneId);
        LocalDateTime myClosedLDT = myZonedClosedDT.toLocalDateTime();

        if (startLDT.isAfter(myOpenLDT) && (endLDT.isBefore(myClosedLDT)))
        {
            withinTime = true;
        }

        if (startLDT.isEqual(myOpenLDT) || (endLDT.isEqual(myClosedLDT)))
        {
            withinTime = true;
        }
        return withinTime;
    }


    public static void loadTimes()
    {
        for (int i = 0; i <= 23; i++) {
            {
                time[i] = LocalTime.of((i), 0);
                myLT.add(time[i]);

                time[i + 1] = LocalTime.of((i), 15);
                myLT.add(time[i + 1]);

                time[i + 1] = LocalTime.of((i), 30);
                myLT.add(time[i + 1]);

                time[i + 1] = LocalTime.of((i), 45);
                myLT.add(time[i + 1]);
            }
        }
    }

    public static ObservableList<LocalTime> getTimes() {
        if(!timesLoaded) {
            loadTimes();
            timesLoaded = true;
        }
        return myLT;
    }

    public static String myFormattedTF (LocalTime myLT)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        String formattedDate = myLT.format(format);
        return formattedDate;
    }

    public static String myFormattedDTF (LocalDateTime myLDT)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        String formattedDate = myLDT.format(format);
        return formattedDate;
    }

    public static void loadTypes() {
        for (int i = 0; i <= 2; i++) {
            types.add(typeLessons[i]);
        }
    }

    public static ObservableList<String> getTypes() {
        if(!typesLoaded) {
            loadTypes();
            typesLoaded = true;
        }
        return types;
    }

    public static void loadMonths() {
        for (int i = 0; i <= 11; i++) {
            allMonths.add(months[i]);
        }
    }

    public static ObservableList<String> getMonths() {
        if(!monthsLoaded) {
            loadMonths();
            monthsLoaded = true;
        }
        return allMonths;
    }

}
