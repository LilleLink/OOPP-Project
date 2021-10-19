package model.notifications;

import java.util.ArrayList;
import java.util.List;

/**
 * A class responsible for collecting objects sent by a {@link ChronologicalBroadcaster}.
 *
 * Notifications can be retrieved, cleared and muted.
 *
 * The time interval before the point in time corresponding to the chronological object.
 *
 * In order for the {@code Notifications} to run in the background, it needs to be given to a separate {@link Thread}.
 *
 * @param <T> the type of chronological objects to collect
 * @author Simon Johnsson
 */
public class Notifications<T extends IChronological> implements IObjectBroadcastListener<T>, Runnable{

    private final List<T> active;
    private ChronologicalBroadcaster<T> broadcaster;

    Notifications(List<T> content) {
        this.active = new ArrayList<>();
        this.broadcaster = new ChronologicalBroadcaster<>(content);
    }

    /**
     * Updates the broadcaster's interval to the given minutes.
     *
     * Note: changes are not reflected during {@code runtime}.
     *
     * @param minutes the new notification time
     */
    synchronized void setInterval(long minutes) {
        this.broadcaster = broadcaster.withInterval(minutes);
    }

    /**
     * Updates the broadcaster's content to the given list.
     *
     * Note: changes are not reflected during {@code runtime}.
     *
     * @param content the new content
     */
    synchronized void setContent(List<T> content) {
        this.broadcaster = broadcaster.withContent(content);
    }


    /**
     * Empties the list of notifications.
     */
    synchronized void clear() {
        active.clear();
    }

    /**
     * Causes the thread which this notifications object is running on to sleep for the given milliseconds.
     *
     * @param millis the length of sleep in milliseconds
     * @throws InterruptedException if the current thread is interrupted while muted.
     */
    synchronized void mute(long millis) throws InterruptedException {
        Thread.sleep(millis);
    }

    /**
     * Returns the currently active notifications.
     *
     * @return a list of type {@code T extends {@link IChronological}}
     */
    synchronized List<T> getActive() {
        return new ArrayList<>(active);
    }

    /**
     * Returns the current amount of notifications.
     *
     * @return the amount of notifications
     */
    synchronized int size() {
        return active.size();
    }

    @Override
    public void onBroadcast(T object) {
        active.add(object);
    }

    @Override
    public void run() {
        broadcaster.run();
    }
}
