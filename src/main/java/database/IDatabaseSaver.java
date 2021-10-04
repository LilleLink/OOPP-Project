package database;

import model.User;

import java.io.IOException;
import java.nio.file.Path;

public interface IDatabaseSaver {

    /***
     * Save a user to the PRM model database.
     * @param user The user to save.
     */
    void save(User user, Path databasePath) throws IOException;

}
