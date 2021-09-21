package model;

public interface IPRMVisitor<E, T> {

    T visitUser(User user, E env);
    T visitContact(Contact contact, E env);
    T visitEvent(Event event, E env);
}
