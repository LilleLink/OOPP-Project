package model.notifications;

import java.util.ArrayList;
import java.util.List;

public class Notifications<T extends IChronological> implements IObjectBroadcastListener<T>, Runnable{

    private final List<T> active;
    private final ChronologicalBroadcaster<T> broadcaster;

    Notifications(List<T> content) {
        this.active = new ArrayList<>();
        this.broadcaster = new ChronologicalBroadcaster<>(content);
    }


    void clear() {
        active.clear();
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
