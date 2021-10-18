package vcf;

import model.*;
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

    private HashMap<FIELD, List<String>> data;

    private HashMap<String, FIELD> fields = initFIELDSHashMap();

    private HashMap<String, FIELD> initFIELDSHashMap() {
        HashMap<String, FIELD> map = new HashMap<>();
        for (FIELD field : FIELD.values()) {
            map.put(field.getCode(), field);
        }
        return map;
    }

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
            readContact2(path);
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

    private void readContact2(Path path) throws IOException, NameNotAllowedException {
        data = parseData(path);
        Contact.ContactCache cache = new Contact.ContactCache();
        System.out.println(data);
        readName(cache);
        readAddress(cache);
        readTags(cache);
        readPhoneNumber(cache);
        readNote(cache);
        createUUID(cache);
        contacts.addContact(cache);
    }

    private HashMap<FIELD, List<String>> parseData(Path path) throws IOException {
        HashMap<FIELD, List<String>> parsedData = new HashMap<>();
        for (FIELD field : FIELD.values()) {
            parsedData.put(field, new ArrayList<>());
        }
        Scanner scanner = new Scanner(path);
        String line;
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            String stringType = line.split(":")[0].split(";")[0];
            System.out.println(stringType);
            FIELD type = fields.get(stringType);
            System.out.println(type);
            if (type == null) {
                continue;
            }
            Collection<String> data = new ArrayList<>();
            switch (type) {
                case ADDRESS:
                case NAME:
                case NOTE: {
                    data.add(line.split(":", 2)[1]);
                    break;
                }
                case CATEGORIES: {
                    String[] categories = line.split(":")[1].split(",");
                    data.addAll(Arrays.asList(categories));
                    break;
                }
                case FORMATTED_NAME:
                case VERSION: {
                    data.add(line.split(":")[1]);
                    break;
                }
                case TELEPHONE: {
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
        if (data.get(FIELD.FORMATTED_NAME).size() > 0) {
            cache.name = data.get(FIELD.FORMATTED_NAME).get(0);
            return;
        }
        if (data.get(FIELD.NAME).size() <= 0) {
            cache.name = "";
            return;
        }
        String[] unFormattedName = data.get(FIELD.NAME).get(0).split(";");
        StringBuilder sb = new StringBuilder();
        for (int i = unFormattedName.length - 1; i >= 0; i--) {
            sb.append(unFormattedName[i]);
        }
        cache.name = sb.toString();
    }

    private void readAddress(Contact.ContactCache cache) {
        if (!(data.get(FIELD.ADDRESS).size() > 0)) {
            cache.address = "";
            return;
        }
        String[] addressParts = data.get(FIELD.ADDRESS).get(0).split(";");
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
        data.get(FIELD.CATEGORIES).forEach(tag -> {
            try {
                tags.add(tagHandler.createTag(tag));
            } catch (NameNotAvailableException e) {
                try {
                    tags.add(tagHandler.getTag(tag));
                } catch (TagNotFoundException ignored) {
                }
            } catch (NameNotAllowedException ignored) {
            }
        });
        cache.tags = tags;
    }

    private void readPhoneNumber(Contact.ContactCache cache) {
        List<String> numbers = data.get(FIELD.TELEPHONE);
        cache.phoneNumber = numbers.size() > 0 ? numbers.get(0) : "";
    }

    private void readNote(Contact.ContactCache cache) {
        Notes notes = new Notes();
        data.get(FIELD.NOTE).forEach(notes::add);
        cache.notes = notes;
    }

    private void createUUID(Contact.ContactCache cache) {
        cache.directoryId = UUID.randomUUID();
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
