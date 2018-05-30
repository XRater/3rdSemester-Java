import abstractServer.Utils;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


class FTPClientTest {

    @Test
    void testListEmpty() throws IOException {
        Utils.clear();
        testListWithContent(Utils.map());
        testListWithContent(Utils.map("file1", false));
        testListWithContent(Utils.map("file1", true));
        testListWithContent(Utils.map("file1", true, "file2", false));
    }

    private void testListWithContent(@NotNull final Map<String, Boolean> files) throws IOException {
        final FTPServer server = FTPServer.init();
        final FTPClient client = new FTPClient("localhost", FTPServer.DEFAULT_PORT);
        try {
            Thread.sleep(100);
        } catch (@NotNull final InterruptedException e) {
            // do nothing
        }

        for (final Map.Entry<String, Boolean> entry : files.entrySet()) {
            if (entry.getValue()) {
                Utils.createFile(entry.getKey() + File.separator + "dummy");
            } else {
                Utils.createFile(entry.getKey());
            }
        }

        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        final PrintStream old = System.out;
        System.setOut(new PrintStream(os));
        client.list(Utils.sourceDir);

        final StringBuilder answer = new StringBuilder("Total number of files: " + files.size() + "\n");
        for (final Map.Entry<String, Boolean> entry : files.entrySet()) {
            final String isFolderString = entry.getValue() ? "is a folder." : "is not a folder.";
            answer.append("\"").append(entry.getKey())
                    .append("\" ").append(isFolderString).append('\n');
        }
        assertThat(Arrays.asList(os.toString().split("\n")),
                containsInAnyOrder(answer.toString().split("\n")));

        System.setOut(old);
        Utils.clear();
        server.shutDown();
    }

    @Test
    void testGet() throws IOException {
         Utils.clear();
         testGetWithContent();
         testGetWithContent("hello");
         testGetWithContent("hello", "one");
         testGetWithContent("hello", "hello");
    }

    private void testGetWithContent(final String... content) throws IOException {
        final FTPServer server = FTPServer.init();
        final FTPClient client = new FTPClient("localhost", FTPServer.DEFAULT_PORT);

        Utils.createFile("file", content);

        client.get(Utils.getFileName("file"), "tmp.txt");

        FileUtils.contentEquals(new File(Utils.getFileName("file")), new File("tmp.txt"));

        Utils.deleteFile("tmp.txt");
        Utils.clear();
        server.shutDown();
    }

}