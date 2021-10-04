package model;

import java.util.Optional;

/***
 * The interface for a part of the model with visitable cache.
 */
public interface ICacheVisitable {

    /***
     * Accept a cache visitor and pass the model cache to the correct case in the visitor.
     * @param visitor The cache visitor to accept.
     * @param env     The environment state of the cache visitor. This is the same for all visitor cases.
     * @param <E>     The type of the cache visitor environment state.
     * @param <T>     The type of the cache visitor return value.
     * @return The result of the cache visitor case.
     */
    <E, T> Optional<T> accept(ICacheVisitor<E, T> visitor, E env);

}
