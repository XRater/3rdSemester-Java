package interfaces;

import interfaces.dummies.DummyClient;
import interfaces.dummies.DummyServer;
import interfaces.dummies.DummySession;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.core.Is.is;

public class AbstractBlockingServerTest {

    @Test
    public void initAndShutDown() throws IOException {
        DummyServer server = DummyServer.init();

        for (int i = 0; i < 5; i++) {
            server.shutDown();
            server = DummyServer.init();
            assertThat(server.anyErrorOccurred(), is(false));
        }

        server.shutDown();
    }

    @Test
    public void testMessages() throws IOException {
        final DummyServer server = DummyServer.init();

        for (int i = 0; i < 5; i++) {
            new DummyClient();
        }

        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            // just waiting...
        }

        assertThat(server.anyErrorOccurred(), is(false));
        assertThat(server.getErrors(), is(empty()));
        assertThat(server.session().stream()
                        .map(DummySession::lines).collect(Collectors.toList()),
                everyItem(is(List.of("Hello!"))));

        assertThat(server.session().stream()
                .map(DummySession::getErrors).collect(Collectors.toList()),
                everyItem(is(empty())));

        server.shutDown();
    }


}