package model;

/***
 * The PRM visitor can visit the entire PRM model.
 * @param <E> The type of the visitor environment.
 * @param <T> The return type of the visitor cases.
 */
public interface IPRMVisitor<E, T> {

    /***
     * Visit a user.
     * @param user The user to visit.
     * @param env The visitor environment.
     * @return The result of the visitor case.
     */
    T visitUser(User user, E env);

    /***
     * Visit a contact.
     * @param contact The contact to visit.
     * @param env The visitor environment.
     * @return The result of the visitor case.
     */
    T visitContact(Contact contact, E env);

    /***
     * Visit an event.
     * @param event The event to visit.
     * @param env The visitor environment.
     * @return The result of the visitor case.
     */
    T visitEvent(Event event, E env);

}
