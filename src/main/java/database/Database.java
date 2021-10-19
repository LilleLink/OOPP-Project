package database;

import model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

/***
 * The database of the PRM model, facilitates persistent storage.
 */
public class Database {

    private final Path baseDirectory = Paths.get(System.getProperty("user.home") + "/.prm/");
    private final Path databaseTrackerPath = baseDirectory.resolve("db");
    final private IDatabaseLoader loader;
    final private IDatabaseSaver saver;
    final private IDatabaseTracker tracker;

    Database(IDatabaseTracker tracker, IDatabaseLoader loader, IDatabaseSaver storer) {
        this.loader = loader;
        this.saver = storer;
        this.tracker = tracker;
    }

    /***
     * Load a user from the PRM model database.
     * @return The loaded user.
     */
    public User load(UUID uuid) throws IOException {
        return this.loader.load(this.baseDirectory.resolve("users/" + uuid.toString()));
    }

    /***
     * Save a user to the PRM model database.
     * @param user The user to save.
     */
    public void save(User user) throws IOException {
        this.tracker.addUser(user.getId(), user.getName(), this.databaseTrackerPath);
        this.saver.save(user, this.baseDirectory.resolve("users/" + user.getId().toString()));
    }

    public void remove(User user) throws IOException {
        if(this.tracker.removeUser(user.getId(), this.databaseTrackerPath)) {
            Files.delete(baseDirectory.resolve("users/" + user.getId().toString()));
        }
    }

    public Set<UUID> getUsers() throws IOException {
        return this.tracker.getUsers(this.databaseTrackerPath);
    }

    public String getUsername(UUID uuid) throws IOException {
        return this.tracker.getUsername(uuid, this.databaseTrackerPath);
    }

}
