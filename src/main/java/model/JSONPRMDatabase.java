package model;

import com.google.gson.Gson;

import javax.xml.crypto.Data;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/***
 * The JSON database of the prm model.
 */
public class JSONPRMDatabase implements IPRMDatabase {

    private Path databaseFile;

    JSONPRMDatabase(Path databaseFile) {
        this.databaseFile = databaseFile;
    }

    @Override
    public User load(String name) {
        return null;
    }

    @Override
    public void save(User user) {
        DatabaseState state = new DatabaseState();
        state.prm.users.add((UserRecord) Objects.requireNonNull(user.accept(new CacheVisitor(), state)));
        try {
            Files.write(databaseFile, new Gson().toJson(state.prm).getBytes(), StandardOpenOption.WRITE);
        } catch (Exception e) {
        }
    }

    private interface IRecord {};

    private class DatabaseState implements IRecord{
        HashMap<Contact, Integer> contactIndices = new HashMap<>();
        PRMRecord prm = new PRMRecord();
    }

    private class ContactRecord implements IRecord {
        String name;
        String phoneNumber;
        Address address;
    }

    private class EventRecord implements IRecord {
        String name;
        Address address = new Address("");
        String dateTime;
        String description;
        ArrayList<Integer> contacts = new ArrayList<>();
    }

    private class UserRecord implements IRecord {
        String name;
        List<Integer> contacts = new ArrayList<>();
        List<EventRecord> events = new ArrayList<>();
    }

    private class PRMRecord implements IRecord {
        List<ContactRecord> contacts = new ArrayList<>();
        List<UserRecord> users = new ArrayList<>();
    }

    private class CacheVisitor implements ICacheVisitor<DatabaseState, IRecord> {

        private int createContact(Contact contact, DatabaseState env) {
           if(!env.contactIndices.containsKey(contact)) {
               ContactRecord result = (ContactRecord) contact.accept(this, env);
               env.contactIndices.put(contact, env.prm.contacts.size());
               env.prm.contacts.add(result);
           }

           return env.contactIndices.get(contact);
        }

        @Override
        public IRecord visitUserCache(User.UserCache user, DatabaseState env) {
            UserRecord record = new UserRecord();
            for (Contact contact : user.contacts) {
                record.contacts.add(createContact(contact, env));
            }
            for (Event event : user.events) {
                record.events.add((EventRecord) Objects.requireNonNull(event.accept(this, env)));
            }
            return record;
        }

        @Override
        public IRecord visitContactCache(Contact.ContactCache contact, DatabaseState env) {
            ContactRecord record = new ContactRecord();
            record.address = contact.address;
            record.name = contact.name;
            record.phoneNumber = contact.phoneNumber;
            return record;
        }

        @Override
        public IRecord visitEventCache(Event.EventCache event, DatabaseState env) {
            EventRecord record = new EventRecord();
            record.address = event.address;
            record.dateTime = event.dateTime.toString();
            record.name = event.name;
            record.description = event.description;
            for (Contact contact : event.contacts) {
                record.contacts.add(createContact(contact, env));
            }
            return record;
        }
    }

}
