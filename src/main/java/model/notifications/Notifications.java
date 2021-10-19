package model.notifications;

import java.util.ArrayList;
import java.util.List;

/**
 * A class responsible for collecting objects sent by a {@link ChronologicalBroadcaster}.
 *
 * Notifications can be retrieved, cleared and muted.
 *
 * In order for the {@code Notifications} to run in the background, it needs to be given to a separate {@link Thread}.
 *
 * @param <T> the type of chronological objects to collect
 * @author Simon Johnsson
 */
public class Notifications<T extends IChronological> implements IObjectBroadcastListener<T>, Runnable{

    private final List<T> active;
    private final ChronologicalBroadcaster<T> broadcaster;

    Notifications(List<T> content) {
        this.active = new ArrayList<>();
        this.broadcaster = new ChronologicalBroadcaster<>(content);
    }


    synchronized void clear() {
        active.clear();
    }

    synchronized void mute(long milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }

    List<T> getActive() {
        return new ArrayList<>(active);
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
