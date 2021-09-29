package model;


import java.util.ArrayList;
import java.util.List;
import model.exceptions.TagNotFoundException;

public class Contact {

    private String name;
    private String phoneNumber = "";
    private Address address = new Address("");
    private final List<ITag> tags = new ArrayList<>();
    private final Notes notes = new Notes();

    /**
     * @param name The contact's name.
     */
    Contact(String name){
        this.name = name;
    }

    /**
     *
     * @return The contact's name.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return The contact's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @return Contact's address as string.
     */
    public String getAddress(){
        return this.address.getAddress();
    }

    /**
     * Opens the contact's address on google maps in browser.
     * @return If it worked or not.
     */
    public boolean openMap(){
        return this.address.openMap();
    }

    /**
     * Updates the contact's address.
     * @param address The address to be updated to.
     */
    void setAddress(String address){
        this.address = new Address(address);
    }


    /**
     * Sets the name of the contact.
     * @param name The name to change to.
     */
    void setName(String name){
        this.name = name;
    }

    /**
     * Sets the phone number of the contact.
     * @param number The number to change to.
     */
    void setPhoneNumber(String number){
        this.phoneNumber = number;
    }



    /**
     * Adds a tag to the contact.
     * @param tag The desired tag.
     */
    void addTag(ITag tag){
        tags.add(tag);
    }

    /**
     * Removes a tag from the contact.
     * @param tag The tag to remove.
     * @throws TagNotFoundException If the contact does not have the given tag.
     */
    void removeTag(ITag tag) throws TagNotFoundException {
        if (!tags.contains(tag)) throw new TagNotFoundException(tag.getName());
        tags.remove(tag);
    }

    /**
     *
     * @return A list of the contact's tags.
     */
    public List<ITag> getTags(){
        return new ArrayList<>(tags);
    }

    /**
     * Adds a note with the specified text to Notes.
     * @param text the information to be added
     */
    void addNote(String text) {
        notes.addNote(text);
    }

    /**
     * Adds a note with empty text to Notes.
     */
    void addNote() {
        notes.addNote();
    }

    /**
     * Removes the note at the specified index
      * @param index the index of the note to be removed
     */
    void removeNote(int index) {
        notes.removeNote(index);
    }

    /**
     * Edits the note at the specified index with the given text.
     * @param index the index of the note to be edited
     * @param text the new text
     */
    void editNote(int index, String text) {
        notes.editNoteAt(index,text);
    }

    /**
     * Retrieves the text at the given index
     * @param index the index of the note to view
     * @return a string
     */
    String viewNoteAt(int index) {
        return notes.viewNoteAt(index);
    }

    /**
     * Retrieves the notes in this contact.
     * @return a list of notes
     */
    List<Note> getNotes() {
        return notes.getSortedElem();
    }


}
