package denz.ui;

import denz.app.Denz;
import denz.exception.DenzException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Controller class for the {@code MainWindow.fxml}.
 * <p>
 * Responsible for providing the logic that connects the UI layout defined in FXML
 * with the underlying application logic in {@link Denz}.
 * <ul>
 *     <li>Manages references to UI elements injected by the FXML loader.</li>
 *     <li>Initializes the chat window with default values.</li>
 *     <li>Handles user input and appends dialog boxes to the container.</li>
 * </ul>
 */
public class MainWindow {
    /** Scrollable pane that wraps the dialog container. */
    @FXML private ScrollPane scrollPane;

    /** Container that holds dialog boxes for both user and Denz. */
    @FXML private VBox dialogContainer;

    /** Text field where the user enters messages. */
    @FXML private TextField userInput;

    /** Button to send the message typed into {@link #userInput}. */
    @FXML private Button sendButton;

    /** Reference to the chatbot logic. Set by {@link #setDenz(Denz)}. */
    private Denz denz;

    /** Avatar image shown for the user’s dialog boxes. */
    private final Image userImage = new Image(getClass().getResourceAsStream("/images/user.png"));

    /** Avatar image shown for Denz’s dialog boxes. */
    private final Image denzImage = new Image(getClass().getResourceAsStream("/images/denz.png"));

    /**
     * Called by {@link denz.ui.Main} after FXML has been loaded.
     * <p>
     * Stores the given {@link Denz} instance and shows a greeting message in the dialog container.
     *
     * @param d chatbot instance to use for generating responses
     */
    public void setDenz(Denz d) {
        this.denz = d;
        dialogContainer.getChildren().add(
                DialogBox.getDenzDialog("Hello, I'm your dawg Denz\n" + "What can I do for you?\n", denzImage)
        );
    }

    /**
     * Initializes UI behavior after all {@code @FXML} fields have been injected.
     * <p>
     * Configures auto-scrolling, layout padding, and disables the send button
     * when the input field is empty.
     */
    @FXML
    private void initialize() {
        scrollPane.setFitToWidth(true);
        dialogContainer.setPadding(new Insets(10));
        // auto-scroll to bottom when content grows
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Handles user input events triggered by pressing the Send button
     * or hitting Enter inside the input field.
     * <p>
     * <ol>
     *     <li>Reads the user’s input string.</li>
     *     <li>Delegates to {@link Denz#getResponse(String)} to generate a reply.</li>
     *     <li>Adds dialog boxes for both the user’s input and Denz’s reply.</li>
     *     <li>Clears the input field.</li>
     * </ol>
     * If an error occurs in processing, a fallback error message is shown instead.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.isBlank() || denz == null) {
            return;
        }

        String response;
        try {
            response = denz.getResponse(input);
        } catch (DenzException e) {
            response = e.getMessage();
        } catch (Exception e) {
            response = "Something went wrong.";
        }

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDenzDialog(response, denzImage)
        );
        userInput.clear();
    }
}
