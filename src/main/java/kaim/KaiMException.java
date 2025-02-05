package kaim;

/**
 * Represents an exception specific to the KaiM application.
 * This exception is thrown when an error occurs during task processing
 * or other application operations.
 */
public class KaiMException extends Exception {
    
    /**
     * Working in progress
     * Constructs a new KaiMException with the specified detail message.
     * 
     * @param message The error message describing the exception.
     */
    public KaiMException(String message) {
        super(message);
    }
}
