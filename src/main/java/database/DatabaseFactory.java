package database;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

/***
 * The PRMDatabaseFactory contains a static method for creating an abstract PRM database.
 */
public class DatabaseFactory {

    static private Database database = null;

    /***
     * Create a new abstract PRM database.
     * @throws IOException If the factory failed to initialize the database disk storage.
     * @return The new PRM database.
     */
    static public Database getDatabase() throws IOException {
        if (database == null) {
            /// TODO Don't use a temporary file dummy!
            database = new Database(Files.createTempFile("prm-database", ""), new JSONDatabase(), new JSONDatabase());
        }
        return database;
    }

}
