package model;

public interface IPRMVisitor<T, E> {

    T visitUser(User user, E env);
    T visitContact(Contact contact, E env);
    T visitEvent(Event event, E env);
    T visitNotes(Notes notes, E env);

}
