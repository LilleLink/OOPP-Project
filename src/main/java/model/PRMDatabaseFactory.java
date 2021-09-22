package model;

import java.io.IOException;
import java.nio.file.Files;

public class PRMDatabaseFactory {

    /**
     * Create a new PRM database.
     * @return The new PRM database.
     */
    static IPRMDatabase createUserDatabase() {
        JSONPRMDatabase jsonPrmDatabase = null;
        //TODO Remove try-catch
        try {
            jsonPrmDatabase = new JSONPRMDatabase(
                    Files.createTempFile("prm-test", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonPrmDatabase;
    }

}
