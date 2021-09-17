package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Event {

    private String name;
    private Address address;
    private LocalDateTime dateTime;
    private String description;

    private final ArrayList<UUID> tagIdList = new ArrayList<>();
    private ArrayList<UUID> contactIdList = new ArrayList<>();

    public Event(String name, String address, LocalDateTime dateTime, String description, ArrayList<UUID> contactIdList) {
        this.name = name;
        this.address = new Address(address);
        this.dateTime = dateTime;
        this.description = description;
        this.contactIdList = contactIdList;
    }

    public Event(String name, LocalDateTime date){
        this.name = name;
        this.dateTime = date;
    }

    public String getName() {
        return name;
    }

    public Event setName(String name) {
        return new Event(name, this.address.getAddress(), this.dateTime, this.description, this.contactIdList);
    }

    public String getAddress() {
        return address.getAddress();
    }

    public Event setAddress(String address) {
        return new Event(this.name, address, this.dateTime, this.description, this.contactIdList);
    }

    public LocalDateTime getDate() {
        return dateTime;
    }

    public Event setDate(LocalDateTime dateTime) {
        return new Event(this.name, this.address.getAddress(), dateTime, this.description, this.contactIdList);
    }

    public String getDescription() {
        return description;
    }

    public Event setDescription(String description) {
        return new Event(this.name, this.address.getAddress(), this.dateTime, description, this.contactIdList);
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

    public boolean addContact(UUID id){
        if (contactIdList.contains(id)){
            return false;
        }
        contactIdList.add(id);
        return true;
    }

    public boolean removeContact(UUID id){
        return contactIdList.remove(id);
    }

    public ArrayList<UUID> getContactIdList(){
        return new ArrayList<>(contactIdList);
    }
}
