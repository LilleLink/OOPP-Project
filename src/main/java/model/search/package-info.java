/**
 * The main API for searching by relevancy.
 * <p>
 * The classes defined here represent aspects and attributes related to performing searches where more than one result
 * is relevant in a {@link java.util.List}.
 *
 *
 * <header>
 * <h1>Search Engine:</h1>
 * <p>{@link model.search.ISearchable} describes a searchable type.</p>
 * <p>{@link model.search.SearchEngine} performs string-based searches.</p>
 * <p>{@link model.search.LevenshteinUtils} measures the edit distance between two character sequences.</p>
 * </header>
 *
 * <header>
 * <h1>Observer</h1>
 * <p>{@link model.search.ISearchObservable} describes a type returning client-interesting results.</p>
 * <p>{@link model.search.ISearchObserver} describes a type interested in knowing search results.</p>
 * </header>
 *
 * @author Simon Johnsson
 */
package model.search;

