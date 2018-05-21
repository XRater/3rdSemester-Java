package handlers;

import game.GameConfig;
import game.GameController;
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

    private GameController gameController;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        setFieldListeners();
        gameController = new GameController(initView());

        gameScreen.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                onExit();
            }
        });
    }

    @Override
    public void onShow() {
        if (!gameController.isActiveSession()) {
            gameController.startNewSession(GameConfig.getOptions());
        }
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
                stackPane.setOnMouseReleased(event -> {
                    if (gameController.isGameInProgress()) {
                        gameController.turn(x, y);
                    } else {
                        onRestart();
                    }
                });
            }
        }
    }

    @FXML
    private void onRestart() {
        gameController.startNewGame(GameConfig.getOptions());
    }

    @FXML
    private void onExit() {
        gameController.closeSession();
        SceneManager.changeScene(SceneManager.SceneEnum.MAIN_MENU);
    }

    @FXML
    private void onStats() {
        SceneManager.changeScene(SceneManager.SceneEnum.STATS);
        ((StatsHandler) SceneManager.getScene(SceneManager.SceneEnum.STATS)).
                setStatistics(gameController.getStatistics());
    }
}
