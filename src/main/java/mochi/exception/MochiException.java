package mochi.exception;

/**
 * Represents an exception specific to the Mochi application.
 * This exception is thrown when an error occurs during task processing
 * or other application operations.
 */
public class MochiException extends Exception {
    /**
     * Constructs a new MochiException with the specified detail message.
     *
     * @param message The error message describing the exception.
     */
    public MochiException(String message) {
        super(message);
    }
}
