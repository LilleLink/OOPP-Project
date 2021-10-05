package model;

import jdk.internal.org.jline.utils.Levenshtein;

import java.util.List;

/**
 * A tool for searching a given list of a searchable type using string inputs.
 * Relevancy is determined using the {@link Levenshtein} distance method.
 * @param <T> the type to search through
 */
public class SearchEngine<T extends ISearchable<String> >{

    private final List<T> searchBase;


    public SearchEngine(List<T> searchBase) {
        this.searchBase = searchBase;
    }

    public List<T> search(String query) {
        return searchBase;
    }



    private int getRelevancy(String s1, String s2) {
        return Levenshtein.distance(s1,s2);
    }
}
