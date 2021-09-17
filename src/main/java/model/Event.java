package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Event {

    private String name;
    private String address;
    private LocalDateTime dateTime;
    private String description;

    private final ArrayList<UUID> tagIdList = new ArrayList<>();
    private List<Contact> registeredContacts;

    public Event(String name, String address, LocalDateTime dateTime, String description, List<Contact> registeredContacts) {
        this.name = name;
        this.address = address;
        this.dateTime = dateTime;
        this.description = description;
        this.registeredContacts = registeredContacts;
    }

    public Event(String name, LocalDateTime date){
        this.name = name;
        this.dateTime = date;
    }

    public String getName() {
        return name;
    }

    public Event setName(String name) {
        return new Event(name, this.address, this.dateTime, this.description, this.registeredContacts);
    }

    public String getAddress() {
        return address;
    }

    public Event setAddress(String address) {
        return new Event(this.name, address, this.dateTime, this.description, this.registeredContacts);
    }

    public LocalDateTime getDate() {
        return dateTime;
    }

    public Event setDate(LocalDateTime dateTime) {
        return new Event(this.name, this.address, dateTime, this.description, this.registeredContacts);
    }

    public String getDescription() {
        return description;
    }

    public Event setDescription(String description) {
        return new Event(this.name, this.address, this.dateTime, description, this.registeredContacts);
    }

    public List<Contact> getRegisteredContacts() {
        return new ArrayList<>(this.registeredContacts);
    }

    public boolean addContact(Contact contact) {
        return this.registeredContacts.add(contact);
    }

    public boolean addContacts(List<Contact> contactList) {
        return this.registeredContacts.addAll(contactList);
    }

    public boolean removeContact(Contact contact) {
        return this.registeredContacts.remove(contact);
    }

    public boolean removeContacts(List<Contact> contactList) {
        return this.registeredContacts.removeAll(contactList);
    }

    public boolean addTag(UUID tag){
        if (tagIdList.contains(tag)) return false;
        tagIdList.add(tag);
        return true;
    }

    public boolean removeTag(UUID id){
        return tagIdList.remove(id);
    }

    public ArrayList<UUID> getTagsIdList(){
        return new ArrayList<>(tagIdList);
    }
}
