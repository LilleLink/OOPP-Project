package model;

import java.time.LocalDateTime;

/***
 * A utility class that helps unify LocalDateTime formats across the application.
 */
public final class CalendarDateUtils {

    private CalendarDateUtils() {
    }

    /***
     * Gets the current date and time, formats it to a calendarized date and returns it.
     * @return the calendarized date
     */
    public static LocalDateTime getCalendarizedDate() {
        return calendarizeDate(LocalDateTime.now());
    }

    /***
     * Calendarizes a given date. Rounds minutes down to the latest quarter hour, sets seconds and nanoseconds to zero.
     * @param dateTime the LocalDateTime object to be formatted.
     * @return the calendarized date.
     */
    public static LocalDateTime calendarizeDate(LocalDateTime dateTime) {
        return dateTime.withMinute(calendarizeMinutes(dateTime)).withSecond(0).withNano(0);
    }

    /***
     * Calendarizes the minute property of the current time (rounds to latest quarter hour) and returns the value in integers.
     * @return an integer representing the latest quarter hour.
     */
    public static int getCalendarizedMinutes() {
        return calendarizeMinutes(LocalDateTime.now());
    }

    /***
     * Calendarizes minutes of the given LocalDateTime object. Rounds minutevalue to latest quarter hour.
     * @param dateTime the LocalDateTime object containing the un-calendarized minute value.
     * @return the integer value representing the latest quarter hour.
     */
    public static int calendarizeMinutes(LocalDateTime dateTime) {
        return dateTime.getMinute() - dateTime.getMinute() % 15;
    }

}
