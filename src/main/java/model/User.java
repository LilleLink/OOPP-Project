package model;

import javax.lang.model.type.ArrayType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
    //TODO fix javadoc, rushing to get runnable version W3
    private String name;
    private final Map<UUID, Contact> contactMap = new HashMap<>();
    private final ArrayList<Event> eventList = new ArrayList<>();

    public User(String name){
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void addContact(String name){
        Contact contact = new Contact(name);
        contactMap.put(contact.getId(), contact);
    }

    public String getContactName(UUID id){
        return contactMap.get(id).getName();
    }

    public String getContactPhoneNumber(UUID id){
        return contactMap.get(id).getPhoneNumber();
    }

    public void setContactName(UUID id, String name){
        Contact updatedContact = contactMap.get(id).setName(name);
        contactMap.replace(id, updatedContact);
    }

    public void setContactPhoneNumber(UUID id, String phoneNumber){
        Contact updatedContact = contactMap.get(id).setPhoneNumber(phoneNumber);
        contactMap.replace(id, updatedContact);
    }

    //todo check if contact actually exists for add and remove tag methods
    public boolean addContactTag(UUID contactId, UUID tagId){
        Contact contact = contactMap.get(contactId);
        return(contact.addTag(tagId));
    }

    public boolean removeContactTag(UUID contactId, UUID tagId){
        Contact contact = contactMap.get(contactId);
        return(contact.removeTag(tagId));
    }

    public ArrayList<UUID> getContactTagIds(UUID contactId){
        Contact contact = contactMap.get(contactId);
        return contact.getTagsIdList();
    }

    public void addEvent(String name, LocalDateTime dateTime){
        eventList.add(new Event(name, dateTime));
    }

    public void removeEvent(Event event){
        eventList.remove(event);
    }

    public ArrayList<Event> getEvents(){
        return new ArrayList<>(eventList);
    }


}
