package model.notifications;

public interface IObjectBroadcaster<T> {

    void broadcast(T object);

    void addListener(IObjectBroadcastListener<T> listener);

    void removeListener(IObjectBroadcastListener<T> listener);

}
