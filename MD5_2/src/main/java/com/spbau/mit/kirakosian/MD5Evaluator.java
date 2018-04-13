package com.spbau.mit.kirakosian;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * The class is supposed to evaluate MD5 hash of the given by path file.
 *
 * For directories will work recursively.
 */
@SuppressWarnings("WeakerAccess")
public class MD5Evaluator {

    private static final int BUFFER_SIZE = 1024;
    private static final byte[] buffer = new byte[BUFFER_SIZE];

    /**
     * Evaluates hash in single thread.
     *
     * @param path path to the file
     * @throws IOException if any error occured
     */
    public static byte[] evaluateMD5WithOneThread(@NotNull final Path path) throws IOException {
        final MessageDigest md = createMD5MessageDigest();

        if (Files.isDirectory(path)) {
            md.update(path.getFileName().toString().getBytes());
            for (final Path innerPath : Files.walk(path).sorted().collect(Collectors.toList())) {
                if (innerPath.equals(path)) {
                    continue;
                }
                md.update(evaluateMD5WithOneThread(innerPath));
            }
        } else {
            updateMD5WithFile(md, path);
        }

        return md.digest();
    }

    /**
     * Evaluates hash with many thread (with ForkJoinPool).
     *
     * @param path path to the file
     * @throws IOException if any error occured
     */
    public static byte[] evaluateMD5WithMultiThread(@NotNull final Path path) throws IOException {
        final ForkJoinPool pool = new ForkJoinPool();
        final MD5EvaluatorTask task = new MD5EvaluatorTask(path);

        final byte[] answer = pool.invoke(task);
        final IOException exception = task.getExecutionException();
        if (exception != null) {
            throw exception;
        }
        return answer;
    }

    private static void updateMD5WithFile(@NotNull final MessageDigest md, @NotNull final Path path) throws IOException {
        if (Files.isDirectory(path)) {
            throw new IllegalArgumentException("Illegal path to file " + path.toString());
        }
        try (final DigestInputStream stream = new DigestInputStream(Files.newInputStream(path), md)) {
            //noinspection StatementWithEmptyBody
            while (stream.read(buffer) != -1) {
                // We don't really care about data
            }
        }
    }

    private static MessageDigest createMD5MessageDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (@NotNull final NoSuchAlgorithmException e) {
            //should never happen
            throw new RuntimeException();
        }
    }

    private static class MD5EvaluatorTask extends RecursiveTask<byte[]> {

        private final Path path;
        private IOException exception;

        @SuppressWarnings("WeakerAccess")
        public MD5EvaluatorTask(final Path path) {
            this.path = path;
        }

        @SuppressWarnings("WeakerAccess")
        public IOException getExecutionException() {
            return exception;
        }

        @Override
        public byte[] compute() {
            final MessageDigest md = createMD5MessageDigest();
            if (Files.isDirectory(path)) {
                final ArrayList<MD5EvaluatorTask> innerTasks = new ArrayList<>();

                md.update(path.getFileName().toString().getBytes());
                try {
                    for (final Path innerPath : Files.walk(path).sorted().collect(Collectors.toList())) {
                        if (innerPath.equals(path)) {
                            continue;
                        }
                        final MD5EvaluatorTask newTask = new MD5EvaluatorTask(innerPath);
                        newTask.fork();
                        innerTasks.add(newTask);
                    }
                } catch (@NotNull final IOException e) {
                    exception = e;
                }

                for (final MD5EvaluatorTask task : innerTasks) {
                    task.join();
                }
            } else {
                try {
                    updateMD5WithFile(md, path);
                } catch (@NotNull final IOException e) {
                    exception = e;
                }
            }
            return md.digest();
        }

    }
}
