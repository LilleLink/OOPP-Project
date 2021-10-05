package attachmentHandler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * The facade for the entire AttachmentHandler service that can save attachments to specific IDs under user.home/.prm/{id}.
 * Contains all functionality needed to save attachments, optionally under different categories, as well as retrieve and
 * delete those attachments.
 */
public interface IAttachmentHandlerFacade {
    /**
     * Saves a file in the base directory for the given id.
     * @param id ID of the entity you want to store the file under.
     * @param file A path object pointing to the file you want to copy.
     * @throws IOException If an I/O error occurs, most likely that the file does not exist.
     */
    void saveAttachment(UUID id, Path file) throws IOException;

    /**
     * Saves a file in the given category for the given id. Will throw an illegalArgumentException if the category
     * string contains anything other than letters.
     * @param id ID of the entity you want to store the file under.
     * @param file A path object pointing to the file you want to copy.
     * @param category The category to store the file to, this string should only contain letters.
     * @throws IOException If an I/O error occurs, most likely that the file does not exist.
     * @throws IllegalArgumentException If the category contains non-letters.
     */
    void saveAttachment(UUID id, Path file, String category) throws IOException, IllegalArgumentException;

    /**
     * Returns a list of Path objects containing all the files saved under the given ID.
     * @param id ID of the entity you want to see saved files of.
     * @return A list of Path objects pointing to all the files saved under the given ID.
     * @throws IOException If an I/O error occurs.
     */
    List<Path> getAttachmentNames(UUID id) throws IOException;

    /**
     * Returns a list of Path objects containing all the files in the given category saved under the given ID.
     * @param id ID of the entity you want to see saved files of.
     * @param category The category to retrieve the files from, this string should only contain letters.
     * @return A list of Path objectse pointing to all the files saved under the given ID in the given category.
     * @throws IOException If an I/O error occurs.
     * @throws IllegalArgumentException If the category contains non-letters.
     */
    List<Path> getAttachmentNames(UUID id, String category) throws IOException, IllegalArgumentException;

    /**
     * Removes the given ID's directory and all it's subdirectories and files.
     * @param id The ID to remove.
     * @throws IOException If an I/O error occurs.
     */
    void removeId(UUID id) throws IOException;

    /**
     * Removes the given ID's files under a certain category.
     * @param id The ID you want to remove a category from.
     * @param category The category to remove.
     * @throws IOException If an I/O error occurs.
     */
    void removeAttachmentCategory(UUID id, String category) throws IOException;
}
