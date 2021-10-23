package mocks;


import model.notifications.IChronological;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;

/**
 * Testing class to contain a chronological object.
 */
public class MockChronological implements IChronological {

    private final LocalDateTime time;

    public MockChronological(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public int compareTime(ChronoLocalDateTime<LocalDate> dateTime) {
        return time.compareTo(dateTime);
    }
}
