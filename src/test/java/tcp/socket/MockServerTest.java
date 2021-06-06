package tcp.socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tcp.mock.TcpSocketConnection;

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
