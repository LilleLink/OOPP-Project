package vcf;

/***
 * Defines enum "FIELD" with a number of fields such as address, categories etc. to help parse VCF-files.
 */
enum FIELD {
    NAME("N"),
    FORMATTED_NAME("FN"),
    ADDRESS("ADR"),
    CATEGORIES("CATEGORIES"),
    NOTE("NOTE"),
    TELEPHONE("TEL"),
    VERSION("VERSION");

    private final String code;

    FIELD(String code) {
        this.code = code;
    }

    String getCode() {
        return code;
    }
}
