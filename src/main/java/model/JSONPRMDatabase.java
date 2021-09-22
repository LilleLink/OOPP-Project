package model;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

/***
 * The JSON database of the prm model.
 */
public class JSONPRMDatabase implements IPRMDatabase {

    // The file to save/load the model to.
    final private Path databaseFile;

    /***
     * Create a new JSONPRMDatabase to save/load the model to a json file.
     * @param databaseFile The json file to save/load the model to.
     */
    JSONPRMDatabase(Path databaseFile) {
        this.databaseFile = databaseFile;
    }

    /***
     * Load the model from the database file.
     * @param name The name of the user.
     * @return The loaded user.
     */
    @Override
    public User load(String name) {
        return null;
    }

    /***
     * Save the model to a database file.
     * @param user The user to save.
     * @throws IOException If there was a problem writing to the database file.
     */
    @Override
    public void save(User user) throws IOException {
        DatabaseState state = new DatabaseState();
        state.prm.users.add((UserRecord) user.accept(new CacheVisitor(), state).orElseThrow(IllegalStateException::new));
        Files.write(databaseFile, new Gson().toJson(state.prm).getBytes(), StandardOpenOption.WRITE);
    }

    // JSON records.
    // JSON record interface.
    private interface IRecord {
    }

    // Contact JSON record.
    static private class ContactRecord implements IRecord {
        String name;
        String phoneNumber;
        Address address;
    }

    // Event JSON record.
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    static private class EventRecord implements IRecord {
        String name;
        Address address = new Address("");
        String dateTime;
        String description;
        ArrayList<Integer> contacts = new ArrayList<>();
    }

    // User JSON record.
    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "unused"})
    static private class UserRecord implements IRecord {
        String name;
        List<Integer> contacts = new ArrayList<>();
        List<EventRecord> events = new ArrayList<>();
    }

    // PRM JSON record.
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    static private class PRMRecord implements IRecord {
        List<ContactRecord> contacts = new ArrayList<>();
        List<UserRecord> users = new ArrayList<>();
    }

    // The environment of the cache visitor.
    static private class DatabaseState implements IRecord {
        // The indices of contact records in the prm model.
        HashMap<Contact, Integer> contactIndices = new HashMap<>();
        // The final prm record to serialize.
        PRMRecord prm = new PRMRecord();
    }

    // The cache visitor visits the entire cache hierarchy of the model and returns a serializable JSON record.
    static private class CacheVisitor implements ICacheVisitor<DatabaseState, IRecord> {
        // Create a contact JSON record and return its index. If the record already exists, just return its index.
        private int createContact(Contact contact, DatabaseState env) {
            if (!env.contactIndices.containsKey(contact)) {
                ContactRecord result = (ContactRecord) contact.accept(this, env).orElseThrow(IllegalStateException::new);
                env.contactIndices.put(contact, env.prm.contacts.size());
                env.prm.contacts.add(result);
            }

            return env.contactIndices.get(contact);
        }

        // Visit user cache and return a JSON record of it.
        @Override
        public Optional<IRecord> visit(User.UserCache user, DatabaseState env) {
            UserRecord record = new UserRecord();
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
        public Optional<IRecord> visit(Contact.ContactCache contact, DatabaseState env) {
            ContactRecord record = new ContactRecord();
            record.address = contact.address;
            record.name = contact.name;
            record.phoneNumber = contact.phoneNumber;
            return Optional.of(record);
        }

        // Visit event cache and return a JSON record of it.
        @Override
        public Optional<IRecord> visit(Event.EventCache event, DatabaseState env) {
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
