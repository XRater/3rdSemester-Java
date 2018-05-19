package com.spbau.mit.kirakosian.threadPool;

import org.jetbrains.annotations.NotNull;

public class Utils {

    public static void sleep(final int time) {
        try {
            Thread.sleep(time);
        } catch (@NotNull final InterruptedException e) {
            e.printStackTrace();
        }
    }

}
