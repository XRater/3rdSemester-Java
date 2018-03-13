package controllers;

import controllers.Controller;
import javafx.scene.input.MouseEvent;

public class MenuController extends Controller {

    public void initialize() {
        System.out.println("Init menu");
    }

    public void onNewGame() {
        System.out.println("Menu");
        changeScene(Controller.SceneEnum.GAME);
    }
}
