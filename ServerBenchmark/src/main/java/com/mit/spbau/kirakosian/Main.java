package com.mit.spbau.kirakosian;

import com.mit.spbau.kirakosian.ui.SceneManager;
import com.mit.spbau.kirakosian.ui.Window;

import javax.swing.*;

public class Main {

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final Window window = new Window();
            SceneManager.init(window);
            window.setVisible(true);
        });
    }

}
