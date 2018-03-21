package handlers;

import game.GameConfig;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class BotMenuHandler implements Handler {

    public StackPane botModeMenu;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        botModeMenu.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                onBackFromBotMenu();
            }
        });
    }

    public void onEasyBot() {
        GameConfig.setSinglePlayerEasyGame();
        SceneManager.changeScene(SceneManager.SceneEnum.GAME);
    }

    public void onHardBot() {
        GameConfig.setSinglePlayerHardGame();
        SceneManager.changeScene(SceneManager.SceneEnum.GAME);
    }

    public void onBackFromBotMenu() {
        SceneManager.changeScene(SceneManager.SceneEnum.MODE_MENU);
    }
}
