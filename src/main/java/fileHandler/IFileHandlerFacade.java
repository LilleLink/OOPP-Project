package fileHandler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public interface IFileHandlerFacade {
    void saveFile(UUID id, Path sourceFile) throws IOException;
    void saveFile(UUID id, Path sourceFile, Path directory) throws IOException;
    List<Path> getFiles(UUID id);
    List<Path> getFiles(UUID id, Path directory);
    void removeAllFiles(UUID id);
    void removePath(UUID id, Path directory);
}
