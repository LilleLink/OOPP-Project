package database;

import model.Contact;
import model.ITag;
import model.User;
import model.exceptions.NameNotAllowedException;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseTest {
    private final Database db = DatabaseFactory.getService();
    private final User user = new User("Pelle");
    private final User user2 = new User("Dawg");

    @After
    public void tearDown() throws IOException {
        db.remove(user);
        db.remove(user2);
    }

    @Test
    public void saveLoad() throws NameNotAllowedException, IOException {
        ITag friendTag = user.getTagHandler().createTag("friend");
        ITag biznizTag = user.getTagHandler().createTag("bizniz");

        user.getContacts().addContact("McLovin");
        Contact contact = user.getContacts().getList().stream().filter(c -> c.getName().equals("McLovin"))
                .findFirst().orElseThrow(IllegalStateException::new);
        contact.setAddress("Kungsportsavenyen 32");
        contact.addNote("Hahah this guy amirite");
        contact.addNote("K he's aight I guess");

        user.getContacts().addContact("Bruh");
        Contact contact2 = user.getContacts().getList().stream().filter(c -> c.getName().equals("Bruh"))
                .findFirst().orElseThrow(IllegalStateException::new);
        contact.setAddress("Kungsportsavenyen 32");
        contact.addNote("Wow same address wtf lame");

        contact.addAllTags(Arrays.asList(friendTag, biznizTag));

        ITag pleasureTag = user.getTagHandler().createTag("pleasure");

        user.getEvents().addEvent("Feeeeest", LocalDateTime.of(2077, 10, 13, 10, 10),
                "Mars", "Cool event on mars",
                Arrays.asList(contact), pleasureTag);

        db.save(user2);
        db.save(user);
        assertThat(user).usingRecursiveComparison().isEqualTo(db.load(user.getId()));

        assertThat(db.getUsers().stream().map(id -> {
            try {
                return db.getUsername(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        })).contains("Pelle", "Dawg");
    }

}