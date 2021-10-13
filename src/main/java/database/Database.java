package database;

import model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/***
 * The database of the PRM model, facilitates persistent storage.
 */
public class Database {

    private final Path baseDirectory = Paths.get(System.getProperty("user.home") + "/.prm/");
    final private IDatabaseLoader loader;
    final private IDatabaseSaver saver;

    Database(IDatabaseLoader loader, IDatabaseSaver storer) {
        this.loader = loader;
        this.saver = storer;
    }

    /***
     * Load a user from the PRM model database.
     * @param name The name of the user.
     * @return The loaded user.
     */
    //TODO: This should probably load the entire PRM model instead.
    public User load(UUID uuid) throws IOException {
        return this.loader.load(this.baseDirectory.resolve(uuid.toString() + ".json"));
    }

    /***
     * Save a user to the PRM model database.
     * @param user The user to save.
     */
    //TODO: This should probably load the entire PRM model instead.
    public void save(User user, UUID uuid) throws IOException {
        this.saver.save(user, this.baseDirectory.resolve(uuid.toString() + ".json"));
    }

    public void remove(UUID uuid) throws IOException {
        Files.delete(baseDirectory.resolve(uuid.toString() + ".json"));
    }

}
