package database.json;

import com.google.gson.Gson;
import database.IDatabaseTracker;
import model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.UUID;

public class JSONDatabaseTracker implements IDatabaseTracker {

    private JSONRecords.TrackerRecord record = new JSONRecords.TrackerRecord();

    private void save(Path databaseTrackerFile) throws IOException {
        if(!Files.exists(databaseTrackerFile)) {
            Files.createDirectories(databaseTrackerFile.getParent());
            Files.createFile(databaseTrackerFile);
        }

        Files.write(databaseTrackerFile, new Gson().toJson(record).getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    private void load(Path databaseTrackerFile) throws IOException {
        if(Files.exists(databaseTrackerFile)) {
            record = new Gson().fromJson(String.join("\n", Files.readAllLines(databaseTrackerFile)), JSONRecords.TrackerRecord.class);
        }else {
            record = new JSONRecords.TrackerRecord();
        }
    }

    @Override
    public void addUser(UUID id, String name, Path databaseTrackerFile) throws IOException {
        record.users.put(id, name);

        save(databaseTrackerFile);
    }

    @Override
    public boolean removeUser(UUID id, Path databaseTrackerFile) throws IOException {
        if(record.users.containsKey(id)) {
            record.users.remove(id);

            save(databaseTrackerFile);

            return true;
        }
        return false;
    }

    @Override
    public Set<UUID> getUsers(Path databaseTrackerFile) throws IOException {
        load(databaseTrackerFile);

        return record.users.keySet();
    }

    @Override
    public String getUsername(UUID uuid, Path databaseTrackerFile) throws IOException {
        load(databaseTrackerFile);

        return record.users.get(uuid);
    }

}
