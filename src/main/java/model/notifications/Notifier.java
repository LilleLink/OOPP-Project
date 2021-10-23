package model.notifications;

import model.IObservable;
import model.IObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * A class responsible for collecting objects sent by a {@link ChronologicalBroadcaster}.
 * <p>
 * Notifications can be retrieved, cleared and muted.
 * <p>
 * The time interval before the point in time corresponding to the chronological object.
 * <p>
 * In order for the {@code Notifications} to run in the background, it needs to be given to a separate {@link Thread}.
 *
 * @param <T> the type of chronological objects to collect
 * @author Simon Johnsson
 */
public class Notifier<T extends IChronological> implements IObjectBroadcastListener<T>, Runnable, IObservable {

    private final List<T> active;
    private final List<IObserver> observers;
    private ChronologicalBroadcaster<T> broadcaster;
    private boolean isMuted;
    private long muteTime;

    public Notifier(List<T> content) {
        this.active = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.broadcaster = new ChronologicalBroadcaster<>(content);
        this.broadcaster.addListener(this);
        this.isMuted = false;
    }


    /**
     * Updates the broadcaster's interval to the given minutes.
     *
     * @param minutes the new notification time
     */
    public synchronized void setInterval(long minutes) {
        this.broadcaster = broadcaster.withInterval(minutes);
    }

    /**
     * Updates the broadcaster's content to the given list.
     *
     * @param content the new content
     */
    public synchronized void setContent(List<T> content) {
        this.broadcaster = broadcaster.withContent(content);
    }


    /**
     * Empties the list of notifications.
     */
    public synchronized void clear() {
        active.clear();
    }

    /**
     * Causes the thread which this notifications object is running on to sleep for the given milliseconds.
     *
     * @param millis the length of sleep in milliseconds
     */
    public synchronized void mute(long millis) {
        isMuted = true;
        muteTime = millis;
    }

    /**
     * Un-mutes notifications to receive notifications.
     */
    public synchronized void unmute() {
        isMuted = false;
    }

    /**
     * Puts the active thread to sleep as long as notifications are muted.
     * Catches an interrupted exception and breaks the while loop if the thread is interrupted while sleeping.
     *
     * @param millis the amount of time to sleep in milliseconds
     */
    private void muteSleep(long millis) {
        long t = millis;
        while (isMuted && t != 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                break;
            }
            t--;
        }
    }


    /**
     * Returns the currently active notifications.
     *
     * @return a list of type {@code T extends {@link IChronological}}
     */
    public synchronized List<T> getActive() {
        return new ArrayList<>(active);
    }

    /**
     * Returns the current amount of notifications.
     *
     * @return the amount of notifications
     */
    public synchronized int size() {
        return active.size();
    }

    @Override
    public void onBroadcast(T object) {
        active.add(object);
        notifyObservers();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (!isMuted) {
                broadcaster.run();
            } else {
                muteSleep(muteTime);
                unmute();
            }
        }
    }

    @Override
    public void subscribe(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unSubscribe(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.onEvent();
        }
    }
}
