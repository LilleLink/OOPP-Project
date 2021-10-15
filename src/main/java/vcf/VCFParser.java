package vcf;

import model.Contact;
import model.ContactList;
import model.exceptions.NameNotAllowedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Class that reads a file or directory and adds contacts to a given list
 */
public class VCFParser {

    private final ContactList contacts;

    /**
     * Creates a new parser
     *
     * @param contactList the ContactList where the new contacts will be added
     */
    public VCFParser(ContactList contactList) {
        this.contacts = contactList;
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
        //FIXME make recursive?
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

    private void readContact(Path path) throws IOException, NameNotAllowedException {
        Scanner reader = new Scanner(path);
        Contact.ContactCache cache = new Contact.ContactCache();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] data = line.split(";");
            if (data[0].charAt(0) == '#') continue;
            if (data[0].startsWith("FN")) {
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
}
