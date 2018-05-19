package handlers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Interface to handle different scenes.
 *
 * Initialize method suspected to be called only one time for every scene.
 *
 * If you want to do action on every scene call use onShow method.
 */
public interface Handler extends Initializable {

    /**
     * Method to initialize scene. Derived from Initializable interface.
     *
     * The method is called, when attached to the handler scene was shown
     * on the first time.
     *
     * The method does nothing by default.
     */
    @Override
    default void initialize(final URL location, final ResourceBundle resources) {}

    /**
     * This method is suspected to be called when scene shows up. That means, that
     * method might be used if you want to do some initialization on every scene show up.
     *
     * The method does nothing by default.
     */
    default void onShow() {}
}
