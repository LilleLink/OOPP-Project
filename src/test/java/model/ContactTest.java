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

    @Test
    public void createTest(){
        c1 = new Contact(testName);
        assert testName.equals(c1.getName());
    }

    @Test
    public void changeNameTest(){
        String newName = "new name";
        Contact c2 = c1.setName(newName);
        assert !(c1.getName().equals(newName));
        assert !(c1.equals(c2));
    }

    @Test
    public void changePhoneNumber(){
        String newNumber = "new name";
        Contact c2 = c1.setPhoneNumber(newNumber);
        assert !(c1.getPhoneNumber().equals(newNumber));
        assert !(c1.equals(c2));
    }
}
