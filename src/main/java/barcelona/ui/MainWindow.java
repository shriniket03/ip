package barcelona.ui;

import barcelona.Barcelona;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Barcelona barcelona;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user-avatar.png"));
    private Image barcelonaImage = new Image(this.getClass().getResourceAsStream("/images/bot-avatar.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setBarcelona(Barcelona b) {
        barcelona = b;
    }

    /**
     * Creates a dialog box containing user input, and appends it to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String reply = barcelona.getResponse(userInput.getText());
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userInput.getText(), userImage)
        );
        userInput.clear();
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            dialogContainer.getChildren().addAll(
                    DialogBox.getBarcelonaDialog(reply, barcelonaImage)
            );
        });
        pause.play();
    }
}
