package database;

import model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

/***
 * The PRM model database with abstract logic.
 */
public class Database {

    // The app data directory.
    private final Path baseDirectory = Paths.get(System.getProperty("user.home") + "/.prm/");
    // The database tracker file.
    private final Path databaseTrackerPath = baseDirectory.resolve("db");
    // The database loader logic.
    final private IDatabaseLoader loader;
    // The database saver logic
    final private IDatabaseSaver saver;
    // The database tracker logic.
    final private IDatabaseTracker tracker;

    /**
     * Construct a database with tracker, loader and saver logic.
     * @param tracker The database tracker logic.
     * @param loader The database loader logic.
     * @param storer The database saver logic.
     */
    Database(IDatabaseTracker tracker, IDatabaseLoader loader, IDatabaseSaver storer) {
        this.loader = loader;
        this.saver = storer;
        this.tracker = tracker;
    }

    /***
     * Load a user from the database.
     * @param uuid The UID of the user to load.
     * @return The loaded user.
     */
    public User load(UUID uuid) throws IOException {
        return this.loader.load(this.baseDirectory.resolve("users/" + uuid.toString()));
    }

    /***
     * Save a user to the database.
     * @param user The user to save.
     */
    public void save(User user) throws IOException {
        this.tracker.addUser(user.getId(), user.getName(), this.databaseTrackerPath);
        this.saver.save(user, this.baseDirectory.resolve("users/" + user.getId().toString()));
    }

    /**
     * Remove a user from the database.
     * @param user The user to remove from the database.
     * @throws IOException If an IO error occurs.
     */
    public void remove(User user) throws IOException {
        if(this.tracker.removeUser(user.getId(), this.databaseTrackerPath)) {
            Files.delete(baseDirectory.resolve("users/" + user.getId().toString()));
        }
    }

    /**
     * Get all the user UIDs from the database.
     * @return A set of all user UIDs in the database.
     * @throws IOException If an IO error occurs.
     */
    public Set<UUID> getUsers() throws IOException {
        return this.tracker.getUsers(this.databaseTrackerPath);
    }

    /**
     * Get a username from a UID in the database.
     * @param uuid The UUID of the user to get a name from.
     * @return The name of the user.
     * @throws IOException If an IO error occurs.
     */
    public String getUsername(UUID uuid) throws IOException {
        return this.tracker.getUsername(uuid, this.databaseTrackerPath);
    }

}
