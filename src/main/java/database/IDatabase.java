package database;

import model.User;

import java.io.IOException;

/***
 * The database of the PRM model, facilitates persistent storage.
 */
public interface IDatabase {

    /***
     * Load a user from the PRM model database.
     * @param name The name of the user.
     * @return The loaded user.
     */
    //TODO: This should probably load the entire PRM model instead.
    User load(String name) throws IOException;

    /***
     * Save a user to the PRM model database.
     * @param user The user to save.
     */
    //TODO: This should probably load the entire PRM model instead.
    void save(User user) throws IOException;

}
