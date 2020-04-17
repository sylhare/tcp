package tcp.greet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GreetTest {

    private static GreetServer server;

    @BeforeAll
    static void setup() {
        new Thread(() -> {
            server = new GreetServer();
            try {
                server.start(6666);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.stop();
    }

    @Test
    void serverRespondsWhenStartedTest() throws IOException {
        GreetClient client = new GreetClient();
        client.startConnection("127.0.0.1", 6666);
        String response = client.sendMessage("hello server");
        assertEquals("hello client", response);
    }
}
