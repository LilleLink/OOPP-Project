package database;

import java.io.IOException;
import java.nio.file.Files;

/***
 * The PRMDatabaseFactory contains a static method for creating an abstract PRM database.
 */
public class DatabaseFactory {

    /***
     * Create a new abstract PRM database.
     * @throws IOException If the factory failed to initialize the database disk storage.
     * @return The new PRM database.
     */
    static public Database getDatabase() throws IOException {
        /// TODO Don't use a temporary file dummy!
        return new Database(Files.createTempFile("prm-database", ""), new JSONDatabase(), new JSONDatabase());
    }

}
