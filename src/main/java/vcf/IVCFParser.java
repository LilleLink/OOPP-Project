package vcf;

import model.exceptions.NameNotAllowedException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Defines functionality of a concrete implementation of a VCF-file parser
 */
public interface IVCFParser {

    /**
     * Adds a contact from a {*.vcf} file to the list given in the constructor
     *
     * @param path the path to the vcf file
     * @throws IOException             if the file was not a {*.vcf} file or if an error occurs in {@link Scanner}
     * @throws NameNotAllowedException if a card with an illegal name is read
     */
    void addContact(Path path) throws IOException, NameNotAllowedException;

    /**
     * adds contacts from all vcf files in a directory (not recursive)
     *
     * @param directory the directory to be read
     * @throws IOException if no {*.vcf} file is found or if an error occurs in {@link Scanner}
     */
    void addContactsFromDirectory(Path directory) throws IOException;
}
