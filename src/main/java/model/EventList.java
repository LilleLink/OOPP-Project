package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventList implements IObservable {

    private List<Event> eventList = new ArrayList<>();
    private List<IObserver> observers = new ArrayList<>();

    /***
     * Creates a new eventlist wrapper object
     */
    public EventList() {}

    /***
     * Wraps a given list of events.
     * @param events the list to be wrapped
     */
    public EventList(List<Event> events) {
        this.eventList = events;
    }

    /***
     * Adds a event to the eventList.
     * @param name the name of the event
     */
    public void addEvent(String name, LocalDateTime dateTime) {
        eventList.add(new Event(name, dateTime));
    }

    /***
     * Adds a event to the eventList
     * @param event the event object
     */
    public void addEvent(Event event) {
        eventList.add(event);
    }

    /***
     * Removes a event from the eventList
     * @param event the event to be removed
     */
    public void removeEvent(Event event) {
        eventList.remove(event);
    }

    /***
     * Returns the eventList
     * @return the event list
     */
    public List<Event> getList() {
        return this.eventList;
    }

    @Override
    public void subscribe(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unSubscribe(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers){
            observer.onEvent();
        }
    }

}
