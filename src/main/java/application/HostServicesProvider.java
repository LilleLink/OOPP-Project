package application;

import javafx.application.HostServices;

/**
 *
 */
public final class HostServicesProvider {

    private HostServicesProvider() {
    }

    private static HostServices hostServices;

    /**
     * Sets the hostServices of the provider, can only be done once, or it will throw an exception.
     *
     * @param services The applications hostServices.
     */
    public static void init(HostServices services) {
        if (hostServices != null) {
            throw new IllegalStateException("Host services already initialized");
        }
        hostServices = services;
    }

    /**
     * The applications hostServices if it has been set, otherwise throws an exception.
     *
     * @return The applications hostServices.
     */
    public static HostServices getHostServices() {
        if (hostServices == null) {
            throw new IllegalStateException("Host services not initialized");
        }
        return hostServices;
    }
}
