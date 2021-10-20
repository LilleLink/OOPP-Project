package database;

import model.User;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;

public interface IDatabaseTracker {

    void addUser(UUID id, String name, Path databaseTrackerFile) throws IOException;
    boolean removeUser(UUID id, Path databaseTrackerFile) throws IOException;
    Set<UUID> getUsers(Path databaseTrackerFile) throws IOException;
    String getUsername(UUID uuid, Path databaseTrackerFile) throws IOException;

}
