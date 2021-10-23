package database;

import model.User;

import java.io.IOException;
import java.nio.file.Path;

/***
 * Defines functionality of concrete database-loaders.
 */
public interface IDatabaseLoader {

    /***
     * Load a user from the PRM model database.
     * @param databasePath The path to the users file.
     * @return The loaded user.
     */
    User load(Path databasePath) throws IOException;

}
