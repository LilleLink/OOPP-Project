package database.json;

import com.google.gson.Gson;
import database.IDatabaseLoader;
import model.*;
import model.notes.Note;
import model.notes.NoteBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class JSONDatabaseLoader implements IDatabaseLoader {

    /***
     * Load the model from the database file.
     * @return The loaded user.
     */
    @Override
    public User load(Path databaseFile) throws IOException {
        JSONRecords.PRMRecord record = new Gson().fromJson(String.join("\n", Files.readAllLines(databaseFile)), JSONRecords.PRMRecord.class);
        RecordVisitorState env = new RecordVisitorState();
        return (User) record.accept(new RecordVisitor(), env).orElseThrow(IllegalStateException::new);
    }

    // The environment of the record visitor.
    static private class RecordVisitorState {
        List<Contact> contacts = new ArrayList<>();
        HashMap<String, Tag> tags = new HashMap<>();
    }

    // The record visitor visists all the JSON records and returns the reinstated prm model.
    static private class RecordVisitor implements JSONRecords.IRecordVisitor<RecordVisitorState, ICacheVisitable> {
        @Override
        public Optional<ICacheVisitable> visit(JSONRecords.UserRecord user, RecordVisitorState env) {
            User.UserCache cache = new User.UserCache();
            cache.tagHandler = (TagHandler) user.tags.accept(this, env).orElseThrow(IllegalStateException::new);
            cache.name = user.name;
            cache.contacts = user.contacts.stream().map(i -> env.contacts.get(i)).collect(Collectors.toList());
            cache.events = user.events.stream().map(e -> (Event) e.accept(this, env).orElseThrow(IllegalStateException::new)).collect(Collectors.toList());
            cache.uuid = UUID.fromString(user.uuid);
            return Optional.of(new User(cache));
        }

        @Override
        public Optional<ICacheVisitable> visit(JSONRecords.ContactRecord contact, RecordVisitorState env) {
            Contact.ContactCache cache = new Contact.ContactCache();
            cache.name = contact.name;
            cache.phoneNumber = contact.phoneNumber;
            cache.address = contact.address;
            cache.tags = new ArrayList<>();
            cache.noteBook = (NoteBook) contact.notes.accept(this, env).orElseThrow(IllegalStateException::new);
            cache.directoryId = UUID.fromString(contact.directoryId);
            return Optional.of(new Contact(cache));
        }

        @Override
        public Optional<ICacheVisitable> visit(JSONRecords.EventRecord event, RecordVisitorState env) {
            Event.EventCache cache = new Event.EventCache();
            cache.name = event.name;
            cache.address = event.address;
            cache.dateTime = LocalDateTime.parse(event.dateTime);
            cache.description = event.description;
            cache.tag = env.tags.get(event.tag);
            cache.contacts = event.contacts.stream().map(i -> env.contacts.get(i)).collect(Collectors.toList());
            cache.directoryId = UUID.fromString(event.directoryId);
            return Optional.of(new Event(cache));
        }

        @Override
        public Optional<ICacheVisitable> visit(JSONRecords.PRMRecord prm, RecordVisitorState env) {
            prm.contacts.forEach(c -> env.contacts.add((Contact) this.visit(c, env).orElseThrow(IllegalStateException::new)));
            return prm.user.accept(this, env);
        }

        @Override
        public Optional<ICacheVisitable> visit(JSONRecords.NoteRecord note, RecordVisitorState env) {
            Note.NoteCache cache = new Note.NoteCache();
            cache.pointOfCreation = LocalDateTime.parse(note.pointOfCreation);
            cache.text = note.text;
            return Optional.of(new Note(cache));
        }

        @Override
        public Optional<ICacheVisitable> visit(JSONRecords.NotesRecord notes, RecordVisitorState env) {
            NoteBook.NotesCache cache = new NoteBook.NotesCache();
            cache.elements = notes.elements.stream().map(n -> (Note) n.accept(this, env).orElseThrow(IllegalStateException::new)).collect(Collectors.toList());
            return Optional.of(new NoteBook(cache));
        }

        @Override
        public Optional<ICacheVisitable> visit(JSONRecords.TagHandlerRecord tagHandler, RecordVisitorState env) {
            tagHandler.tags.forEach((t, v) -> env.tags.put(t, (Tag) v.accept(this, env).orElseThrow(IllegalStateException::new)));
            TagHandler.TagHandlerCache cache = new TagHandler.TagHandlerCache();
            cache.stringTagHashMap = env.tags;
            return Optional.of(new TagHandler(cache));
        }

        @Override
        public Optional<ICacheVisitable> visit(JSONRecords.TagRecord tag, RecordVisitorState env) {
            Tag.TagCache cache = new Tag.TagCache();
            cache.name = tag.name;
            cache.color = tag.color;
            return Optional.of(new Tag(cache));
        }
    }

}
