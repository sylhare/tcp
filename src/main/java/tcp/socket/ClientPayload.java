package socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;


public class ClientPayload {

    public String send(byte[] payload) {
        try {
            Socket socket = createSocket();
            OutputStream out = socket.getOutputStream();
            out.write(payload);
            socket.close();
            return "OK";
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return "FAILED";
        }
    }

    protected Socket createSocket() {
        try {
            return new Socket("127.0.0.1", 5555);
        } catch (IOException e) {
            e.printStackTrace();
            return new Socket();
        }
    }
}
