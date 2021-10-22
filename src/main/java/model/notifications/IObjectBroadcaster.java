package model.notifications;

/**
 * An {@code IObjectBroadcaster} wraps content and allows for objects from this to be broadcast.
 *
 * General contract of this interface is that any type of object can be broadcast, but the implementation
 * should clearly specify the broadcasts type. Implementations are strongly advised to broadcast immutable objects
 * to prevent unexpected mutations.
 *
 * @param <T> the type of the broadcast objects
 * @author Simon Johnsson
 */
public interface IObjectBroadcaster<T> {

    /**
     * Sends an object a {@link IObjectBroadcastListener} can receive.
     *
     * @param object the object to broadcast
     */
    void broadcast(T object);

    /**
     * Adds a {@link IObjectBroadcastListener} to receive broadcasts
     *
     * @param listener the receiver to add
     */
    void addListener(IObjectBroadcastListener<T> listener);

    /**
     * Removes a {@link IObjectBroadcastListener} from receiving broadcasts
     *
     * @param listener the receiver to remove
     */
    void removeListener(IObjectBroadcastListener<T> listener);

}
