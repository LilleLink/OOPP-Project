package model;

import model.notes.Note;
import model.notes.NoteBook;

import java.util.Optional;

/***
 * The interface for a visitor which visits all the cache of the model.
 * @param <E> The type of the cache visitor environment.
 * @param <T> The return type of the cache visitor cases.
 */
public interface ICacheVisitor<E, T> {

    /***
     * Visit a user.
     * @param user The user to visit.
     * @param env The visitor environment.
     * @return The result of the visitor case.
     */
    default Optional<T> visit(User.UserCache user, E env) {
        return Optional.empty();
    }

    /***
     * Visit a contact.
     * @param contact The contact to visit.
     * @param env The visitor environment.
     * @return The result of the visitor case.
     */
    default Optional<T> visit(Contact.ContactCache contact, E env) {
        return Optional.empty();
    }

    /***
     * Visit an event.
     * @param event The event to visit.
     * @param env The visitor environment.
     * @return The result of the visitor case.
     */
    default Optional<T> visit(Event.EventCache event, E env) {
        return Optional.empty();
    }

    default Optional<T> visit(NoteBook.NotesCache event, E env) {
        return Optional.empty();
    }

    default Optional<T> visit(Note.NoteCache event, E env) {
        return Optional.empty();
    }

    default Optional<T> visit(TagHandler.TagHandlerCache tagHandlerCache, E env) {
        return Optional.empty();
    }

    default Optional<T> visit(Tag.TagCache tagCache, E env) {
        return Optional.empty();
    }

}
