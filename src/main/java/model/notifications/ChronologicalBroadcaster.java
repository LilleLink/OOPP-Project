package model.notifications;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An immutable object responsible for broadcasting {@link IChronological} objects with a given interval of mintues.
 *
 * @param <T> a chronological implementation
 * @author Simon Johnsson
 */
class ChronologicalBroadcaster<T extends IChronological> implements IObjectBroadcaster<T>, Runnable {

    private final List<T> content;
    private final List<IObjectBroadcastListener<T>> listeners;
    private final long minuteInterval;

    /**
     * Constructs a {@code ChronologicalBroadcaster} with the given content and interval in minutes.
     *
     * Given {@code content} list is copied and can therefore not be accessed within the object after construction.
     *
     * The {@code minuteInterval} determines how many minutes before the chronological object's point in time a broadcast is performed.
     *
     * Chronological objects whose point in time has passed are removed from the content list.
     *
     * @param content the content to broadcast
     * @param minuteInterval how many minutes from the content's point in time to broadcast
     */
    ChronologicalBroadcaster(List<T> content, long minuteInterval) {
        this.content = new ArrayList<>(content);
        removePassed();
        this.listeners = new ArrayList<>();
        this.minuteInterval = minuteInterval;
    }

    /**
     * Default constructor broadcasting with an interval of 1 minute.
     * Broadcasts content when the current time is 1 minute from the chronological object's point in time.
     *
     * @param content the content to broadcast
     */
    ChronologicalBroadcaster(List<T> content) {
        this(content,1);
    }

    /**
     * Private constructor for keeping adding listeners.
     * @param content the content to broadcast
     * @param minuteInterval how many minutes from the content's point in time to broadcast
     * @param listeners the broadcaster's listeners
     */
    private ChronologicalBroadcaster(List<T> content, long minuteInterval, List<IObjectBroadcastListener<T>> listeners) {
        this(content,minuteInterval);
        this.listeners.addAll(listeners);
    }

    /**
     * Returns an adjusted copy with the given interval.
     *
     * @param minuteInterval the new interval
     * @return an adjusted copy with the given interval
     */
    ChronologicalBroadcaster<T> withInterval(long minuteInterval) {
        return new ChronologicalBroadcaster<>(this.content,minuteInterval,this.listeners);
    }

    /**
     * Returns an adjusted copy with the given content.
     *
     * @param content the content to broadcast
     * @return an adjusted copy with the given content
     */
    ChronologicalBroadcaster<T> withContent(List<T> content) {
        return new ChronologicalBroadcaster<>(content,this.minuteInterval,this.listeners);
    }

    /**
     * Removes all content that lies chronologically behind the current time.
     */
    private void removePassed() {
        content.removeIf(this::hasPassed);
    }

    /**
     * Checks if a chronological object is chronologically behind the current time.
     *
     * @param chronological the object to check
     * @return true if the time has passed - false if not
     */
    private boolean hasPassed(IChronological chronological) {
        return chronological.compareTime(LocalDateTime.now()) < 0;
    }

    /**
     * Checks if a chronological object is within the {@code minuteInterval} minutes of the current time.
     *
     * @param chronological the object to check
     * @return true if within the interval, false if not
     */
    private boolean isWithinInterval(IChronological chronological) {
        return 0 <= chronological.compareTime(LocalDateTime.now().minusMinutes(minuteInterval));
    }

    /**
     * Broadcasts content that is within the given {@code minuteInterval}.
     * Removes it from the content list after it has been broadcast.
     */
    private void broadcastContent() {
        for(Iterator<T> iterator = content.iterator(); iterator.hasNext();) {
            T chronological = iterator.next();
            if(isWithinInterval(chronological)) {
                broadcast(chronological);
                iterator.remove();
            }
        }
    }

    @Override
    public void broadcast(T object) {
        for (IObjectBroadcastListener<T> bl: listeners) {
            bl.onBroadcast(object);
        }
    }

    @Override
    public void addListener(IObjectBroadcastListener<T> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(IObjectBroadcastListener<T> listener) {
        listeners.remove(listener);
    }

    @Override
    public void run() {
        broadcastContent();
        removePassed();
    }
}
