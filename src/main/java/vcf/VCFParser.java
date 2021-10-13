package vcf;

import model.Contact;
import model.ContactList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class VCFParser {

    private ContactList contacts;

    VCFParser(){}

    public VCFParser(ContactList contactList){
        this.contacts = contactList;
    }

    public void addContact(Path path) throws IOException {
        Contact.ContactCache cache = new Contact.ContactCache();
        Scanner reader = new Scanner(path);
        while (reader.hasNextLine()){
            System.out.println(reader.nextLine());
        }
        //TODO replace with contact loading
    }
}
