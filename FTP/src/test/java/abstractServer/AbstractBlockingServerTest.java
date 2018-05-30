package abstractServer;

import abstractServer.dummies.DummyClient;
import abstractServer.dummies.DummyException;
import abstractServer.dummies.DummyServer;
import abstractServer.dummies.DummySession;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.core.Is.is;

class AbstractBlockingServerTest {

    @Test
    void initAndShutDown() throws IOException {
        DummyServer server = DummyServer.init();

        for (int i = 0; i < 5; i++) {
            server.shutDown();
            server = DummyServer.init();
            assertThat(server.anyErrorOccurred(), is(false));
        }

        server.shutDown();
    }

    @Test
    void testMessages() throws IOException {
        final DummyServer server = DummyServer.init();

        for (int i = 0; i < 5; i++) {
            new DummyClient().sendCommands(42, 17, 46, 14, 22, 134);
        }

        try {
            Thread.sleep(1000);
        } catch (@NotNull final InterruptedException e) {
            // just waiting...
        }

        assertThat(server.anyErrorOccurred(), is(false));
        assertThat(server.getErrors(), is(empty()));
        assertThat(server.session().stream()
                        .map(DummySession::lines).collect(Collectors.toList()),
                everyItem(is(Utils.list("42", "17", "46", "14", "22", "134"))));

        assertThat(server.session().stream()
                .map(DummySession::getErrors).collect(Collectors.toList()),
                everyItem(is(empty())));


        server.shutDown();
    }

    @Test
    void testCloseSession() throws IOException {
        final DummyServer server = DummyServer.init();

        for (int i = 0; i < 3; i++) {
            new DummyClient().sendCommands(-2);
        }

        try {
            Thread.sleep(1000);
        } catch (@NotNull final InterruptedException e) {
            // just waiting...
        }

        assertThat(server.anyErrorOccurred(), is(false));
        assertThat(server.getErrors(), is(empty()));
        assertThat(server.session().stream().map(DummySession::getErrors)
                        .collect(Collectors.toList()),
                everyItem(everyItem(is(instanceOf(DummyException.class)))));
        assertThat(server.session().stream().map(DummySession::getErrors)
                        .collect(Collectors.toList()),
                everyItem(iterableWithSize(1)));

        server.shutDown();
    }

    @Test
    void testAnswer() throws IOException {
        final DummyServer server = DummyServer.init();

        assertThat(new DummyClient().sendAnswerIntCommand(), is(42));

        server.shutDown();
    }

}