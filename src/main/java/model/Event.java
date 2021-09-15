package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Event {

    private final String name;
    private final String address;
    private final LocalDate date;
    private final String description;

    private final List<Contact> registeredContacts;

    public Event(String name, String address, LocalDate date, String description, List<Contact> registeredContacts) {
        this.name = name;
        this.address = address;
        this.date = date;
        this.description = description;
        this.registeredContacts = registeredContacts;
    }

    public String getName() {
        return name;
    }

    public Event setName(String name) {
        return new Event(name, this.address, this.date, this.description, this.registeredContacts);
    }

    public String getAddress() {
        return address;
    }

    public Event setAddress(String address) {
        return new Event(this.name, address, this.date, this.description, this.registeredContacts);
    }

    public LocalDate getDate() {
        return date;
    }

    public Event setDate(LocalDate date) {
        return new Event(this.name, this.address, date, this.description, this.registeredContacts);
    }

    public String getDescription() {
        return description;
    }

    public Event setDescription(String description) {
        return new Event(this.name, this.address, this.date, description, this.registeredContacts);
    }

}
