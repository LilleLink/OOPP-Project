package database;

import database.json.JSONDatabaseLoader;
import database.json.JSONDatabaseSaver;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

/***
 * The DatabaseFactory contains a static method for creating an abstract PRM database.
 */
public class DatabaseFactory {

    static private Database database = null;

    /***
     * Create a new abstract PRM database.
     * @throws IOException If the factory failed to initialize the database disk storage.
     * @return The new PRM database.
     */
    static public Database getService() {
        if (database == null) {
            /// TODO Don't use a temporary file dummy!
                database = new Database(new JSONDatabaseLoader(), new JSONDatabaseSaver());
        }
        return database;
    }

}
