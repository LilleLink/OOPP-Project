package application;

import controller.javafx.JavaFXViewInitializer;
import controller.javafx.UserWizard;
import database.DatabaseFactory;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

/***
 * The main class of the application containing the entry point and instantiation
 */
public class Application extends javafx.application.Application {

    private User user = null;

    public static void main(String[] args) {
        launch(args);
    }

    /***
     * Starts the JavaFX application
     * @param stage the stage of the GUI
     * @throws IOException if the necessary files cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Instantiates model
        UserWizard wizard = JavaFXViewInitializer.createUserWizard(stage);

        // Initializes HostServiceProvider
        HostServicesProvider.init(getHostServices());

        //Instantiates javafx controller/view
        wizard.addListener(u -> {
            try {
                this.user = u;
                JavaFXViewInitializer.initializeJavaFXView(stage, u);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /***
     * Stops the application and saves the loaded user to its file.
     * @throws IOException if saving of the user is unsuccessful.
     */
    @Override
    public void stop() throws IOException {
        if (user != null) {
            DatabaseFactory.getService().save(user);
        }
    }
}
