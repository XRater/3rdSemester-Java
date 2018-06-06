package com.mit.spbau.kirakosian;

import com.mit.spbau.kirakosian.servers.Server;
import com.mit.spbau.kirakosian.servers.Servers;
import com.mit.spbau.kirakosian.ui.SettingsWindow;
import com.mit.spbau.kirakosian.ui.Window;

import javax.swing.*;

public class Main {

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final Window window = new SettingsWindow();
            window.setVisible(true);
        });
    }

}
