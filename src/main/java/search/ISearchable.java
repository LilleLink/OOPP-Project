package search;

/**
 * An object containing searchable information. The user of this interface determines which information defines its
 * identity when being compared to search queries.
 */
public interface ISearchable<T> {

    /**
     * Gives the user's identity in the search algorithm depending on the given type.
     * A search-algorithmically defined identity may be for example: an integer, a string or a list.
     * @return the identity defining this user
     */
    T getSearchIdentity();
}
