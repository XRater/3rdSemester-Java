package handlers;

import game.Statistics;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ResourceBundle;

public class StatsHandler implements Handler {

    @FXML
    private StackPane stats;
    @FXML
    private Button XWins;
    @FXML
    private Button OWins;
    @FXML
    private Button Draws;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        stats.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                onBackToGame();
            }
        });
    }

    void setStatistics(@NotNull final Statistics statistics) {
        XWins.setText("X won " + statistics.getXWins() + " times.");
        OWins.setText("O won " + statistics.getOWins() + " times.");
        Draws.setText("Draw was " + statistics.getDraws() + " times.");
    }

    @FXML
    private void onBackToGame() {
        SceneManager.changeScene(SceneManager.SceneEnum.GAME);
    }
}