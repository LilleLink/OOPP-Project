package model;

import java.time.LocalDate;

public class Event {

    private final String name;
    private final String address;
    private final LocalDate date;
    private final String description;

    public Event(String name, String address, LocalDate date, String description) {
        this.name = name;
        this.address = address;
        this.date = date;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Event setName(String name) {
        return new Event(name, this.address, this.date, this.description);
    }

    public String getAddress() {
        return address;
    }

    public Event setAddress(String address) {
        return new Event(this.name, address, this.date, this.description);
    }

    public LocalDate getDate() {
        return date;
    }

    public Event setDate(LocalDate date) {
        return new Event(this.name, this.address, date, this.description);
    }

    public String getDescription() {
        return description;
    }

    public Event setDescription(String description) {
        return new Event(this.name, this.address, this.date, description);
    }
}
