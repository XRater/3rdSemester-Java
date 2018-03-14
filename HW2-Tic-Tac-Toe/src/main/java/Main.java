import controllers.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) throws IOException {
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(400);
        primaryStage.setTitle("Tic-tac-toe");

        Controller.initialize(primaryStage);
        Controller.changeScene(Controller.SceneEnum.GAME);
//        Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
//        Scene scene = new Scene(root, 300, 400);
//        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
