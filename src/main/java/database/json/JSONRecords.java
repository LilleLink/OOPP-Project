package database.json;

import model.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JSONRecords {

    // The record visitor interface.
    interface IRecordVisitor<E, T> {
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
    interface IRecordVisitable {
        <E, T> Optional<T> accept(IRecordVisitor<E, T> visitor, E env);
    }

    // JSON records.

    // Contact JSON record.
    static class ContactRecord implements JSONRecords.IRecordVisitable {
        String name;
        String phoneNumber;
        Address address;
        NotesRecord notes;

        @Override
        public <E, T> Optional<T> accept(JSONRecords.IRecordVisitor<E, T> visitor, E env) {
            return visitor.visit(this, env);
        }
    }

    static class NotesRecord implements JSONRecords.IRecordVisitable {
        List<NoteRecord> elements;

        @Override
        public <E, T> Optional<T> accept(IRecordVisitor<E, T> visitor, E env) {
            return Optional.empty();
        }
    }

    static class NoteRecord implements JSONRecords.IRecordVisitable {
        String text;
        String pointOfCreation;

        @Override
        public <E, T> Optional<T> accept(IRecordVisitor<E, T> visitor, E env) {
            return Optional.empty();
        }
    }

    // Event JSON record.
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    static class EventRecord implements JSONRecords.IRecordVisitable {
        String name;
        Address address = new Address("");
        String dateTime;
        String description;
        ArrayList<Integer> contacts = new ArrayList<>();

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
        List<EventRecord> events = new ArrayList<>();

        @Override
        public <E, T> Optional<T> accept(JSONRecords.IRecordVisitor<E, T> visitor, E env) {
            return visitor.visit(this, env);
        }
    }

    // PRM JSON record.
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    static class PRMRecord implements JSONRecords.IRecordVisitable {
        List<ContactRecord> contacts = new ArrayList<>();
        List<UserRecord> users = new ArrayList<>();

        @Override
        public <E, T> Optional<T> accept(JSONRecords.IRecordVisitor<E, T> visitor, E env) {
            return visitor.visit(this, env);
        }
    }

}
