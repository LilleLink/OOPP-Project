package vcf;

import model.Contact;
import model.ContactList;
import model.TagHandler;
import model.User;
import model.exceptions.NameNotAllowedException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

public class VCFParserTest {
    private static IVCFParser parser;
    private static final TagHandler tagHandler = new TagHandler();
    private static final ContactList contactList = new User("NoName").getContacts();
    private static final Path testFileDirectory = Paths.get("src/test/java/vcf/testFiles/");

    @BeforeClass
    public static void preTest() {
        parser = VCFParserFactory.getService(contactList, tagHandler);
    }

    @Test
    public void readFiles() {
        Path path = Paths.get(testFileDirectory + "/multipleFiles/");
        try {
            parser.addContactsFromDirectory(path);
        } catch (IOException e) {
            fail();
        }
        List<Contact> contacts = contactList.getList();
        Contact c1 = contacts.get(0);
        assert (testContact1(c1));
        Contact c2 = contacts.get(1);
        assert (testContact2(c2));
    }

    private boolean testContact1(Contact c1) {
        if (!c1.getName().equals("PreFIX NAME MIDDLE_NAME SURNAME Suffix")) return false;
        if (!c1.getPhoneNumber().equals("1234567890")) return false;
        if (!c1.getTags().get(0).getName().equals("Tag1")) return false;
        if (!c1.getNote(0).toString().equals("Contact note")) return false;
        return c1.getAddress().equals("Teknologstigen 3, Chalmers");
    }

    private boolean testContact2(Contact c2) {
        if (!c2.getName().equals("This is Full Name")) return false;
        if (!c2.getPhoneNumber().equals("0734111337")) return false;
        if (!c2.getTags().get(0).getName().equals("Tag1") || !c2.getTags().get(1).getName().equals("Tag2"))
            return false;
        if (!c2.getNote(0).toString().equals("Contact 2 note")) return false;
        return c2.getAddress().equals("");
    }

    @Test
    public void illegalNameTest() {
        Path path = Paths.get(testFileDirectory + "/illegalName.vcf");
        assertThrows(NameNotAllowedException.class, () -> parser.addContact(path));
    }

    @Test
    public void IllegalFileTypeTest() {
        Path path = Paths.get(testFileDirectory + "/illegalFile.txt");
        assertThrows(IOException.class, () -> parser.addContact(path));
    }
}
