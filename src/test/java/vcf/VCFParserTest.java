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
        int indexOfC1 = contacts.get(0).getTags().size() == 1 ? 0 : 1;
        Contact c1 = contacts.get(indexOfC1);
        assert testContact1(c1);
        Contact c2 = contacts.get(contacts.size() - 1 - indexOfC1);
        assert testContact2(c2);
    }

    private boolean testContact1(Contact c1) {
        if (!"PreFIX NAME MIDDLE_NAME SURNAME Suffix".equals(c1.getName())) {
            return false;
        }
        if (!"1234567890".equals(c1.getPhoneNumber())) {
            return false;
        }
        if (!"Tag1".equals(c1.getTags().get(0).getName())) {
            return false;
        }
        if (!"Contact note".equals(c1.getNote(0).toString())) {
            return false;
        }
        return "Teknologstigen 3, Chalmers".equals(c1.getAddress());
    }

    private boolean testContact2(Contact c2) {
        if (!"This is Full Name".equals(c2.getName())) {
            return false;
        }
        if (!"0734111337".equals(c2.getPhoneNumber())) {
            return false;
        }
        if (!"Tag1".equals(c2.getTags().get(0).getName()) || !"Tag2".equals(c2.getTags().get(1).getName())) {
            return false;
        }
        if (!"Contact 2 note".equals(c2.getNote(0).toString())) {
            return false;
        }
        return "".equals(c2.getAddress());
    }

    @Test
    public void illegalNameTest() {
        Path path = Paths.get(testFileDirectory + "/illegalName.vcf");
        assertThrows(NameNotAllowedException.class, () -> parser.addContact(path));
    }

    @Test
    public void illegalFileTypeTest() {
        Path path = Paths.get(testFileDirectory + "/illegalFile.txt");
        assertThrows(IOException.class, () -> parser.addContact(path));
    }
}
