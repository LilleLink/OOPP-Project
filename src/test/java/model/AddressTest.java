package model;

import model.Address;
import org.junit.Test;
import static org.junit.Assert.*;

public class AddressTest {
    @Test
    public void TestCleanString(){
        Address address = new Address("   Hubben     2.1  ");
        assertEquals("Hubben 2.1", address.getAddress());
    }
}
