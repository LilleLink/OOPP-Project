package search;


/**
 * An object capable of performing search related operations of which external clients are interested of.
 * It is strongly advised that the implementer can generate a form of operational result.
 */
public interface ISearchObservable {

    /**
     * Subscribes the given observer to the operations of this object.
     *
     * @param observer the client interested in search operations
     */
    void subscribe(ISearchObserver observer);

    /**
     * Unsubscribes the given observer to the operations of this object.
     *
     * @param observer the client to unsubscribe
     */
    void unsubscribe(ISearchObserver observer);

    /**
     * Alerts subscribers to the results of a performed operation.
     */
    void notifyResult();
}
