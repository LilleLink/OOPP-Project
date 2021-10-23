package controller.javafx;

import controller.javafx.components.PageFactory;
import controller.javafx.components.ViewComponent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

/***
 * The root-window of the JavaFX view environment.
 * Is used to navigate within the view through the implementation of the IPageNavigator class and the TopBar component.
 * The parent of all "Page" components created through the ViewComponentFactory.
 */
public class RootWindow implements IPageNavigator, Initializable {

    private User user;

    @FXML
    private AnchorPane topBarAnchorPane;
    @FXML
    private AnchorPane pageAnchorPane;

    private ViewComponent statisticsPage;
    private ViewComponent testTopBar;
    private ViewComponent contactPage;
    private ViewComponent calendarPage;
    private ViewComponent notificationsPage;

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
        openContactPage();
    }

    private void initiatePages() {
        testTopBar = PageFactory.createTopBar(this);
        statisticsPage = PageFactory.createStatisticsPage(user.getEvents(), user.getTagHandler());
        calendarPage = PageFactory.createCalendarPage(user.getEvents(), user.getContacts(), user.getTagHandler());
        contactPage = PageFactory.createContactPage(user.getContacts(), user.getTagHandler(), user.getEvents());
        notificationsPage = PageFactory.createNotificationsPage(user.getEvents());
    }

    @Override
    public void openStatisticsPage() {
        clearRootPage();
        pageAnchorPane.getChildren().add(statisticsPage.getPane());
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
    public void openNotificationPage() {
        clearRootPage();
        pageAnchorPane.getChildren().add(notificationsPage.getPane());
    }

    @Override
    public void clearRootPage() {
        pageAnchorPane.getChildren().clear();
    }


}
