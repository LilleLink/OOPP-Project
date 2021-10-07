package fileHandler;

/**
 * A static class to provide the instance of the service.
 */
public class FileHandlerFactory {
    static private IFileHandler instance = null;

    public static IFileHandler getService(){
        if (instance == null){
            instance = new FileHandler();
        }
        return instance;
    }

    private FileHandlerFactory(){}
}
