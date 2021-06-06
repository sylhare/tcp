package tcp.socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ClientPayloadTest {

    @Mock
    private Socket socket;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSimplePayload() throws IOException {
        byte[] emptyPayload = new byte[1001];
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        "message".chars().forEach(byteArrayOutputStream::write);
        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);

        ClientPayload client = new ClientPayload() {
            @Override
            protected Socket createSocket() {
                return socket;
            }
        };

        assertEquals("OK", client.send(emptyPayload));
        verify(socket).close();
    }
}
