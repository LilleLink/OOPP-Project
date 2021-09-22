package model;

/***
 * The interface for a visitable part of the PRM model.
 */
public interface IPRMVisitable {

    /**
     * Accept a visitor and invoke the correct visitor case.
     * @param visitor The visitor to accept.
     * @param env The environment state of the visitor.
     * @param <E> The type of the visitor environment state.
     * @param <T> The type of the visitor return value.
     * @return The result of the visitor case.
     */
    <E, T>T accept(IPRMVisitor<E, T> visitor, E env);

}
