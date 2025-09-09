package denz.exception;

/**
 * Exception thrown when there is an error parsing or executing
 * the {@code remind} command.
 * <p>
 * Examples of invalid cases include:
 * <ul>
 *     <li>User provides too many arguments after the {@code remind} keyword.</li>
 *     <li>User specifies a non-integer value as the reminder limit.</li>
 * </ul>
 * This exception extends {@link DenzException}, making it specific
 * to reminder-related errors within the chatbot.
 *
 * @author aldenchua
 * @since 9/9/25
 */
public class RemindException extends DenzException {
  public RemindException(String message) {
    super(message);
  }
}
