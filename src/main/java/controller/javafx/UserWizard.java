package controller.javafx;

import database.DatabaseFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class UserWizard implements Initializable {

    @FXML
    private VBox userButtons;

    @FXML
    private TextField userNameField;

    private Set<UUID> users;
    private Map<UUID, String> userNames = new HashMap<>();

    public interface UserWizardListener {
        void onUser(User user);
    }

    private List<UserWizardListener> userWizardListeners = new ArrayList<>();

    public UserWizard() throws IOException {
        this.users = DatabaseFactory.getService().getUsers();
        for (UUID uuid : this.users) {
            userNames.put(uuid, DatabaseFactory.getService().getUsername(uuid));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (UUID user : this.users) {
            Button button = new Button(this.userNames.get(user));

            button.setOnAction(a -> {
                try {
                    User userModel = DatabaseFactory.getService().load(user);
                    userWizardListeners.forEach(l -> l.onUser(userModel));
                } catch (IOException e) {
                    // Corrupt database
                    e.printStackTrace();
                }
            });

            userButtons.getChildren().add(button);
        }
    }

    @FXML
    private void newUser(ActionEvent event) {
        User user = new User(userNameField.getText());
        userWizardListeners.forEach(l -> l.onUser(user));
    }

    public void addListener(UserWizardListener listener) {
        this.userWizardListeners.add(listener);
    }
}
