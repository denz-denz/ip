package denz.ui;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * DialogBox class represents the UI for seeing your image and text when sending messages
 *
 *
 *
 * @author aldenchua
 * @since 2/9/25
 */
public class DialogBox extends HBox {

    @FXML
    private Label dialog;

    @FXML
    private ImageView displayPicture;

    private final Region spacer = new Region();

    /**
     * Initialises a Dialog box which is seen as a speech box in the UI
     * @param text String to display in box
     * @param img Image to display in box
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/denz/ui/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setMaxWidth(Double.MAX_VALUE);

        dialog.setText(text);
        displayPicture.setImage(img);
        dialog.setWrapText(true);
        dialog.setTextOverrun(OverrunStyle.CLIP); // no "…"
        dialog.setMaxWidth(Double.MAX_VALUE); // let it expand
        dialog.setMinHeight(Region.USE_PREF_SIZE);
        HBox.setHgrow(dialog, Priority.ALWAYS); // use remaining row width
        HBox.setHgrow(spacer, Priority.ALWAYS);
        getChildren().clear();
        getChildren().addAll(spacer, dialog, displayPicture);
        getStyleClass().add("dialog-box");
    }

    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.dialog.getStyleClass().addAll("bubble", "user-bubble");
        return db;
    }

    public static DialogBox getDenzDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.dialog.getStyleClass().addAll("bubble", "denz-bubble");
        db.flip(); // left-align Denz
        return db;
    }
}
