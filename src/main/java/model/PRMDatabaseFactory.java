package model;

import java.io.IOException;
import java.nio.file.Files;

/***
 * The PRMDatabaseFactory contains a static method for creating an abstract PRM database.
 */
public class PRMDatabaseFactory {

    /***
     * Create a new abstract PRM database.
     * @throws IOException If the factory failed to initialize the database disk storage.
     * @return The new PRM database.
     */
    static IPRMDatabase createPRMDatabase() throws IOException {
        /// TODO Don't use a temporary file dummy!
        return new JSONPRMDatabase(
                Files.createTempFile("prm-database", ""));
    }

}
