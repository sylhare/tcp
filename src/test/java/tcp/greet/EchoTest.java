package tcp.greet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import tcp.multibind.EchoMultiServer;

class EchoTest {

    private static GreetClient client;
    private static EchoMultiServer multiServer;
    private static EchoServer server;

    @BeforeAll
    static void setup() throws IOException {
        new Thread(() -> {
            server = new EchoServer();
            try {
                server.start(4441);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            multiServer = new EchoMultiServer();
            try {
                multiServer.start(1555);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        client = new GreetClient();
        client.startConnection("127.0.0.1", 4441);
    }

    @AfterAll
    static void tearDown() throws IOException {
        client.stopConnection();
        server.stop();
        multiServer.stop();
    }

    @Test
    void serverEchoMessageTest() throws IOException {
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        String resp3 = client.sendMessage("!");
        String resp4 = client.sendMessage(".");

        assertEquals("hello", resp1);
        assertEquals("world", resp2);
        assertEquals("!", resp3);
        assertEquals("good bye", resp4);
    }

    @Test
    void multiEchoServerRespondsToClientOne() throws IOException {
        GreetClient client1 = new GreetClient();
        client1.startConnection("127.0.0.1", 1555);
        String msg1 = client1.sendMessage("hello");
        String msg2 = client1.sendMessage("world");
        String terminate = client1.sendMessage(".");

        assertEquals(msg1, "hello");
        assertEquals(msg2, "world");
        assertEquals(terminate, "bye");
    }

    @Test
    public void multiEchoServerRespondsRespondstoClientTwo() throws IOException {
        GreetClient client2 = new GreetClient();
        client2.startConnection("127.0.0.1", 1555);
        String msg1 = client2.sendMessage("hello");
        String msg2 = client2.sendMessage("world");
        String terminate = client2.sendMessage(".");

        assertEquals(msg1, "hello");
        assertEquals(msg2, "world");
        assertEquals(terminate, "bye");
    }
}
