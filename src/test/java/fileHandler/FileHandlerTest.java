package fileHandler;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileHandlerTest {
    IFileHandlerFacade fileHandler = FileHandlerFactory.getService();

    @Test
    public void testTest(){
        UUID id = UUID.randomUUID();
        try {
            //fileHandler.saveFile(id, Paths.get("C:/Users/axoxe/dev/java-devel/testCopy.txt"));
            Files.createDirectories(Paths.get(System.getProperty("user.home") + "/.prm/").resolve(id.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
