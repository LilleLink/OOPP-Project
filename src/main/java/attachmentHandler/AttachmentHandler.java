package attachmentHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

class AttachmentHandler implements IAttachmentHandler {
    private final Path baseDirectory = Paths.get(System.getProperty("user.home") + "/.prm/");

    AttachmentHandler(){

    }

    @Override
    public void addAttachment(UUID id, Path sourceFile) throws IOException {
        Path attachmentDirectory = baseDirectory.resolve(id.toString() + "/attachments");
        Files.createDirectories(attachmentDirectory);
        Files.copy(sourceFile, attachmentDirectory
                .resolve(sourceFile.getFileName()), REPLACE_EXISTING);

    }

    @Override
    public void addAttachment(UUID id, Path sourceFile, String category) throws IOException, IllegalArgumentException {
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
    public List<Path> getAttachments(UUID id) throws IOException {
        Path attachmentDirectory = baseDirectory.resolve(id.toString() + "/attachments");
        List<Path> files = new ArrayList<>();
        if (!Files.exists(attachmentDirectory)) return files;
        Files.walk(attachmentDirectory)
                .filter(Files::isRegularFile).forEach(files::add);
        return files;
    }

    @Override
    public List<Path> getAttachments(UUID id, String category) throws IOException, IllegalArgumentException {
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
    public List<String> getAttachmentCategories(UUID id) throws IOException {
        List<String> attachmentCategories = new ArrayList<>();
        Path attachmentDirectory = baseDirectory.resolve(id.toString() + "/attachments");
        Files.walk(attachmentDirectory, 1)
                .filter(Files::isDirectory)
                .forEach((directory) -> {
                    if (directory != attachmentDirectory){
                        attachmentCategories.add(directory.getFileName().toString());
                    }
                });
        return attachmentCategories;
    }

    @Override
    public void removeAttachmentCategory(UUID id, String category) throws IOException {
        Path attachmentDirectory = baseDirectory.resolve(id.toString() + "/attachments");
        if (Files.exists(attachmentDirectory.resolve(category)))
            deleteDirectoryRecursively(attachmentDirectory.resolve(category));
    }

    @Override
    public void removeAllAttachments(UUID id) throws IOException {
        if (Files.exists(baseDirectory.resolve(id.toString() + "/attachments")))
            deleteDirectoryRecursively(baseDirectory.resolve(id.toString() + "/attachments"));
    }

    @Override
    public void removeAllFiles(UUID id) throws IOException {
        if (Files.exists(baseDirectory.resolve(id.toString())))
            deleteDirectoryRecursively(baseDirectory.resolve(id.toString()));
    }

    @Override
    public void saveMainImage(UUID id, Path picture) throws IllegalArgumentException, IOException {
        List<String> imageFileExtensions = Arrays.asList("bmp", "gif", "jpeg", "jpg", "png");
        Path mainImageDirectory = baseDirectory.resolve(id.toString()).resolve("mainImage/");
        if (!imageFileExtensions.contains(getFileExtension(picture).toLowerCase())) {
            throw new IllegalArgumentException("The specified file needs to be a image.");
        }
        removeMainImage(id);
        Files.createDirectories(mainImageDirectory);
        Files.copy(picture, mainImageDirectory.resolve(picture.getFileName()), REPLACE_EXISTING);
    }

    @Override
    public void removeMainImage(UUID id) throws IOException {
        Path mainImage = baseDirectory.resolve(id.toString()).resolve("mainImage");
        if (Files.exists(mainImage)){
            deleteDirectoryRecursively(mainImage);
        }
    }

    @Override
    public Path getMainImage(UUID id) throws IOException, NoSuchFileException {
        Path mainImageDirectory = baseDirectory.resolve(id.toString()).resolve("mainImage/");
        List<Path> mainImageDirectoryFiles = new ArrayList<>();
        Files.walk(mainImageDirectory, 1).filter(Files::isRegularFile).forEach(mainImageDirectoryFiles::add);

        if (mainImageDirectoryFiles.isEmpty()) {
            throw new NoSuchFileException("There is no mainImage for this ID");
        } else {
            return mainImageDirectoryFiles.get(0);
        }
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
            return ""; //no extension
        }
        return name.substring(lastDot + 1); // returns extension without dot
    }

}
