package controller.javafx;

import controller.javafx.components.*;
import javafx.scene.layout.AnchorPane;
import model.Contact;
import model.ITag;

import java.util.List;

/***
 * Factory that creates JavaFX components and returns them as ViewComponents to the caller.
 */
public class ViewComponentFactory {

    /***
     * Creates a top bar for testing purposes.
     * @param nav the navigator object
     * @return the top bar in the form of a ViewComponent
     */
    public static ViewComponent CreateTestTopBar(IPageNavigator nav) {
        return new TestTopBar(nav);
    }

    /***
     * Creates a MainPage component.
     * @return the MainPage in the form of a ViewComponent
     */
    public static ViewComponent CreateMainPage() {
        return new MainPage();
    }

    /***
     * Creates a SecondaryPage component.
     * @return the SecondaryPage in the form of a ViewComponent
     */
    public static ViewComponent CreateSecondaryPage () {
        return new SecondaryPage();
    }

    /**
     * Creates a ContactPage component.
     * @param contacts A list of the contacts to display
     * @return ContactPage as a ViewComponent
     */
    public static ViewComponent CreateContactPage (List<Contact> contacts) {
        return new ContactPage(contacts);
    }

    /**
     * Creates a ContactCard component.
     * @param contact to create card from
     * @return ContactCard as a ViewComponent
     */
    public static ViewComponent CreateContactCard (Contact contact) {
        return new ContactCard(contact);
    }

    public static ViewComponent CreateTagCard (ITag tag) {
        return new TagCard(tag);
    }

}
