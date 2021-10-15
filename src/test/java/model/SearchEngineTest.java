package model;

import mocks.MockStringSearchable;
import org.junit.Before;
import org.junit.Test;
import model.search.SearchEngine;

import java.util.ArrayList;
import java.util.List;

public class SearchEngineTest {

    private List<MockStringSearchable> base;

    @Before
    public void instantiateList() {
        base = new ArrayList<>();
    }

    @Test
    public void strictAccuracyTest() {
        MockStringSearchable s1 = new MockStringSearchable("Pelle");
        MockStringSearchable s2 = new MockStringSearchable( "Pulle");
        MockStringSearchable s3 = new MockStringSearchable("Palle");
        base.add(s1);
        base.add(s2);
        base.add(s3);
        SearchEngine<MockStringSearchable> searchEngine = new SearchEngine<>(base);
        List<MockStringSearchable> results = searchEngine.search("Pelle",0);
        assert(results.contains(s1) && results.size() == 1);
    }

    @Test
    public void oneCharacterToleranceTest() {
        MockStringSearchable s1 = new MockStringSearchable("Pelle");
        MockStringSearchable s2 = new MockStringSearchable( "Pele");
        MockStringSearchable s3 = new MockStringSearchable("Plele");
        base.add(s1);
        base.add(s2);
        base.add(s3);
        SearchEngine<MockStringSearchable> searchEngine = new SearchEngine<>(base);
        List<MockStringSearchable> results = searchEngine.search("Pelle",1);
        assert(results.contains(s1) && results.contains(s2) && results.contains(s3) && results.size() == 3);
    }

    @Test
    public void looseToleranceTest() {
        MockStringSearchable s1 = new MockStringSearchable("Kjelle");
        MockStringSearchable s2 = new MockStringSearchable("Peter");
        MockStringSearchable s3 = new MockStringSearchable("Kalle");
        MockStringSearchable s4 = new MockStringSearchable("Kerstin");
        base.add(s1);
        base.add(s2);
        base.add(s3);
        base.add(s4);
        SearchEngine<MockStringSearchable> searchEngine = new SearchEngine<>(base);
        List<MockStringSearchable> results = searchEngine.search("pelle",3);
        assert(results.contains(s1) && results.contains(s2) &&
                results.contains(s3) && !results.contains(s4));
    }

}
