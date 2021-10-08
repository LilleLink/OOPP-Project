package attachmentHandler;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;


import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class AttachmentHandlerTest {
    private static final IAttachmentHandler fileHandler = AttachmentHandlerFactory.getService();

    private static final UUID id = UUID.randomUUID();
    private static final Path testFileDirectory = Paths.get("src/test/java/attachmentHandler/testFiles/");

    //TODO javadoc for tests, maybe write some more rigorous tests

    @After
    public void removeTestIdDirectory(){
        try {
            fileHandler.removeAllFiles(id);
        } catch (NoSuchFileException ignored) {}
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addAttachmentsNoCategoryTest() throws IOException {
        fileHandler.addAttachment(id, testFileDirectory.resolve("test1.txt"));
        assertEquals("test1.txt", fileHandler.getAttachments(id).get(0).getFileName().toString());
    }

    @Test
    public void attachmentsCategoryTest() throws IOException {
        fileHandler.addAttachment(id, testFileDirectory.resolve("test1.txt"), "textfiles");
        fileHandler.addAttachment(id, testFileDirectory.resolve("test2.txt"), "textfiles");
        fileHandler.addAttachment(id, testFileDirectory.resolve("testImage.png"), "images");
        assertEquals(2, fileHandler.getAttachmentCategories(id).size());
        assertEquals(2, fileHandler.getAttachments(id, "textfiles").size());
        assertEquals(1, fileHandler.getAttachments(id, "images").size());
        assertEquals(3, fileHandler.getAttachments(id).size());
    }

    @Test
    public void removeCategoryTest() throws IOException {
        fileHandler.addAttachment(id, testFileDirectory.resolve("test1.txt"), "textfiles");
        fileHandler.addAttachment(id, testFileDirectory.resolve("test2.txt"), "textfiles");
        fileHandler.addAttachment(id, testFileDirectory.resolve("testImage.png"), "images");
        fileHandler.removeAttachmentCategory(id,"textfiles");
        assertEquals(1, fileHandler.getAttachments(id).size());
    }

    @Test
    public void removeAllAttachmentsTest() throws IOException {
        fileHandler.addAttachment(id, testFileDirectory.resolve("test1.txt"), "textfiles");
        fileHandler.addAttachment(id, testFileDirectory.resolve("test2.txt"), "textfiles");
        fileHandler.addAttachment(id, testFileDirectory.resolve("testImage.png"), "images");
        fileHandler.removeAllAttachments(id);
        assertEquals(0, fileHandler.getAttachments(id).size());
    }

    @Test
    public void removeEmptyDirectoriesTest() throws IOException {
        fileHandler.removeAttachmentCategory(id, "textfiles");
        fileHandler.removeAllFiles(id);
    }

    @Test
    public void getEmptyMainImageTest(){
        assertThrows(NoSuchFileException.class, () -> fileHandler.getMainImage(id));
    }

    @Test
    public void saveAndGetMainImageTest() throws IOException {
        fileHandler.saveMainImage(id, testFileDirectory.resolve("testImage.png"));
        assertEquals("testImage.png", fileHandler.getMainImage(id).getFileName().toString());
    }

    @Test
    public void saveMultipleImagesOverrideTest() throws IOException {
        fileHandler.saveMainImage(id, testFileDirectory.resolve("testImage.png"));
        assertEquals("testImage.png", fileHandler.getMainImage(id).getFileName().toString());
        fileHandler.saveMainImage(id, testFileDirectory.resolve("testImage2.jpg"));
        assertEquals("testImage2.jpg", fileHandler.getMainImage(id).getFileName().toString());
    }

    @Test
    public void getMainImageTest() throws IOException {
        fileHandler.saveMainImage(id, testFileDirectory.resolve("testImage.png"));
        assertEquals("testImage.png", fileHandler.getMainImage(id).getFileName().toString());
    }

    @Test
    public void removeThenGetMainImageTest() throws IOException {
        fileHandler.removeMainImage(id);
        assertThrows(NoSuchFileException.class, () -> fileHandler.getMainImage(id));
    }
}
