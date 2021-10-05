package attachmentHandler;

/**
 * A static class to provide the instance of the service.
 */
public class AttachmentHandlerFactory {
    static private IAttachmentHandlerFacade instance = null;

    public static IAttachmentHandlerFacade getService(){
        if (instance == null){
            instance = new AttachmentHandler();
        }
        return instance;
    }

    private AttachmentHandlerFactory(){}
}
