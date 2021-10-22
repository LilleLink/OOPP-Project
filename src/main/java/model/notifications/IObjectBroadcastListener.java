package model.notifications;

/**
 * A {@code IObjectBroadcastListener} allows for objects to be received from {@link IObjectBroadcaster} implementations.
 *
 * General contract of this interface is that implementations should in some way be interested in the given object from
 * the broadcaster.
 *
 * @param <T> the type of the objects to receive
 * @author Simon Johnsson
 */
public interface IObjectBroadcastListener<T> {

    /**
     * Alerts the listener of a broadcast object being received.
     *
     * @param object the received object
     */
    void onBroadcast(T object);
}
