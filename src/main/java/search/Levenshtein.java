package search;

/**
 * Calculates the Levenshtein distance of two sequences of characters.
 * Based on the Wagner-Fischer dynamic programming algorithm calculating Damerau-Levenshtein distance.
 * @see <a href="https://en.wikipedia.org/wiki/Damerau%E2%80%93Levenshtein_distance">
 *     The Damerua-Levenshtein Wikipedia article</a>
 * @author Simon Johnsson
 */
class Levenshtein {

    /**
     * Gives the <b>Optimal string alignment distance</b> for two sequences of characters.
     * Determining operations include:
     * <ul>
     *     <li><b>Deletions</b></li>
     *     <li><b>Insertions</b></li>
     *     <li><b>Substitutions</b></li>
     *     <li><b>Transpositions</b></li>
     * </ul>
     * @param a the source sequence
     * @param b the target sequence
     * @return the edit distance between the two sequences
     */
    public static int distance(CharSequence a, CharSequence b) {
        //matrix containing operations
        int[][] d = new int[a.length() + 1][b.length() + 1];


        for (int i = 0; i <= a.length(); i++) {
            d[i][0] = i;
        }
        for (int j = 0; j <= b.length(); j++) {
            d[0][j] = j;
        }

        for (int i = 1; i <= a.length(); i++) {
            int cost;
            for (int j = 1; j <= b.length(); j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    cost = 0;
                } else {
                    cost = 1;
                }
                d[i][j] = Math.min(d[i - 1][j] + 1, //deletion
                        Math.min(d[i][j - 1] + 1, //insertion
                                d[i - 1][j - 1] + cost));// substitution
                if (i > 1 && j > 1 && a.charAt(i - 1) == b.charAt(j - 2) && a.charAt(i - 2) == b.charAt(j - 1)) {
                    d[i][j] = Math.min(d[i][j], d[i - 2][j - 2] + 1); // transposition
                }
            }
        }
        return d[a.length()][b.length()];
    }
}
