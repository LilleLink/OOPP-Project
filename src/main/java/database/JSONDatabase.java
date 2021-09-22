package database;

import com.google.gson.Gson;
import model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

//TODO: This file is very long, should probably be separated into a JSON package.

/***
 * The JSON database of the prm model.
 */
public class JSONDatabase implements IDatabase {

    // The file to save/load the model to.
    final private Path databaseFile;

    /***
     * Create a new JSONPRMDatabase to save/load the model to a json file.
     * @param databaseFile The json file to save/load the model to.
     */
    JSONDatabase(Path databaseFile) {
        this.databaseFile = databaseFile;
    }

    /////////////////////////////////////////// Loading ///////////////////////////////////////////

    /***
     * Load the model from the database file.
     * @param name The name of the user.
     * @return The loaded user.
     */
    @Override
    public User load(String name) throws IOException {
        PRMRecord record = new Gson().fromJson(String.join(",", Files.readAllLines(databaseFile)), PRMRecord.class);
        RecordVisitorState env = new RecordVisitorState();
        return (User) record.accept(new RecordVisitor(), env).orElseThrow(IllegalStateException::new);
    }

    // The record visitor interface.
    private interface IRecordVisitor<E, T> {
        default Optional<T> visit(UserRecord user, E env) {
            return Optional.empty();
        }

        default Optional<T> visit(ContactRecord contact, E env) {
            return Optional.empty();
        }

        default Optional<T> visit(EventRecord event, E env) {
            return Optional.empty();
        }

        default Optional<T> visit(PRMRecord prm, E env) {
            return Optional.empty();
        }
    }

    // The visitable record interface.
    private interface IRecordVisitable {
        <E, T> Optional<T> accept(IRecordVisitor<E, T> visitor, E env);
    }

    // The environment of the record visitor.
    static private class RecordVisitorState {
        List<Contact> contacts = new ArrayList<>();
    }

    // The record visitor visists all the JSON records and returns the reinstated prm model.
    static private class RecordVisitor implements IRecordVisitor<RecordVisitorState, Object> {
        @Override
        public Optional<Object> visit(UserRecord user, RecordVisitorState env) {
            User.UserCache cache = new User.UserCache();
            cache.name = user.name;
            cache.contacts = user.contacts.stream().map(i -> env.contacts.get(i)).collect(Collectors.toList());
            cache.events = user.events.stream().map(e -> (Event) e.accept(this, env).orElseThrow(IllegalStateException::new)).collect(Collectors.toList());
            return Optional.of(new User(cache));
        }

        @Override
        public Optional<Object> visit(ContactRecord contact, RecordVisitorState env) {
            Contact.ContactCache cache = new Contact.ContactCache();
            cache.name = contact.name;
            cache.phoneNumber = contact.phoneNumber;
            cache.address = contact.address;
            cache.tags = new ArrayList<>();
            cache.notes = null;
            return Optional.of(new Contact(cache));
        }

        @Override
        public Optional<Object> visit(EventRecord event, RecordVisitorState env) {
            Event.EventCache cache = new Event.EventCache();
            cache.name = event.name;
            cache.address = event.address;
            cache.dateTime = LocalDateTime.parse(event.dateTime);
            cache.description = event.description;
            cache.tag = null;
            cache.contacts = event.contacts.stream().map(i -> env.contacts.get(i)).collect(Collectors.toList());
            return Optional.of(new Event(cache));
        }

        @Override
        public Optional<Object> visit(PRMRecord prm, RecordVisitorState env) {
            prm.contacts.forEach(c -> env.contacts.add((Contact) this.visit(c, env).orElseThrow(IllegalStateException::new)));
            return prm.users.get(0).accept(this, env);
        }
    }

    /////////////////////////////////////////// Saving ///////////////////////////////////////////

    /***
     * Save the model to a database file.
     * @param user The user to save.
     * @throws IOException If there was a problem writing to the database file.
     */
    @Override
    public void save(User user) throws IOException {
        CacheVisitorState state = new CacheVisitorState();
        state.prm.users.add((UserRecord) user.accept(new CacheVisitor(), state).orElseThrow(IllegalStateException::new));
        Files.write(databaseFile, new Gson().toJson(state.prm).getBytes(), StandardOpenOption.WRITE);
    }

