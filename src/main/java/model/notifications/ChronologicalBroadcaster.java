package model.notifications;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class responsible for broadcasting {@link IChronological} objects with a given interval.
 *
 * @param <T>
 * @author Simon Johnsson
 */
public class ChronologicalBroadcaster<T extends IChronological> implements IObjectBroadcaster<T>, Runnable {

    private final List<T> content;
    private final List<IObjectBroadcastListener<T>> listeners;

    ChronologicalBroadcaster(List<T> content) {
        this.content = content;
        removePassed();
        this.listeners = new ArrayList<>();
    }

    /**
     * Removes all content that has passed the current time.
     */
    private void removePassed() {
        content.removeIf(this::hasPassed);
    }

    /**
     * Checks if a chronological object has passed the current time.
     *
     * @param chronological the object to check
     * @return true if the time has passed - false if not
     */
    private boolean hasPassed(IChronological chronological) {
        return chronological.compareTime(LocalDateTime.now()) < 0;
    }

    private void broadcastContent() {
        for(Iterator<T> iterator = content.iterator(); iterator.hasNext();) {
            T chronological = iterator.next();
            if(0 <= chronological.compareTime(LocalDateTime.now())) {
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
        while(true){
            broadcastContent();
            removePassed();
        }
    }
}
