package handlers;

import game.GameConfig;
import game.model.GameSession;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import view.View;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles game actions.
 */
public class GameHandler implements Handler {

    @FXML private StackPane gameScreen;
    @FXML private GridPane table;

    private GameSession gameSession;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        setFieldListeners();
        gameSession = new GameSession(initView());

        gameScreen.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                onExit();
            }
        });
    }

    @Override
    public void onShow() {
        gameSession.initGame(GameConfig.getOptions());
    }

    private View initView() {
        final StackPane[][] grid = new StackPane[3][3];
        for (final Node node : table.getChildren()) {
            if (node instanceof StackPane) {
                final StackPane stackPane = (StackPane) node;
                final int x = GridPane.getColumnIndex(stackPane);
                final int y = GridPane.getRowIndex(stackPane);
                grid[x][y] = stackPane;
            }
        }
        return new View(grid);
    }

    private void setFieldListeners() {
        for (final Node node : table.getChildren()) {
            if (node instanceof StackPane) {
                final StackPane stackPane = (StackPane) node;
                final int x = GridPane.getColumnIndex(stackPane);
                final int y = GridPane.getRowIndex(stackPane);
                stackPane.setOnMouseReleased(event -> gameSession.turn(x, y));
            }
        }
    }

    @FXML public void onRestart() {
        gameSession.initGame(GameConfig.getOptions());
    }

    @FXML public void onExit() {
        SceneManager.changeScene(SceneManager.SceneEnum.MAIN_MENU);
    }
}
