package mocks;

import model.search.ISearchable;

/**
 * Testing class to contain a searchable string identity.
 */
public class MockStringSearchable implements ISearchable<String> {
    private final String searchIdentity;

    public MockStringSearchable(String searchIdentity) {
        this.searchIdentity = searchIdentity;
    }

    @Override
    public String getSearchIdentity() {
        return searchIdentity;
    }
}
