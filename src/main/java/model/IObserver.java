package model;

public interface IObserver {
    /**
     * Updates after a subscription notifies this
     */
    void onEvent();
}
