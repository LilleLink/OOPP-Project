package model;

public interface IObservable {
    /**
     * Adds an observer to an object
     * @param observer the observer to add
     */
    void subscribe(IObserver observer);

    /**
     * removes an observer from an object
     * @param observer the observer to remove
     */
    void unSubscribe(IObserver observer);

    /**
     * Notifies all subscribed observers
     */
    void notifyObservers();
}
