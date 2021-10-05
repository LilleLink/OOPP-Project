package fileHandler;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileHandler implements IFileHandlerFacade {
    private File baseDirectory = new File(System.getProperty("user.home") + "/.prm/");

    FileHandler(){

    }

    @Override
    public void saveFile(UUID id, File sourceFile) {
        //Files.copy(sourceFile, baseDirectory, REPLACE_EXISTING);
    }

    @Override
    public void saveFile(UUID id, File sourceFile, File directory) {

    }

    @Override
    public List<File> getFiles(UUID id) {
        return null;
    }

    @Override
    public List<File> getFiles(UUID id, File directory) {
        return null;
    }

    @Override
    public void removeDirectory(UUID id){

    }
}
