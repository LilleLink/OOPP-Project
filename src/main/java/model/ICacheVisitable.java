package model;

/***
 * The interface for a visitable part of the PRM model.
 */
interface ICacheVisitable {

    /**
     * Accept a visitor and invoke the correct visitor case.
     * @param visitor The visitor to accept.
     * @param env The environment state of the visitor.
     * @param <E> The type of the visitor environment state.
     * @param <T> The type of the visitor return value.
     * @return The result of the visitor case.
     */
    <E, T>T accept(ICacheVisitor<E, T> visitor, E env);

}
