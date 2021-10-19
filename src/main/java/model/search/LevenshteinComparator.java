package model.search;


import java.util.Comparator;

/**
 * A comparator comparing the search identities of two objects with searchable type string
 * to the given source query. The search identity with a lower {@link Levenshtein} distance is
 * sorted first.
 *
 * @param <T>
 * @author Simon Johnsson
 */
class LevenshteinComparator<T extends ISearchable<String>> implements Comparator<T> {

    private final CharSequence source;

    /**
     * Constructs a comparator comparing to the given char sequence.
     *
     * @param source the char sequence to compare to
     */
    LevenshteinComparator(CharSequence source) {
        this.source = source;
    }

    @Override
    public int compare(T t1, T t2) {
        return Levenshtein.distance(source,t1.getSearchIdentity()) - Levenshtein.distance(source,t2.getSearchIdentity());
    }
}
