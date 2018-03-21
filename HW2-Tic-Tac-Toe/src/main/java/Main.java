import handlers.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(400);
        primaryStage.setTitle("Tic-tac-toe");

        SceneManager.initialize(primaryStage);
        SceneManager.changeScene(SceneManager.SceneEnum.MAIN_MENU);
        primaryStage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
