package database;

import model.User;

import java.io.IOException;
import java.nio.file.Path;

/***
 * The database of the PRM model, facilitates persistent storage.
 */
public class Database {

    final private IDatabaseLoader loader;
    final private IDatabaseSaver saver;
    final private Path databasePath;

    Database(Path databasePath, IDatabaseLoader loader, IDatabaseSaver storer) {
        this.loader = loader;
        this.saver = storer;
        this.databasePath = databasePath;
    }

    /***
     * Load a user from the PRM model database.
     * @param name The name of the user.
     * @return The loaded user.
     */
    //TODO: This should probably load the entire PRM model instead.
    public User load(String name) throws IOException {
        return this.loader.load(name, this.databasePath);
    }

    /***
     * Save a user to the PRM model database.
     * @param user The user to save.
     */
    //TODO: This should probably load the entire PRM model instead.
    public void save(User user) throws IOException {
        this.saver.save(user, this.databasePath);
    }

}
