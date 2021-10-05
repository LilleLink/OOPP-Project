package attachmentHandler;

import org.junit.Test;



import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class AttachmentHandlerTest {
    IAttachmentHandlerFacade fileHandler = AttachmentHandlerFactory.getService();

    @Test
    public void testTest(){
        UUID id = UUID.randomUUID();
        try {
            fileHandler.saveAttachment(id, Paths.get("C:/Users/axoxe/dev/java-devel/testCopy.txt"));
            fileHandler.saveAttachment(id, Paths.get("C:/Users/axoxe/dev/java-devel/testCopy.txt"), "test" );
            for (Path file : fileHandler.getAttachmentNames(id)){
                System.out.println(file);
            }
            fileHandler.removeId(id);
            for (Path file : fileHandler.getAttachmentNames(id)){
                System.out.println(file);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
