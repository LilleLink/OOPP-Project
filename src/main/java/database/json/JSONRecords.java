package database.json;

import java.util.*;

/***
 * Class containing records of all the serializable classes, helps the json database-services to serialize and unserialize.
 */
public class JSONRecords {

    // The record visitor interface.
    interface IRecordVisitor<E, T> {
        Optional<T> visit(UserRecord user, E env);

        Optional<T> visit(ContactRecord contact, E env);

        Optional<T> visit(EventRecord event, E env);

        Optional<T> visit(NoteRecord note, E env);

        Optional<T> visit(NotesRecord notes, E env);

        Optional<T> visit(TagHandlerRecord tagHandler, E env);

        Optional<T> visit(TagRecord tag, E env);
    }

    // The visitable record interface.
    interface IRecordVisitable {
        <E, T> Optional<T> accept(IRecordVisitor<E, T> visitor, E env);
    }

    // JSON records.

    // Contact JSON record.
    static class TagRecord implements JSONRecords.IRecordVisitable {
        String name;
        String color;

        @Override
        public <E, T> Optional<T> accept(IRecordVisitor<E, T> visitor, E env) {
            return visitor.visit(this, env);
        }
    }

    static class TagHandlerRecord implements JSONRecords.IRecordVisitable {
        Map<String, TagRecord> tags;

        @Override
        public <E, T> Optional<T> accept(IRecordVisitor<E, T> visitor, E env) {
            return visitor.visit(this, env);
        }
    }

    static class ContactRecord implements JSONRecords.IRecordVisitable {
        String name;
        String phoneNumber;
        String address;
        NotesRecord notes;
        List<String> tags;
        String directoryId;

        @Override
        public <E, T> Optional<T> accept(JSONRecords.IRecordVisitor<E, T> visitor, E env) {
            return visitor.visit(this, env);
        }
    }

    static class NotesRecord implements JSONRecords.IRecordVisitable {
        List<NoteRecord> elements;

        @Override
        public <E, T> Optional<T> accept(IRecordVisitor<E, T> visitor, E env) {
            return visitor.visit(this, env);
        }
    }

    static class NoteRecord implements JSONRecords.IRecordVisitable {
        String text;
        String pointOfCreation;

        @Override
        public <E, T> Optional<T> accept(IRecordVisitor<E, T> visitor, E env) {
            return visitor.visit(this, env);
        }
    }

    // Event JSON record.
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    static class EventRecord implements JSONRecords.IRecordVisitable {
        String name;
        String address = "";
        String dateTime;
        String description;
        List<Integer> contacts = new ArrayList<>();
        String tag;
        String directoryId;

        @Override
        public <E, T> Optional<T> accept(JSONRecords.IRecordVisitor<E, T> visitor, E env) {
            return visitor.visit(this, env);
        }
    }

    // User JSON record.
    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "unused"})
    static class UserRecord implements JSONRecords.IRecordVisitable {
        String name;
        List<Integer> contacts = new ArrayList<>();
        List<ContactRecord> contactObjects = new ArrayList<>();
        List<EventRecord> events = new ArrayList<>();
        TagHandlerRecord tags;
        String uuid;

        @Override
        public <E, T> Optional<T> accept(JSONRecords.IRecordVisitor<E, T> visitor, E env) {
            return visitor.visit(this, env);
        }
    }

    static class TrackerRecord {
        Map<UUID, String> users = new HashMap<>();
    }

}
