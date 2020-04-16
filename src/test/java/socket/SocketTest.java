package socket;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import static org.junit.Assert.assertEquals;

/**
 * http://rememberjava.com/socket/2017/02/21/socket_client_server.html
 * Simple client / server Socket tests, including a Buffered PrintWriter which
 * has to be flushed.
 */
public class SocketTest {

    private static final int PORT = 8887;

    private OutputStream serverOut;
    private InputStream serverIn;

    /**
     * Shared lock between the "client" and "server" code, to make the test case
     * synchronous.
     */
    private Semaphore lock = new Semaphore(0);

    /**
     * Tests server and client side sockets in one flow. A lock object is used for
     * synchronous between the two sides.
     */
    @Test
    public void testClientServer() throws IOException, InterruptedException {
        ServerSocket server = new ServerSocket(PORT);
        listen(server);

        Socket client = new Socket("localhost", PORT);
        OutputStream clientOut = client.getOutputStream();
        InputStream clientIn = client.getInputStream();

        System.out.println("Waiting for lock");
        lock.acquire();
        System.out.println("Acquired lock");


        write(clientOut, "Hi");
        Thread.sleep(200);
        assertRead(serverIn, "Hi");

        write(serverOut, "Hello");
        Thread.sleep(200);
        assertRead(clientIn, "Hello");

        printWrite(clientOut, "Test printWrite");
        Thread.sleep(200);
        assertRead(serverIn, "Test printWrite");

        printWrite(serverOut, "Test printWrite again");
        Thread.sleep(200);
        assertRead(clientIn, "Test printWrite again");

        client.close();
        server.close();
    }

    /**
     * Writes to an OutputStream. Used for both server and client output streams.
     */
    private void write(OutputStream out, String str) throws IOException {
        out.write(str.getBytes());
        out.flush();
    }

    /**
     * Writes to an OutputStream. Used for both server and client output streams.
     */
    private void printWrite(OutputStream out, String str) throws IOException {
        PrintWriter pw = new PrintWriter(out);
        pw.print(str);
        pw.flush();
    }

    /**
     * Reads from an InputStream. Used for both server and client input streams.
     */
    private void assertRead(InputStream in, String expected) throws IOException {
        assertEquals("Too few bytes available for reading: ", expected.length(), in.available());

        byte[] buf = new byte[expected.length()];
        in.read(buf);
        assertEquals(expected, new String(buf));
    }

    /**
     * Listens for and accepts one incoming request server side on a separate
     * thread. When a request is received, grabs its IO streams and "signals" to
     * the client side above through the shared lock object.
     */
    private void listen(ServerSocket server) {
        new Thread(() -> {
            try {
                Socket socket = server.accept();
                System.out.println("Incoming connection: " + socket);

                serverOut = socket.getOutputStream();
                serverIn = socket.getInputStream();

                lock.release();
                System.out.println("Released lock");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}