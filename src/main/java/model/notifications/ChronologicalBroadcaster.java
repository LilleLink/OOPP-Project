package model.notifications;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChronologicalBroadcaster<T extends IChronological> implements IObjectBroadcaster<T>, Runnable {

    private final List<T> content;

    private final List<IObjectBroadcastListener<T>> listeners;

    ChronologicalBroadcaster(List<T> content) {
        this.content = content;
        removePassed();
        this.listeners = new ArrayList<>();
    }

    private void removePassed() {
        content.removeIf(timeable -> timeable.compareTime(LocalDateTime.now()) < 0);
    }

    private void broadcastContent() {
        for(Iterator<T> iterator = content.iterator(); iterator.hasNext();) {
            T timeable = iterator.next();
            if(0 <= timeable.compareTime(LocalDateTime.now())) {
                broadcast(timeable);
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
