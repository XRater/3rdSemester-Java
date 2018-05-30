import java.io.IOException;

@SuppressWarnings("unused")
public class FTPApplication {

    private static void init(final String url) throws IOException {
        final FTPWindow window = new FTPWindow(url);
        window.setVisible(true);
    }

}
