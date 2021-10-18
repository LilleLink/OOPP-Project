package vcf;

import model.Contact;
import model.ContactList;
import model.ITag;
import model.TagHandler;
import model.exceptions.NameNotAllowedException;
import model.exceptions.NameNotAvailableException;
import model.exceptions.TagNotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


/**
 * Class that reads a file or directory and adds contacts to a given list
 */
public class VCFParser {

    private final ContactList contacts;

    private HashMap<String, List<String>> data;

    private final TagHandler tagHandler;

    /**
     * Creates a new parser
     *
     * @param contactList the ContactList where the new contacts will be added
     */
    public VCFParser(ContactList contactList, TagHandler tagHandler) {
        this.contacts = contactList;
        this.tagHandler = tagHandler;
    }

    /**
     * Adds a contact from a {*.vcf} file to the list given in the constructor
     *
     * @param path the path to the vcf file
     * @throws IOException             if the file was not a {*.vcf} file or if an error occurs in {@link Scanner}
     * @throws NameNotAllowedException if a card with an illegal name is read
     */
    public void addContact(Path path) throws IOException, NameNotAllowedException {
        if (isVCFFile(path)) {
            readContact(path);
        } else {
            throw new FileNotFoundException();
        }
    }

    /**
     * adds contacts from all vcf files in a directory (not recursive)
     *
     * @param directory the directory to be read
     * @throws IOException if no {*.vcf} file is found or if an error occurs in {@link Scanner}
     */
    public void addContactsFromDirectory(Path directory) throws IOException {
        int createdContacts = 0;
        for (Path path : Files.newDirectoryStream(directory)) {
            if (isVCFFile(path)) {
                try {
                    addContact(path);
                    createdContacts++;
                } catch (IOException | NameNotAllowedException ignored) {

                }
            }
        }
        if (createdContacts == 0) throw new IOException("No *.vcf files found in the directory");
    }

    private boolean isVCFFile(Path path) {
        return path.toString().toLowerCase().endsWith(".vcf");
    }

    private void readContact2(Path path) throws IOException {
        data = parseData(path);
        Contact.ContactCache cache = new Contact.ContactCache();
        readName(cache);
        readAddress(cache);
        readTags(cache);
        readPhoneNumber(cache);
    }

    private HashMap<String, List<String>> parseData(Path path) throws IOException {
        HashMap<String, List<String>> parsedData = new HashMap<>();
        Scanner scanner = new Scanner(path);
        String line;
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            String type = line.split(":")[0].split(";")[0];
            parsedData.computeIfAbsent(type, k -> new ArrayList<>());
            Collection<String> data = new ArrayList<>();
            switch (type) {
                case "ADR":
                case "N":
                case "NOTE": {
                    data.add(line.split(":", 2)[1]);
                    break;
                }
                case "CATEGORIES": {
                    String[] categories = line.split(":")[1].split(",");
                    data.addAll(Arrays.asList(categories));
                    break;
                }
                case "FN":
                case "VERSION": {
                    data.add(line.split(":")[1]);
                    break;
                }
                case "TEL": {
                    String[] telInfo = line.split(":");
                    data.add(telInfo[telInfo.length - 1]);
                    break;
                }
            }
            parsedData.get(type).addAll(data);
        }
        return parsedData;
    }

    private void readName(Contact.ContactCache cache) {
        if (data.containsKey("FN")) {
            cache.name = data.get("FN").get(0);
            return;
        }
        String[] unFormattedName = data.get("N").get(0).split(";");
        StringBuilder sb = new StringBuilder();
        for (int i = unFormattedName.length - 1; i >= 0; i--) {
            sb.append(unFormattedName[i]);
        }
        cache.name = sb.toString();
    }

    private void readAddress(Contact.ContactCache cache) {
        String[] addressParts = data.get("ADR").get(0).split(";");
        List<String> activeParts = new ArrayList<>();
        for (String part : addressParts) {
            if (part.length() > 0) {
                activeParts.add(part);
            }
        }
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = activeParts.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        cache.address = sb.toString();
    }

    private void readTags(Contact.ContactCache cache) {
        List<ITag> tags = new ArrayList<>();
        for (String tag : data.get("CATEGORIES")) {
            try {
                tags.add(tagHandler.createTag(tag));
            } catch (NameNotAvailableException e) {
                try {
                    tags.add(tagHandler.getTag(tag));
                } catch (TagNotFoundException ignored) {
                }
            } catch (NameNotAllowedException ignored) {
            }
        }
        cache.tags = tags;
    }

    private void readPhoneNumber(Contact.ContactCache cache) {
        cache.phoneNumber = data.get("TEL").get(0);
    }

    private void readNote(Contact.ContactCache cache) {
        
    }

    private void readContact(Path path) throws IOException, NameNotAllowedException {
        Scanner reader = new Scanner(path);
        Contact.ContactCache cache = new Contact.ContactCache();
        String line = reader.nextLine();
        if (line.equals("BEGIN:VCARD")) {
            line = reader.nextLine();
        }
        int version = 1;
        if (line.startsWith("VERSION")) {
            version = readVersion(line);
        }
        while (reader.hasNextLine()) {
            line = reader.nextLine();
            if (line.charAt(0) == '#') continue;
            String[] data = line.split(";");
            if (data[0].startsWith("FN") && version > 1) {
                cache.name = data[0].substring(3);
                continue;
            }
            if (data[0].equals("TEL")) {
                cache.phoneNumber = data[1].split(":")[1];
            }
            if (data[0].equals("ADR")) {
                cache.address = data[3];
            }
        }
        cache.tags = new ArrayList<>();
        contacts.addContact(cache);
    }

    private int readVersion(String line) {
        String strVersion = line.split(":")[1];
        return Integer.parseInt(strVersion);
    }
}
