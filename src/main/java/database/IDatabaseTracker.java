package database;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;

/***
 * Defines functionality of concrete database-trackers.
 */
public interface IDatabaseTracker {

    /***
     * Adds a user to the database given the parameters id, name and the path to the file.
     * @param id the UUID of the user
     * @param name the name of the user
     * @param databaseTrackerFile the path to the file where the user is to be saved
     * @throws IOException if the file cannot be accessed.
     */
    void addUser(UUID id, String name, Path databaseTrackerFile) throws IOException;

    /***
     * Removes a user given the parameters id and the path to its file.
     * @param id the UUID of the user.
     * @param databaseTrackerFile the path to the user's file
     * @return boolean representing the result of the operation.
     * @throws IOException if the file cannot be accessed
     */
    boolean removeUser(UUID id, Path databaseTrackerFile) throws IOException;

    /***
     * Returns a set of users UUIDs
     * @param databaseTrackerFile the path to the file to read from
     * @return a Set of users UUIDs
     * @throws IOException if the file cannot be accessed
     */
    Set<UUID> getUsers(Path databaseTrackerFile) throws IOException;

    /***
     * Gets a username given the users UUID and path to its file.
     * @param uuid the UUID of the user
     * @param databaseTrackerFile the file of the user
     * @return a String containing the username.
     * @throws IOException if the file cannot be accessed.
     */
    String getUsername(UUID uuid, Path databaseTrackerFile) throws IOException;

}
