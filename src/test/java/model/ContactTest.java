package model;

import org.junit.Before;
import org.junit.Test;


public class ContactTest {

    private Contact c1;

    String testName = "Test Name";

    @Before
    public void setup(){
        c1 = new Contact(testName);
    }


}