    // JSON records.
    // JSON record interface.
    private interface IRecord {
    }

    // Contact JSON record.
    static private class ContactRecord implements IRecord, IRecordVisitable {
        String name;
        String phoneNumber;
        Address address;

        @Override
        public <E, T> Optional<T> accept(IRecordVisitor<E, T> visitor, E env) {
            return visitor.visit(this, env);
        }
    }

    // Event JSON record.
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    static private class EventRecord implements IRecord, IRecordVisitable {
        String name;
        Address address = new Address("");
        String dateTime;
        String description;
        ArrayList<Integer> contacts = new ArrayList<>();

        @Override
        public <E, T> Optional<T> accept(IRecordVisitor<E, T> visitor, E env) {
            return visitor.visit(this, env);
        }
    }

    // User JSON record.
    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "unused"})
    static private class UserRecord implements IRecord, IRecordVisitable {
        String name;
        List<Integer> contacts = new ArrayList<>();
        List<EventRecord> events = new ArrayList<>();

        @Override
        public <E, T> Optional<T> accept(IRecordVisitor<E, T> visitor, E env) {
            return visitor.visit(this, env);
        }
    }

    // PRM JSON record.
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    static private class PRMRecord implements IRecord, IRecordVisitable {
        List<ContactRecord> contacts = new ArrayList<>();
        List<UserRecord> users = new ArrayList<>();

        @Override
        public <E, T> Optional<T> accept(IRecordVisitor<E, T> visitor, E env) {
            return visitor.visit(this, env);
        }
    }

    // The environment of the cache visitor.
    static private class CacheVisitorState {
        // The indices of contact records in the prm model.
        HashMap<Contact, Integer> contactIndices = new HashMap<>();
        // The final prm record to serialize.
        PRMRecord prm = new PRMRecord();
    }

    // The cache visitor visits the entire cache hierarchy of the model and returns a serializable JSON record.
    static private class CacheVisitor implements ICacheVisitor<CacheVisitorState, IRecord> {
        // Create a contact JSON record and return its index. If the record already exists, just return its index.
        private int createContact(Contact contact, CacheVisitorState env) {
            if (!env.contactIndices.containsKey(contact)) {
                ContactRecord result = (ContactRecord) contact.accept(this, env).orElseThrow(IllegalStateException::new);
                env.contactIndices.put(contact, env.prm.contacts.size());
                env.prm.contacts.add(result);
            }

            return env.contactIndices.get(contact);
        }

        // Visit user cache and return a JSON record of it.
        @Override
        public Optional<IRecord> visit(User.UserCache user, CacheVisitorState env) {
            UserRecord record = new UserRecord();
            record.name = user.name;
            // Add contact indices to user record.
            for (Contact contact : user.contacts) {
                record.contacts.add(createContact(contact, env));
            }
            // Add events to user record.
            for (Event event : user.events) {
                record.events.add((EventRecord) event.accept(this, env).orElseThrow(IllegalStateException::new));
            }
            return Optional.of(record);
        }

        // Visit contact cache and return a JSON record of it.
        @Override
        public Optional<IRecord> visit(Contact.ContactCache contact, CacheVisitorState env) {
            ContactRecord record = new ContactRecord();
            record.address = contact.address;
            record.name = contact.name;
            record.phoneNumber = contact.phoneNumber;
            return Optional.of(record);
        }

        // Visit event cache and return a JSON record of it.
        @Override
        public Optional<IRecord> visit(Event.EventCache event, CacheVisitorState env) {
            EventRecord record = new EventRecord();
            record.address = event.address;
            record.dateTime = event.dateTime.toString();
            record.name = event.name;
            record.description = event.description;
            // Add contact indices to event record.
            for (Contact contact : event.contacts) {
                record.contacts.add(createContact(contact, env));
            }
            return Optional.of(record);
        }
    }

}
