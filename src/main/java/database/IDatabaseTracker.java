package database;

import model.User;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;

/**
 * The tracker logic of the database. Tracks which users exist in the database and their name.
 */
public interface IDatabaseTracker {

    /**
     * Add a user to the database tracker file.
     * @param id The uid of the user.
     * @param name The name of the user.
     * @param databaseTrackerFile The tracker file to add the user to.
     * @throws IOException If an IO error occurs while writing to the file.
     */
    void addUser(UUID id, String name, Path databaseTrackerFile) throws IOException;

    /**
     * Remove a user from the database tracker file.
     * @param id The uid of the user.
     * @param databaseTrackerFile The tracker file to remove the user from.
     * @return True if the user existed, false otherwise.
     * @throws IOException If an IO error occurs while writing to the file.
     */
    boolean removeUser(UUID id, Path databaseTrackerFile) throws IOException;

    /**
     * Get all the user ids from the database tracker file.
     * @param databaseTrackerFile The database tracker file to read users from.
     * @return A set of all UIDs in the tracker file.
     * @throws IOException If an IO error occurs while reading from the file.
     */
    Set<UUID> getUsers(Path databaseTrackerFile) throws IOException;

    /**
     * Get the username of a user in the database.
     * @param uuid The UID of the user to query a name from.
     * @param databaseTrackerFile The database tracker file to read the username from.
     * @return The username.
     * @throws IOException If an IO error occurs while reading from the file.
     */
    String getUsername(UUID uuid, Path databaseTrackerFile) throws IOException;

}
