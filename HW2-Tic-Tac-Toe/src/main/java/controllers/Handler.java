package controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public interface Handler extends Initializable {

    @Override
    default void initialize(final URL location, final ResourceBundle resources) {};

    default void onShow() {};

}
