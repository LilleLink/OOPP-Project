package attachmentHandler;

/**
 * A static class to provide the instance of the service.
 */
public class AttachmentHandlerFactory {
    static private IAttachmentHandler instance = null;

    /**
     * @return The AttachmentHandler Service
     */
    public static IAttachmentHandler getService() {
        if (instance == null) {
            instance = new AttachmentHandler();
        }
        return instance;
    }

    private AttachmentHandlerFactory() {
    }
}
