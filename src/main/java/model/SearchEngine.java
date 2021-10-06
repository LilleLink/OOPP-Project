package model;

import jdk.internal.org.jline.utils.Levenshtein;

import java.util.ArrayList;
import java.util.List;

/**
 * A tool for searching a given list of a searchable type using string inputs.
 * Relevancy is determined using the {@link Levenshtein} distance method.
 * @param <T> the type to search through which must implement ISearchable of type String
 * @author Simon Johnsson
 */
public class SearchEngine<T extends ISearchable<String> >{

    /**
     * The collection this search engine iterates through.
     */
    private final List<T> searchBase;

    /**
     * The Levenshtein tolerance integer.
     * A result with a value above this is not considered relevant enough.
     */
    private final int levTolerance;

    /**
     * Returns a search engine based on the given content and the given tolerance.
     * @param searchBase the content to base the search upon
     * @param levTolerance the tolerance of the output results
     */
    public SearchEngine(List<T> searchBase, int levTolerance) {
        this.searchBase = searchBase;
        this.levTolerance = levTolerance;
    }

    /**
     * Iterates through the search base and returns a list containing every object considered relevant to the query.
     * <p>
     *     Relevancy is measured by comparing the {@link Levenshtein} distance to the {@code levTolerance} integer.
     * </p>
     * @param query the string to compare the searchbase to
     * @return a list containing results considered relevant to the query
     */
    public List<T> search(String query) {
        List<T> results = new ArrayList<>();
        for(T elem : searchBase) {
            if(levTolerance <= getRelevancy(query,elem.getSearchIdentity())) {
                results.add(elem);
            }
        }
        return results;
    }



    private int getRelevancy(String s1, String s2) {
        return Levenshtein.distance(s1,s2,2,1,5,5);
    }
}
