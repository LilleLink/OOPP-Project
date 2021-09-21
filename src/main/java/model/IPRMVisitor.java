package model;

public interface IPRMVisitor<E> {

    void visitUser(User user, E env);
    void visitContact(Contact contact, E env);
    void visitEvent(Event event, E env);
    void visitNotes(Notes notes, E env);

}
