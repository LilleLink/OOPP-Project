package database.json;

import com.google.gson.Gson;
import database.IDatabaseLoader;
import model.Contact;
import model.Event;
import model.ICacheVisitable;
import model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JSONDatabaseLoader implements IDatabaseLoader {

    /***
     * Load the model from the database file.
     * @param name The name of the user.
     * @return The loaded user.
     */
    @Override
    public User load(String name, Path databaseFile) throws IOException {
        JSONRecords.PRMRecord record = new Gson().fromJson(String.join("\n", Files.readAllLines(databaseFile)), JSONRecords.PRMRecord.class);
        RecordVisitorState env = new RecordVisitorState();
        return (User) record.accept(new RecordVisitor(), env).orElseThrow(IllegalStateException::new);
    }

    // The environment of the record visitor.
    static private class RecordVisitorState {
        List<Contact> contacts = new ArrayList<>();
    }

    // The record visitor visists all the JSON records and returns the reinstated prm model.
    static private class RecordVisitor implements JSONRecords.IRecordVisitor<RecordVisitorState, ICacheVisitable> {
        @Override
        public Optional<ICacheVisitable> visit(JSONRecords.UserRecord user, RecordVisitorState env) {
            User.UserCache cache = new User.UserCache();
            cache.name = user.name;
            cache.contacts = user.contacts.stream().map(i -> env.contacts.get(i)).collect(Collectors.toList());
            cache.events = user.events.stream().map(e -> (Event) e.accept(this, env).orElseThrow(IllegalStateException::new)).collect(Collectors.toList());
            return Optional.of(new User(cache));
        }

        @Override
        public Optional<ICacheVisitable> visit(JSONRecords.ContactRecord contact, RecordVisitorState env) {
            Contact.ContactCache cache = new Contact.ContactCache();
            cache.name = contact.name;
            cache.phoneNumber = contact.phoneNumber;
            cache.address = contact.address;
            cache.tags = new ArrayList<>();
            cache.notes = null;
            return Optional.of(new Contact(cache));
        }

        @Override
        public Optional<ICacheVisitable> visit(JSONRecords.EventRecord event, RecordVisitorState env) {
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
        public Optional<ICacheVisitable> visit(JSONRecords.PRMRecord prm, RecordVisitorState env) {
            prm.contacts.forEach(c -> env.contacts.add((Contact) this.visit(c, env).orElseThrow(IllegalStateException::new)));
            return prm.users.get(0).accept(this, env);
        }
    }

}
