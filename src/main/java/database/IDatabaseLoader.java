package database;

import model.User;

import java.io.IOException;
import java.nio.file.Path;

public interface IDatabaseLoader {

    /***
     * Load a user from the PRM model database.
     * @param name The name of the user.
     * @return The loaded user.
     */
    User load(String name, Path databasePath) throws IOException;

}
