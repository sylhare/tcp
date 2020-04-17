package tcp.socket;

import tcp.mock.TcpSocketConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MockServerTest {

    @Mock
    private ServerSocket mockServerSocket;
    @Mock
    private Socket mockClientSocket;

    @BeforeEach
    void setup() throws IOException {
        MockitoAnnotations.initMocks(this);

        PipedOutputStream oStream = new PipedOutputStream();
        PipedInputStream iStream = new PipedInputStream(oStream);
        when(mockClientSocket.getOutputStream()).thenReturn(oStream);
        when(mockClientSocket.getInputStream()).thenReturn(iStream);
        when(mockClientSocket.isClosed()).thenReturn(false);
        when(mockServerSocket.accept()).thenReturn(mockClientSocket);
    }

    @Test
    void testConnection() throws IOException {
        TcpSocketConnection connection = new TcpSocketConnection(mockServerSocket); // Add mock Socket

        connection.listen(); // Sends a "Hello, world!" to a connected client
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(mockClientSocket.getInputStream(), StandardCharsets.UTF_8));
        String receivedMessage = reader.readLine();
        assertEquals("Hello, world!", receivedMessage);
        mockClientSocket.close();
        connection.closeSocket();
    }
}
