package model;

import com.google.gson.Gson;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
        state.prm.users.add((UserRecord) Objects.requireNonNull(user.accept(new ContactVisitor(), state)));
        try {
            Files.write(databaseFile, new Gson().toJson(state.prm).getBytes(), StandardOpenOption.WRITE);
        } catch (Exception e) { }
    }

    private class DatabaseState {
        HashMap<Contact, Integer> contactIndices = new HashMap<>();
        PRMRecord prm = new PRMRecord();
    }

    private class ContactRecord {
        String name;
        String phoneNumber;
        Address address;
    }

    private class EventRecord {
        String name;
        Address address = new Address("");
        String dateTime;
        String description;
        ArrayList<Integer> contacts = new ArrayList<>();
    }

    private class UserRecord {
        String name;
        List<Integer> contacts = new ArrayList<>();
        List<EventRecord> events = new ArrayList<>();
    }

    private class PRMRecord {
        List<ContactRecord> contacts = new ArrayList<>();
        List<UserRecord> users = new ArrayList<>();
    }

    private class ContactVisitor implements IPRMVisitor<DatabaseState, Object> {
        @Override
        public Object visitUser(User user, DatabaseState env) {
            UserRecord record = new UserRecord();
            for (Contact contact : user.getContacts()) {
                contact.accept(this, env);
                record.contacts.add((Integer) Objects.requireNonNull(contact.accept(this, env)));
            }
            for (Event event : user.getEventList()) {
                record.events.add((EventRecord) Objects.requireNonNull(event.accept(this, env)));
            }
            return record;
        }

        @Override
        public Object visitContact(Contact contact, DatabaseState env) {
            if (!env.contactIndices.containsKey(contact)) {
                ContactRecord record = new ContactRecord();
                record.address = new Address(contact.getAddress());
                record.name = contact.getName();
                record.phoneNumber = contact.getPhoneNumber();
                env.contactIndices.put(contact, env.prm.contacts.size());
                env.prm.contacts.add(record);
            }
            return env.contactIndices.get(contact);
        }

        @Override
        public Object visitEvent(Event event, DatabaseState env) {
            EventRecord record = new EventRecord();
            record.address = new Address(event.getAddress());
            record.dateTime = event.getDateTime().toString();
            record.name = event.getName();
            record.description = event.getDescription();
            for (Contact contact : event.getContacts()) {
                record.contacts.add((Integer) Objects.requireNonNull(contact.accept(this, env)));
            }
            return record;
        }
    }

}
