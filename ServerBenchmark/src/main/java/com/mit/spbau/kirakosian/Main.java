package com.mit.spbau.kirakosian;

import com.mit.spbau.kirakosian.servers.Server;
import com.mit.spbau.kirakosian.servers.Servers;
import com.mit.spbau.kirakosian.ui.SettingsWindow;
import com.mit.spbau.kirakosian.ui.Window;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    private static Server server;

    public static void main(final String[] args) throws IOException {
//        server = Servers.init(Servers.ServerType.Blocking);

//        try {
//            test(Options1.getOptions());
//        } catch (final FileNotFoundException e) {
//            System.out.println("No such file");
//        }

//        server.shutDown();
        Window window = new SettingsWindow();
        window.setVisible(true);
    }

//    private static void test(Options1 options) {
//
//    }

}
