package model;

import java.io.IOException;
import java.nio.file.Files;

public class UserDatabaseFactory {

    static IUserDatabase createUserDatabase() {
        JSONUserDatabase jsonUserDatabase = null;
        try {
            jsonUserDatabase = new JSONUserDatabase(
                    Files.createTempDirectory("prm-woo" + System.nanoTime()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonUserDatabase;
    }

}
