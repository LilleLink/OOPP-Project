package database;

import model.User;

import java.io.IOException;
import java.nio.file.Path;

/**
 * The saving logic of a database.
 */
public interface IDatabaseSaver {

    /**
     * Save a user to the database.
     * @param user The user to save to the database.
     * @param databasePath The database file to save the user to.
     * @throws IOException If an IO error occurs while saving the user.
     */
    void save(User user, Path databasePath) throws IOException;

}
