package vcf;

import model.Contact;
import model.ContactList;
import model.exceptions.NameNotAllowedException;

import javax.naming.Name;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class VCFParser {

    private final ContactList contacts;

    public VCFParser(ContactList contactList){
        this.contacts = contactList;
    }

    public void addContact(Path path) throws IOException, NameNotAllowedException{
        if (isVCFFile(path)){
            readContact(path);
        } else {
            throw new IOException();
        }
    }

    public void addContactsFromDirectory(Path directory) throws IOException {
        int createdContacts = 0;
        for (Path path: Files.newDirectoryStream(directory)){
            if (isVCFFile(path)){
                try {
                    addContact(path);
                    createdContacts++;
                } catch (IOException | NameNotAllowedException ignored){

                }
            }
        }
        if (createdContacts == 0) throw new IOException("No *.vcf files found in the directory");
    }
    
    private boolean isVCFFile(Path path){
        return path.toString().toLowerCase().endsWith(".vcf");
    }
    
    private void readContact(Path path) throws IOException, NameNotAllowedException{
        Scanner reader = new Scanner(path);
        Contact.ContactCache cache = new Contact.ContactCache();
        while (reader.hasNextLine()){
            String line = reader.nextLine();
            System.out.println(line);
            String[] data = line.split(";");
            if (data[0].charAt(0)=='#') continue;
            if (data[0].startsWith("FN")){
                cache.name = data[0].substring(3);
                continue;
            }
            if (data[0].equals("TEL")){
                cache.phoneNumber = data[1].split(":")[1];
            }
            if (data[0].equals("ADR")){
                cache.address = data[3];
            }
        }
        contacts.addContact(cache);
    }
}
