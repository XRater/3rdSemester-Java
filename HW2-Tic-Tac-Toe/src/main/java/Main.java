import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static double size = 320;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Scene scene = new Scene(root, 300, 275);

        System.out.println(size);
//        size = primaryStage.getMinHeight();
        size = 320;

//        primaryStage.widthProperty().addListener((ObservableValue<? extends Number> ov, Number t, Number t1) -> {
//            if (t1.doubleValue() < size) {
//                primaryStage.setWidth(size);
//            }
//        });
//        primaryStage.heightProperty().addListener((ObservableValue<? extends Number> ov, Number t, Number t1) -> {
//            if (t1.doubleValue() < size) {
//                primaryStage.setHeight(size);
//            }
//        });

//        scene.widthProperty().addListener(new ChangeListener<Number>() {
//            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
//                System.out.println("Width: " + newSceneWidth);
//            }
//        });
//        scene.heightProperty().addListener(new ChangeListener<Number>() {
//            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
//                System.out.println("Height: " + newSceneHeight);
//            }
//        });

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class VisualSettings {

        public final static double PADDING = 10;
    }
}
