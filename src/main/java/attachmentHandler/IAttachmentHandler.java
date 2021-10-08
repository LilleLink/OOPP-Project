package attachmentHandler;

import java.nio.file.NoSuchFileException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * The facade for the entire AttachmentHandler service that can save attachments and the main picture to specific IDs under
 * user.home/.prm/{id}.
 * Contains all functionality needed to save attachments, optionally under different categories, as well as retrieve and
 * delete those attachments. Also contains the functionality for a main picture.
 */
public interface IAttachmentHandler {
    /**
     * Saves a file in the base directory for the given id.
     * @param id ID of the entity you want to store the file under.
     * @param file A path object pointing to the file you want to copy.
     * @throws IOException If an I/O error occurs, most likely that the file does not exist.
     */
    void addAttachment(UUID id, Path file) throws IOException;

    /**
     * Saves a file in the given category for the given id. Will throw an illegalArgumentException if the category
     * string contains anything other than letters.
     * @param id ID of the entity you want to store the file under.
     * @param file A path object pointing to the file you want to copy.
     * @param category The category to store the file to, this string should only contain letters.
     * @throws IOException If an I/O error occurs, most likely that the file does not exist.
     * @throws IllegalArgumentException If the category contains non-letters.
     */
    void addAttachment(UUID id, Path file, String category) throws IOException, IllegalArgumentException;

    /**
     * Returns a list of Path objects containing all the files saved under the given ID.
     * @param id ID of the entity you want to see saved files of.
     * @return A list of Path objects pointing to all the files saved under the given ID.
     * @throws IOException If an I/O error occurs.
     */
    List<Path> getAttachments(UUID id) throws IOException;

    /**
     * Returns a list of Path objects containing all the files in the given category saved under the given ID.
     * @param id ID of the entity you want to see saved files of.
     * @param category The category to retrieve the files from, this string should only contain letters.
     * @return A list of Path objectse pointing to all the files saved under the given ID in the given category.
     * @throws IOException If an I/O error occurs.
     * @throws IllegalArgumentException If the category contains non-letters.
     */
    List<Path> getAttachments(UUID id, String category) throws IOException, IllegalArgumentException;

    /**
     * Returns a list of the ID's attachment categories
     * @param id The ID to get categories from.
     * @return A list of Strings containing the names of the categories.
     * @throws IOException If an I/O error occurs.
     */
    List<String> getAttachmentCategories(UUID id) throws IOException;

    /**
     * Removes the given ID's directory and all it's subdirectories and files.
     * @param id The ID to remove.
     * @throws IOException If an I/O error occurs.
     */
    void removeAllAttachments(UUID id) throws IOException;

    /**
     * Removes the given ID's files under a certain category.
     * @param id The ID you want to remove a category from.
     * @param category The category to remove.
     * @throws IOException If an I/O error occurs.
     */
    void removeAttachmentCategory(UUID id, String category) throws IOException;

    /**
     * Removes all files and the entire directory for an ID.
     * @param id The ID you want to delete all files from.
     * @throws IOException If an I/O error occurs.
     */
    void removeAllFiles(UUID id) throws IOException;

    /**
     * Saves a main image to the given ID. Will overwrite the old image.
     * @param id The ID you want to save the image to.
     * @param image The image to save.
     * @throws IllegalArgumentException If the given image is not a supported filetype.
     * @throws IOException If an I/O error occurs.
     */
    void saveMainImage(UUID id, Path image) throws IllegalArgumentException, IOException;

    /**
     * Will remove the main image from the given ID.
     * @param id The id to remove its main image from.
     * @throws IOException If an I/O error occurs.
     */
    void removeMainImage(UUID id) throws IOException;

    /**
     * Gets the main image for an ID.
     * @param id The id to get the main image from.
     * @return A path object pointing to the main image of the given ID.
     * @throws IOException If an I/O error occurs.
     * @throws NoSuchFileException If the ID does not have a main image saved.
     */
    Path getMainImage(UUID id) throws IOException, NoSuchFileException;
}
