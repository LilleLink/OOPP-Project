package fileHandler;

import java.io.File;
import java.util.List;
import java.util.UUID;

public interface IFileHandlerFacade {
    void saveFile(UUID id, File sourceFile);
    void saveFile(UUID id, File sourceFile, File directory);
    List<File> getFiles(UUID id);
    List<File> getFiles(UUID id, File directory);
    void removeDirectory(UUID id);
}
