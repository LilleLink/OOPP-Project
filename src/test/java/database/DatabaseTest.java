package database;

import model.Contact;
import model.ITag;
import model.Tag;
import model.User;
import model.exceptions.NameNotAllowedException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class DatabaseTest {

    private final UUID id = UUID.randomUUID();
    private final Database db = DatabaseFactory.getService();
    private final User user = new User("Pelle");

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws IOException {
        db.remove(id);
    }

    @Test
    public void saveLoad() throws NameNotAllowedException, IOException {
        ITag friendTag = user.getTagHandler().createTag("friend");
        ITag biznizTag = user.getTagHandler().createTag("bizniz");

        user.getContacts().addContact("McLovin");
        Contact contact = user.getContacts().getList().stream().filter(c -> c.getName().equals("McLovin"))
                .findFirst().orElseThrow(IllegalStateException::new);
        contact.setAddress("Kungsportsavenyen 32");
        contact.getNotes().addNote("Hahah this guy amirite");
        contact.getNotes().addNote("K he's aight I guess");

        //TODO Fix addTag? Not public.
        //contact.addTag(friendTag);
        //contact.addTag(biznizTag);

        ITag pleasureTag = user.getTagHandler().createTag("pleasure");

        user.getEvents().addEvent("Feeeeest", LocalDateTime.of(2077, 10, 13, 10, 10),
                "Mars", "Cool event on mars",
                Arrays.asList(contact), pleasureTag);

        db.save(user, id);
        assertThat(user).usingRecursiveComparison().isEqualTo(db.load(id));
    }

}