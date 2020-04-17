package tcp.mock;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


class InnerListenerClass implements Runnable {
    private final ServerSocket socket;

    public InnerListenerClass(final ServerSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Socket clientSocket = socket.accept();
            OutputStreamWriter writer = new OutputStreamWriter(
                    clientSocket.getOutputStream(), StandardCharsets.UTF_8);
            PrintWriter out = new PrintWriter(writer, true);

            while (!clientSocket.isClosed()) {
                out.println("Hello, world!");
                Thread.sleep(100);
            }
        } catch (InterruptedException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
