package database;

import model.User;

import java.io.IOException;
import java.nio.file.Path;

/**
 * The loading logic of a database.
 */
public interface IDatabaseLoader {

    /***
     * Load a user from the database.
     * @param databasePath The database file to load a user from.
     * @return The user loaded from the database file.
     * @throws IOException If an IO error occurs while loading the user.
     */
    User load(Path databasePath) throws IOException;

}
