package attachmentHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

class AttachmentHandler implements IAttachmentHandlerFacade {
    private final Path baseDirectory = Paths.get(System.getProperty("user.home") + "/.prm/");

    AttachmentHandler(){

    }

    @Override
    public void saveAttachment(UUID id, Path sourceFile) throws IOException {
        Path idSpecificDirectory = baseDirectory.resolve(id.toString());
        Files.createDirectories(idSpecificDirectory);
        Files.copy(sourceFile, idSpecificDirectory
                .resolve(sourceFile.getFileName()), REPLACE_EXISTING);

    }

    @Override
    public void saveAttachment(UUID id, Path sourceFile, String category) throws IOException, IllegalArgumentException {
        category = category.toLowerCase();
        if (!category.chars().allMatch(Character::isLetter)){
            throw new IllegalArgumentException("A category should only contain letters");
        }
        Path idSpecificDirectory = baseDirectory.resolve(id.toString());
        Files.createDirectories((baseDirectory.resolve(id.toString())).resolve(category));
        Files.copy(sourceFile, idSpecificDirectory
                .resolve(category)
                .resolve(sourceFile.getFileName()), REPLACE_EXISTING);
    }

    @Override
    public List<Path> getAttachmentNames(UUID id) throws IOException {
        Path idSpecificDirectory = baseDirectory.resolve(id.toString());
        List<Path> files = new ArrayList<>();
        if (!Files.exists(idSpecificDirectory)) return files;
        Files.walk(idSpecificDirectory)
                .filter(Files::isRegularFile).forEach(files::add);
        return files;
    }

    @Override
    public List<Path> getAttachmentNames(UUID id, String category) throws IOException, IllegalArgumentException {
        Path idSpecificDirectory = baseDirectory.resolve(id.toString());
        category = category.toLowerCase();
        if (!category.chars().allMatch(Character::isLetter)){
            throw new IllegalArgumentException("A category should only contain letters");
        }
        List<Path> files = new ArrayList<>();
        if (!Files.exists(idSpecificDirectory.resolve(category))) return files;
        Files.walk(idSpecificDirectory.resolve(category)).filter(Files::isRegularFile).forEach(files::add);
        return files;
    }

    @Override
    public void removeAttachmentCategory(UUID id, String category) throws IOException {
        Path idSpecificDirectory = baseDirectory.resolve(id.toString());
        deleteDirectoryRecursively(idSpecificDirectory.resolve(category));
    }

    @Override
    public void removeId(UUID id) throws IOException {
        deleteDirectoryRecursively(baseDirectory.resolve(id.toString()));
    }

    private void deleteDirectoryRecursively(Path directory) throws IOException {
        Files.walk(directory)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

}
