package fileHandler;

import org.junit.Test;



import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileHandlerTest {
    IFileHandlerFacade fileHandler = FileHandlerFactory.getService();

    @Test
    public void testTest(){
        UUID id = UUID.randomUUID();
        try {
            fileHandler.saveAttachment(id, Paths.get("C:/Users/axoxe/dev/java-devel/testCopy.txt"));
            fileHandler.saveAttachment(id, Paths.get("C:/Users/axoxe/dev/java-devel/testCopy.txt"), "test" );
            for (Path file : fileHandler.getAttachmentsPaths(id)){
                System.out.println(file);
            }
            fileHandler.removeAllAttachments(id);
            for (Path file : fileHandler.getAttachmentsPaths(id)){
                System.out.println(file);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
