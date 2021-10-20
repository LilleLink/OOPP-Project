package model.notifications;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDateTime;

/**
 * An object which can be placed somewhere in time by using the {@code ISO-8601} calendar system and
 * {@link ChronoLocalDateTime} class.
 *
 * @author Simon Johnsson
 */
public interface IChronological {

    /**
     * Performs a comparison between this object's chronological placement and the one given.
     *
     * @param dateTime the compared point in time
     * @return the comparator value, negative if less, positive if greater
     */
    int compareTime(ChronoLocalDateTime<LocalDate> dateTime);

}
