package database;

import database.json.JSONDatabaseLoader;
import database.json.JSONDatabaseSaver;
import database.json.JSONDatabaseTracker;

import java.io.IOException;

/***
 * The DatabaseFactory allows getting of a Database with abstract logic.
 */
public final class DatabaseFactory {

    // The database instance.
    static private Database database = null;

    private DatabaseFactory() {
    }

    /***
     * Create a new abstract PRM database.
     * @throws IOException If the factory failed to initialize the database disk storage.
     * @return The new PRM database.
     */
    static synchronized public Database getService() {
        if (database == null) {
            /// TODO Don't use a temporary file dummy!
            database = new Database(new JSONDatabaseTracker(), new JSONDatabaseLoader(), new JSONDatabaseSaver());
        }
        return database;
    }

}
