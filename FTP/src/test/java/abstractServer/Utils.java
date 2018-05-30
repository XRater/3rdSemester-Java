package abstractServer;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Utils {

    public static final String sourceDir = "tempDir";

    @NotNull
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

    public static void clear() {
        deleteFile(sourceDir);
    }

    public static String getFileName(final String name) {
        return sourceDir + File.separator + name;
    }

    @NotNull
    public static File createTemporaryFile(final String proxy, final String... lines) throws IOException {
        final File file = createFile(proxy, lines);
        file.deleteOnExit();
        return file;
    }

    @SafeVarargs
    public static <T> List<T> list(final T... values) {
        return Arrays.asList(values);
    }

    public static Map<String, Boolean> map(final Object... elements) {
        final Map<String, Boolean> result = new TreeMap<>();
        for (int i = 0; i < elements.length; i++) {
            result.put((String) elements[i], (Boolean) elements[++i]);
        }
        return result;
    }
}