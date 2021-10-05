package fileHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

class FileHandler implements IFileHandlerFacade {
    private final Path baseDirectory = Paths.get(System.getProperty("user.home") + "/.prm/");

    FileHandler(){

    }

    @Override
    public void saveAttachment(UUID id, Path sourceFile) throws IOException {
        Path attachmentDirectory = baseDirectory.resolve(id.toString() + "/attachments");
        Files.createDirectories(attachmentDirectory);
        Files.copy(sourceFile, attachmentDirectory
                .resolve(sourceFile.getFileName()), REPLACE_EXISTING);

    }

    @Override
    public void saveAttachment(UUID id, Path sourceFile, String category) throws IOException, IllegalArgumentException {
        category = category.toLowerCase();
        if (!category.chars().allMatch(Character::isLetter)){
            throw new IllegalArgumentException("A category should only contain letters");
        }
        Path attachmentDirectory = baseDirectory.resolve(id.toString() + "/attachments");
        Files.createDirectories(attachmentDirectory.resolve(category));
        Files.copy(sourceFile, attachmentDirectory
                .resolve(category)
                .resolve(sourceFile.getFileName()), REPLACE_EXISTING);
    }

    @Override
    public List<Path> getAttachmentsPaths(UUID id) throws IOException {
        Path attachmentDirectory = baseDirectory.resolve(id.toString() + "/attachments");
        List<Path> files = new ArrayList<>();
        if (!Files.exists(attachmentDirectory)) return files;
        Files.walk(attachmentDirectory)
                .filter(Files::isRegularFile).forEach(files::add);
        return files;
    }

    @Override
    public List<Path> getAttachmentsPaths(UUID id, String category) throws IOException, IllegalArgumentException {
        Path attachmentDirectory = baseDirectory.resolve(id.toString() + "/attachments");
        category = category.toLowerCase();
        if (!category.chars().allMatch(Character::isLetter)){
            throw new IllegalArgumentException("A category should only contain letters");
        }
        List<Path> files = new ArrayList<>();
        if (!Files.exists(attachmentDirectory.resolve(category))) return files;
        Files.walk(attachmentDirectory.resolve(category)).filter(Files::isRegularFile).forEach(files::add);
        return files;
    }

    @Override
    public void removeAttachmentCategory(UUID id, String category) throws IOException {
        Path attachmentDirectory = baseDirectory.resolve(id.toString() + "/attachments");
        deleteDirectoryRecursively(attachmentDirectory.resolve(category));
    }

    @Override
    public void removeAllAttachments(UUID id) throws IOException {
        deleteDirectoryRecursively(baseDirectory.resolve(id.toString() + "/attachments"));
    }

    @Override
    public void removeAllFiles(UUID id) throws IOException {
        deleteDirectoryRecursively(baseDirectory.resolve(id.toString()));
    }

    @Override
    public void saveMainImage(UUID id, Path picture) throws IllegalArgumentException, IOException {
        List<String> imageFileExtensions = Arrays.asList("bmp", "gif", "jpeg", "jpg", "png");
        if (!imageFileExtensions.contains(getFileExtension(picture).toLowerCase())) {
            throw new IllegalArgumentException("The specified file needs to be a image.");
        } else {
            Files.copy(picture, baseDirectory.resolve(id.toString()).resolve("mainPicture"), REPLACE_EXISTING);
        }
    }

    @Override
    public void removeMainImage(UUID id) throws IOException {
        Path mainPicture = baseDirectory.resolve(id.toString()).resolve("mainPicture");
        if (Files.exists(mainPicture)){
            Files.delete(mainPicture);
        }
    }

    @Override
    public Path getMainImagePath(UUID id) {
        return baseDirectory.resolve(id.toString()).resolve("mainPicture");
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

    private String getFileExtension(Path file) {
        String name = file.getFileName().toString();
        int lastDot = name.lastIndexOf(".");
        if (lastDot == -1) {
            return ""; // empty extension
        }
        return name.substring(lastDot);
    }

}
