package fr.kaice.tools.exeption;

/**
 * This exception must be throw when a {@linkplain fr.kaice.model.member.Member Member} try to be registered with a used
 * membership number (id)
 *
 * @author Raphaël Merkling
 * @version 1.0
 */
public class AlreadyUsedIdException extends RuntimeException {
    
    /**
     * Create a new {@link AlreadyUsedIdException}.
     *
     * @param arg0 {@link String} - The detail message. The message should contains the incriminate membership number
     *             (id).
     */
    public AlreadyUsedIdException(String arg0) {
        super(arg0);
    }
}
