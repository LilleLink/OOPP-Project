package fileHandler;

/**
 * A static class to provide the instance of the service.
 */
public class FileHandlerFactory {
    static private IFileHandlerFacade instance = null;

    public static IFileHandlerFacade getService(){
        if (instance == null){
            instance = new FileHandler();
        }
        return instance;
    }

    private FileHandlerFactory(){}
}
