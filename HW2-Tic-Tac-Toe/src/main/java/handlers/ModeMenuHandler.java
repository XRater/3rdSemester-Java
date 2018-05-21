package handlers;

import game.GameConfig;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handler to work with ModeMenu buttons.
 */
public class ModeMenuHandler implements Handler {

    @FXML
    private StackPane gameModeMenu;

    /**
     * The method sets exit listener for escape button.
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        gameModeMenu.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                onBackToMainMenu();
            }
        });
    }

    /**
     * The method initializes new multi-player game.
     */
    @FXML
    private void onNewMultiGame() {
        GameConfig.setMultiPlayerGame();
        SceneManager.changeScene(SceneManager.SceneEnum.GAME);
    }

    /**
     * The method changes scene to the BotLevelMenu scene.
     */
    @FXML
    private void onNewSingleGame() {
        SceneManager.changeScene(SceneManager.SceneEnum.BOT_MENU);
    }

    /**
     * The method changes scene to the MainMenu scene.
     */
    @FXML
    private void onBackToMainMenu() {
        SceneManager.changeScene(SceneManager.SceneEnum.MAIN_MENU);
    }
}
