package model;

import java.io.IOException;
import java.nio.file.Files;

public class PRMDatabaseFactory {

    static IPRMDatabase createUserDatabase() {
        JSONPRMDatabase jsonPrmDatabase = null;
        try {
            jsonPrmDatabase = new JSONPRMDatabase(
                    Files.createTempFile("prm-database", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonPrmDatabase;
    }

}
