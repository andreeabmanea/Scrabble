package client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class ControlPane extends Pane {
    public VBox root;
    public Button submitButton;
    public Button shuffleButton;

    public VBox getRoot() {
        return root;
    }

    public ControlPane() {
        root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20, 10, 20, 10));
        root.setSpacing(30);

        submitButton = new Button("Submit");
        submitButton.setPadding(new Insets(10, 10, 10, 10));
        shuffleButton = new Button("Shuffle");
        shuffleButton.setPadding(new Insets(10, 10, 10, 10));

        root.getChildren().addAll(submitButton, shuffleButton);
    }
}
