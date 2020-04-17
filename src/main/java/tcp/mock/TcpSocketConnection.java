package mock;

import java.io.IOException;
import java.net.ServerSocket;

public class TcpSocketConnection {

    private final ServerSocket serverSocket;

    public TcpSocketConnection(final ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public final void listen() {
        new Thread(new InnerListenerClass(serverSocket)).start();
    }

    public void closeSocket() throws IOException {
        serverSocket.close();
    }
}