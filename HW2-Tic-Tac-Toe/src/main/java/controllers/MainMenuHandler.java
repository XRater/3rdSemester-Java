package controllers;

import game.GameConfig;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuHandler implements Handler {

    @FXML public StackPane mainMenu;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        mainMenu.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                onExit();
            }
        });
    }

    public void onNewGame() {
        SceneManager.changeScene(SceneManager.SceneEnum.MODE_MENU);
    }

    public void onNewSingleGame() {
        GameConfig.setSinglePlayerEasyGame();
        SceneManager.changeScene(SceneManager.SceneEnum.GAME);
    }

    public void onExit() {
        System.exit(0);
    }
}
