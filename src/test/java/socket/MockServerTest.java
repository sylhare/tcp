package socket;

import mock.TcpSocketConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

public class MockServerTest {

    @Mock
    private ServerSocket mockServerSocket;
    @Mock
    private Socket mockClientSocket;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);

        try {
            PipedOutputStream oStream = new PipedOutputStream();
            PipedInputStream iStream = new PipedInputStream(oStream);
            when(mockClientSocket.getOutputStream()).thenReturn(oStream);
            when(mockClientSocket.getInputStream()).thenReturn(iStream);
            when(mockClientSocket.isClosed()).thenReturn(false);
            when(mockServerSocket.accept()).thenReturn(mockClientSocket);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testConnection() {
        TcpSocketConnection connection = new TcpSocketConnection(mockServerSocket); // Add mock Socket
        try {
            connection.listen(); // Sends a "Hello, world!" to a connected client
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(mockClientSocket.getInputStream(), StandardCharsets.UTF_8));
            String receivedMessage = reader.readLine();
            assertEquals("Hello, world!", receivedMessage);
            mockClientSocket.close();
            connection.closeSocket();
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
