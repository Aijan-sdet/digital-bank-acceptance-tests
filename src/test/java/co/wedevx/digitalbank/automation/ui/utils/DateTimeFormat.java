package co.wedevx.digitalbank.automation.ui.utils;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeFormat {
    // Create a Date object representing the current date and time
    Date currentDate = new Date();

    // Convert the Date object to a LocalDateTime object
    LocalDateTime currentDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

    // Add 5 hours to the current date and time
    LocalDateTime newDateTimeApi = currentDateTime.plusHours(4);
    LocalDateTime newDateTime = currentDateTime.plusHours(4);

    // Format the new date and time
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    String formattedDateTime = newDateTime.format(formatter);

    DateTimeFormatter formatterApi = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm");
    String formattedDateTimeApi = newDateTimeApi.format(formatterApi);

    DateTimeFormatter formatterApiDB = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    String formattedDateTimeApiDB = newDateTimeApi.format(formatterApiDB);
    // Print the new date and time


    public String getFormattedDateTime() {
        return formattedDateTime;
    }

    public String getFormattedDateTimeApi() {
        return formattedDateTimeApi;
    }

    public String getFormattedDateTimeApiDB() {
        return formattedDateTimeApiDB;
    }
}