package model;

/***
 * The database of the PRM model, facilitates persistent storage.
 */
public interface IPRMDatabase {

    /**
     * Load a user from the PRM model database.
     * @param name The name of the user.
     * @return The loaded user.
     */
    //TODO: This should probably load the entire PRM model instead.
    User load(String name);

    /***
     * Save a user to the PRM model database.
     * @param user
     */
    void save(User user);

}
