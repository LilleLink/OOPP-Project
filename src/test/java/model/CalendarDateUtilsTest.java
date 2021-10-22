package model;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class CalendarDateUtilsTest {

    @Test
    public void testGetCalendarizedDate() {
        LocalDateTime dateTime = CalendarDateUtils.getCalendarizedDate();
        assertEquals(dateTime.getSecond(), 0);
        assertEquals(dateTime.getNano(), 0);
        assertEquals(dateTime.getMinute(), LocalDateTime.now().getMinute() - LocalDateTime.now().getMinute() % 15);
    }

    @Test
    public void testGetCalendarizedMinutes() {
        int calendarizedMinutes = CalendarDateUtils.getCalendarizedMinutes();
        assertEquals(calendarizedMinutes, LocalDateTime.now().getMinute() - LocalDateTime.now().getMinute() % 15);
    }

}
