package com.spbau.mit.kirakosian;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("WeakerAccess")
public class Utils {

    public static final String sourceDir = "tempDir";

    static {
        try {
            createTemporaryFile(sourceDir);
        } catch (IOException e) {
            // should never happen
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createFile(final String name, @NotNull final String... lines) throws IOException {
        final File file = new File(getFileName(name));
        new File(file.getParent()).mkdirs();
        file.createNewFile();

        try (final PrintWriter writer = new PrintWriter(file)) {
            for (final String line : lines) {
                writer.append(line).append('\n');
            }
        }
        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteFile(@NotNull final String name) {
        final File file = new File(name);
        if (!file.isDirectory()) {
            file.delete();
        } else {
            //noinspection ConstantConditions
            for (final File inner : file.listFiles()) {
                deleteFile(inner.getAbsolutePath());
            }
            file.delete();
        }
    }

    private static String getFileName(final String name) {
        return sourceDir + File.separator + name;
    }

    public static File createTemporaryFile(final String proxy) throws IOException {
        final File file = createFile(proxy);
        file.deleteOnExit();
        return file;
    }
}