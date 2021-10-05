package fileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

class FileHandler implements IFileHandlerFacade {
    private Path baseDirectory = Paths.get(System.getProperty("user.home") + "/.prm/");

    FileHandler(){

    }

    @Override
    public void saveFile(UUID id, Path sourceFile) throws IOException {
        Files.createDirectories(baseDirectory.resolve(id.toString()));
        Files.copy(sourceFile, baseDirectory.resolve(id.toString()).resolve(sourceFile.getFileName()), REPLACE_EXISTING);
        System.out.println(sourceFile);
        System.out.println(baseDirectory.resolve(id.toString()).resolve(sourceFile.getFileName()));

    }

    @Override
    public void saveFile(UUID id, Path sourceFile, Path directory) throws IOException {
        Files.copy(sourceFile, baseDirectory.resolve(id.toString()).resolve(directory));
    }

    @Override
    public List<Path> getFiles(UUID id) {
        //Files.walk(baseDirectory.resolve(id));
        System.out.println(baseDirectory.resolve(id.toString()));
        return null;
    }

    @Override
    public List<Path> getFiles(UUID id, Path directory) {
        return null;
    }

    @Override
    public void removePath(UUID id, Path directory){

    }

    @Override
    public void removeAllFiles(UUID id){

    }

}
