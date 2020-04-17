package tcp.bytes;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Client {
    private Socket socket;
    private OutputStream out;
    private InputStream in;

    private Client(String address, int port) {
        start(address, port);
        handle();
        stop();
    }

    public static void main(String[] args) {
        new Client("127.0.0.1", 5000);
    }

    private void handle() {
        try {
            byte[] arr = {(byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x10, (byte) 0x00, (byte) 0x01, (byte) 0x00,
                    (byte) 0x1F, (byte) 0x60, (byte) 0x1D, (byte) 0xA1, (byte) 0x09, (byte) 0x06, (byte) 0x07,
                    (byte) 0x60, (byte) 0x85, (byte) 0x74, (byte) 0x05, (byte) 0x08, (byte) 0x01, (byte) 0x01,
                    (byte) 0xBE, (byte) 0x10, (byte) 0x04, (byte) 0x0E, (byte) 0x01, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x06, (byte) 0x5F, (byte) 0x1F, (byte) 0x04, (byte) 0x00, (byte) 0x00,
                    (byte) 0x18, (byte) 0x1D, (byte) 0xFF, (byte) 0xFF};

            // sending data to server
            out.write(arr);

            String req = Arrays.toString(arr);
            //printing request to console
            System.out.println("Sent to server : " + req);

            // Receiving reply from server
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            baos.write(buffer, 0, in.read(buffer));

            byte[] result = baos.toByteArray();

            String res = Arrays.toString(result);

            // printing reply to console
            System.out.println("Recieved from server : " + res);
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private void stop() {
        // close the connection
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private void start(String address, int port) {
        // establish a connection
        try {
            socket = new Socket(address, port);
            if (socket.isConnected()) {
                System.out.println("Connected");
            }
            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
            //takes input from socket
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}