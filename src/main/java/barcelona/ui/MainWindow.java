package barcelona.ui;

import java.io.File;
import java.util.Objects;

import barcelona.Barcelona;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    @FXML
    private Button dirButton;

    private Barcelona barcelona;

    private final Image userImage = new Image(Objects.requireNonNull(this.getClass()
            .getResourceAsStream("/images/user-avatar.png")));
    private final Image barcelonaImage = new Image(Objects.requireNonNull(this.getClass()
            .getResourceAsStream("/images/bot-avatar.png")));

    /**
     * Initialises GUI for chatbot
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        ImageView iconView = new ImageView(new Image("/images/send-icon.png"));
        iconView.setFitHeight(20);
        iconView.setFitWidth(20);
        sendButton.setGraphic(iconView);
        ImageView dirIconView = new ImageView(new Image("/images/dir-icon.png"));
        dirIconView.setFitHeight(30);
        dirIconView.setFitWidth(30);
        dirButton.setGraphic(dirIconView);
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
        if (userInput.getText().isEmpty()) {
            return;
        }
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
        if (reply.equals("Bye. Hope to see you again soon!")) {
            PauseTransition exitTimeout = new PauseTransition(Duration.seconds(1));
            exitTimeout.setOnFinished(event -> {
                Stage stage = (Stage) dialogContainer.getScene().getWindow();
                stage.close();
            });
            exitTimeout.play();
        }
    }

    /**
     * Opens a file chooser to change txt file directory
     */
    @FXML
    private void handleChangeDirectory() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String reply = barcelona.changeDirectory(selectedFile.getPath());
            dialogContainer.getChildren().addAll(
                    DialogBox.getBarcelonaDialog(reply, barcelonaImage)
            );
        } else {
            dialogContainer.getChildren().addAll(
                    DialogBox.getBarcelonaDialog("No file selected", barcelonaImage)
            );
        }
        return;
    }
}
