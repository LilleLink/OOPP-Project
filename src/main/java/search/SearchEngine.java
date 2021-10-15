package search;

import java.util.ArrayList;
import java.util.List;

/**
 * A tool for searching a given list of a searchable type using string inputs.
 * Relevancy is determined using the {@link Levenshtein} distance method.
 * <p>
 * <b>NOTE:</b> Accuracy is dependant on the difference in length between the intended queries
 * and the search identities of the given searchable objects.
 * </p>
 *
 * @param <T> the type to search through which must implement ISearchable of type String
 * @author Simon Johnsson
 */
public class SearchEngine<T extends ISearchable<String>> {

    /**
     * The collection this search engine iterates through.
     */
    private final List<T> searchBase;

    /**
     * Returns a search engine based on the given content and the given tolerance.
     *
     * @param searchBase the content to base the search upon
     */
    public SearchEngine(List<T> searchBase) {
        this.searchBase = searchBase;
    }

    /**
     * Iterates through the search base and returns a list containing every object considered relevant to the query.
     * <p>
     * Relevancy is measured by comparing the {@link Levenshtein} distance to the {@code tol} integer,
     * where the integer is the maximum tolerated distance between the query and the target search base.
     * </p>
     * The comparison is case-insensitive and will perform calculations based on lower case versions of the inputs.
     *
     * @param query the string to compare the search base to
     * @param tol   the tolerance of the output results
     * @return a list containing results considered relevant to the query
     */
    //TODO Increase relevancy complexity
    public List<T> search(String query, int tol) {
        List<T> results = new ArrayList<>();
        for (T elem : searchBase) {
            if (tol >= Levenshtein.distance(query.toLowerCase(), elem.getSearchIdentity().toLowerCase())) {
                results.add(elem);
            }
        }
        return results;
    }

    /**
     * Returns the iterated search base.
     *
     * @return a list of the search base
     */
    public List<T> getSearchBase() {
        return searchBase;
    }
}
