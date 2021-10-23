package vcf;

import model.ContactList;
import model.TagHandler;

/***
 * Returns an instance of the VCF parser service.
 */
public final class VCFParserFactory {

    static private IVCFParser instance = null;

    public static synchronized IVCFParser getService(ContactList contactList, TagHandler tagHandler) {
        if (instance == null) {
            instance = new VCFParser(contactList, tagHandler);
        }
        return instance;
    }

    private VCFParserFactory() {
    }
}
