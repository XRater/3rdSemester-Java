package handlers;

import game.GameConfig;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ModeMenuHandler implements Handler {

    @FXML
    private StackPane gameModeMenu;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        gameModeMenu.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                onBackFromTypeMenu();
            }
        });
    }

    public void onNewMultiGame() {
        GameConfig.setMultiPlayerGame();
        SceneManager.changeScene(SceneManager.SceneEnum.GAME);
    }

    public void onNewSingleGame() {
        SceneManager.changeScene(SceneManager.SceneEnum.BOT_MENU);
    }

    public void onBackFromTypeMenu() {
        SceneManager.changeScene(SceneManager.SceneEnum.MAIN_MENU);
    }
}
