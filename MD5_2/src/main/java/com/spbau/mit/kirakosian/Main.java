package com.spbau.mit.kirakosian;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    private static final Path path = Paths.get("./src");

    public static void main(final String[] args) throws IOException {

        final long beginOneThread = System.currentTimeMillis();
        MD5Evaluator.evaluateMD5WithOneThread(path);
        final long endOneThread = System.currentTimeMillis();

        final long beginManyThread = System.currentTimeMillis();
        MD5Evaluator.evaluateMD5WithMultiThread(path);
        final long endManyThread = System.currentTimeMillis();

        System.out.println("Time with one thread: " + (endOneThread - beginOneThread));
        System.out.println("Time with many threads: " + (endManyThread - beginManyThread));
    }

}
