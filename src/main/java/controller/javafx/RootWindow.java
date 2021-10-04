package controller.javafx;

import controller.javafx.components.ViewComponent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import model.Contact;
import model.TagHandler;
import model.User;
import model.exceptions.NameNotAvailableException;

import java.net.URL;
import java.util.ResourceBundle;

public class RootWindow implements IPageNavigator, Initializable {

    private User user;

    @FXML private AnchorPane topBarAnchorPane;
    @FXML private AnchorPane pageAnchorPane;

    private ViewComponent mainPage;
    private ViewComponent secondaryPage;
    private ViewComponent testTopBar;
    private ViewComponent contactPage;
    private ViewComponent calendarPage;

    /***
     * The controller class for the root-window of the javafx view.
     * @param user the instantiated user object
     */
    public RootWindow(User user) {
        this.user = user;
    }

    /***
     * Initiates the components on the root window
     * @param url argument given from javafx
     * @param resourceBundle argument given from javafx
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initiatePages();
        topBarAnchorPane.getChildren().add(testTopBar.getPane());
        openMainPage();
    }

    private void initiatePages() {
        testTopBar = ViewComponentFactory.CreateTestTopBar(this);
        mainPage = ViewComponentFactory.CreateMainPage();
        secondaryPage = ViewComponentFactory.CreateSecondaryPage();
        calendarPage = ViewComponentFactory.CreateCalendarPage(user.getEvents());
        contactPage = ViewComponentFactory.CreateContactPage(user.getContacts());
    }

    @Override
    public void openMainPage() {
        clearRootPage();
        pageAnchorPane.getChildren().add(mainPage.getPane());
    }

    @Override
    public void openSecondaryPage() {
        clearRootPage();
        pageAnchorPane.getChildren().add(secondaryPage.getPane());
    }

    @Override
    public void openContactPage() {
        clearRootPage();
        pageAnchorPane.getChildren().add(contactPage.getPane());
    }

    @Override
    public void openCalendarPage() {
        clearRootPage();
        pageAnchorPane.getChildren().add(calendarPage.getPane());
    }

    @Override
    public void clearRootPage() {
        pageAnchorPane.getChildren().clear();
    }


}
