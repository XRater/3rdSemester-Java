package handlers;

import game.GameConfig;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handler to work with MainMenu buttons.
 */
public class BotMenuHandler implements Handler {

    @FXML
    private StackPane botModeMenu;

    /**
     * The method sets exit listener for escape button.
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        botModeMenu.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                onBackToModeMenu();
            }
        });
    }

    /**
     * The method initializes new single-player game with an easy bot.
     */
    @FXML
    private void onEasyBot() {
        GameConfig.setSinglePlayerEasyGame();
        SceneManager.changeScene(SceneManager.SceneEnum.GAME);
    }

    /**
     * The method initializes new single-player game with a hard bot.
     */
    @FXML
    private void onHardBot() {
        GameConfig.setSinglePlayerHardGame();
        SceneManager.changeScene(SceneManager.SceneEnum.GAME);
    }

    /**
     * The method changes scene to the ModeMenu scene.
     */
    @FXML
    private void onBackToModeMenu() {
        SceneManager.changeScene(SceneManager.SceneEnum.MODE_MENU);
    }
}
