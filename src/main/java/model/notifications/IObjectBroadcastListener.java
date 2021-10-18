package model.notifications;

public interface IObjectBroadcastListener<T> {

    void onBroadcast(T object);
}
