package database.json;

import com.google.gson.Gson;
import database.IDatabaseSaver;
import model.Contact;
import model.Event;
import model.ICacheVisitor;
import model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Optional;

public class JSONDatabaseSaver implements IDatabaseSaver {

    /***
     * Save the model to a database file.
     * @param user The user to save.
     * @throws IOException If there was a problem writing to the database file.
     */
    @Override
    public void save(User user, Path databaseFile) throws IOException {
        CacheVisitorState state = new CacheVisitorState();
        state.prm.users.add((JSONRecords.UserRecord) user.accept(new CacheVisitor(), state).orElseThrow(IllegalStateException::new));
        Files.write(databaseFile, new Gson().toJson(state.prm).getBytes(), StandardOpenOption.WRITE);
    }

    // The environment of the cache visitor.
    static private class CacheVisitorState {
        // The indices of contact records in the prm model.
        HashMap<Contact, Integer> contactIndices = new HashMap<>();
        // The final prm record to serialize.
        JSONRecords.PRMRecord prm = new JSONRecords.PRMRecord();
    }

    // The cache visitor visits the entire cache hierarchy of the model and returns a serializable JSON record.
    static private class CacheVisitor implements ICacheVisitor<CacheVisitorState, JSONRecords.IRecord> {
        // Create a contact JSON record and return its index. If the record already exists, just return its index.
        private int createContact(Contact contact, CacheVisitorState env) {
            if (!env.contactIndices.containsKey(contact)) {
                JSONRecords.ContactRecord result = (JSONRecords.ContactRecord) contact.accept(this, env).orElseThrow(IllegalStateException::new);
                env.contactIndices.put(contact, env.prm.contacts.size());
                env.prm.contacts.add(result);
            }

            return env.contactIndices.get(contact);
        }

        // Visit user cache and return a JSON record of it.
        @Override
        public Optional<JSONRecords.IRecord> visit(User.UserCache user, CacheVisitorState env) {
            JSONRecords.UserRecord record = new JSONRecords.UserRecord();
            record.name = user.name;
            // Add contact indices to user record.
            for (Contact contact : user.contacts) {
                record.contacts.add(createContact(contact, env));
            }
            // Add events to user record.
            for (Event event : user.events) {
                record.events.add((JSONRecords.EventRecord) event.accept(this, env).orElseThrow(IllegalStateException::new));
            }
            return Optional.of(record);
        }

        // Visit contact cache and return a JSON record of it.
        @Override
        public Optional<JSONRecords.IRecord> visit(Contact.ContactCache contact, CacheVisitorState env) {
            JSONRecords.ContactRecord record = new JSONRecords.ContactRecord();
            record.address = contact.address;
            record.name = contact.name;
            record.phoneNumber = contact.phoneNumber;
            return Optional.of(record);
        }

        // Visit event cache and return a JSON record of it.
        @Override
        public Optional<JSONRecords.IRecord> visit(Event.EventCache event, CacheVisitorState env) {
            JSONRecords.EventRecord record = new JSONRecords.EventRecord();
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
