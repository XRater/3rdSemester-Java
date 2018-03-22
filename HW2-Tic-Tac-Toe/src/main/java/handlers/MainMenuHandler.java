package handlers;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handler to work with MainMenu buttons.
 */
public class MainMenuHandler implements Handler {

    @FXML public StackPane mainMenu;

    /**
     * The method sets exit listener for escape button.
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        mainMenu.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                onExit();
            }
        });
    }

    /**
     * The method changes scene to the ModeMenu scene.
     */
    public void onNewGame() {
        SceneManager.changeScene(SceneManager.SceneEnum.MODE_MENU);
    }

    /**
     * The method closes an application.
     */
    public void onExit() {
        System.exit(0);
    }
}
