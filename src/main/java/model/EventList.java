package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EventList implements IObservable {

    private List<Event> eventList = new ArrayList<>();
    private List<IObserver> observers = new ArrayList<>();

    /***
     * Creates a new eventlist wrapper object
     */
    EventList() {}

    /***
     * Wraps a given list of events.
     * @param events the list to be wrapped
     */
    EventList(List<Event> events) {
        this.eventList = events;
    }

    /***
     * Adds an event to the eventList.
     * @param name the name of the event
     */
    public void addEvent(String name, LocalDateTime dateTime) {
        eventList.add(new Event(name, dateTime));
        notifyObservers();
    }

    /***
     * Adds an event with the given parameters to the list
     * @param name name of event
     * @param dateTime time of event
     * @param address address of event
     * @param description of event
     * @param contacts participants
     * @param tag category
     */
    public void addEvent(String name, LocalDateTime dateTime, String address, String description, List<Contact> contacts, ITag tag) {
        eventList.add(new Event(name, address, dateTime, description, contacts, tag));
        notifyObservers();
    }

    /***
     * Returns a list of events in the same week as the given localDate object.
     * @param localDate the date object
     * @return a list of events
     */
    public List<Event> getEventsOfWeek(LocalDate localDate) {
        TemporalField weekGetter = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int week = localDate.get(weekGetter);

        List<Event> eventsOfWeek = new ArrayList<>();

        for (Event event : this.eventList) {
            if (event.getDateTime().get(weekGetter) == week) {
                eventsOfWeek.add(event);
            }
        }

        return eventsOfWeek;
    }

    /***
     * Adds a event to the eventList
     * @param event the event object
     */
    public void addEvent(Event event) {
        eventList.add(event);
        notifyObservers();
    }

    /***
     * Removes a event from the eventList
     * @param event the event to be removed
     */
    public void removeEvent(Event event) {
        eventList.remove(event);
        notifyObservers();
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
