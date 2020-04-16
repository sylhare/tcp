package bytes;

// A Java program for a Server

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
    //initialize socket and input stream
    private Socket socket;
    private ServerSocket server;
    private InputStream in;
    private OutputStream out;

    // constructor with port
    private Server(int port) {
        // starts server and waits for a connection
        try {
            start(port);
            connectClientSocket();
            byte[] result = receiveDataFromClient();
            echoingBackToClient(result);
            close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        new EchoByteServer(5000);
    }

    private void close() throws IOException {
        // close connection
        this.socket.close();
        this.in.close();
    }

    private void echoingBackToClient(byte[] result) throws IOException {
        //echoing back to client
        this.out.write(result);
        System.out.println("Closing connection");
    }

    private byte[] receiveDataFromClient() throws IOException {
        // Receiving data from client
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        baos.write(buffer, 0, in.read(buffer));

        byte[] result = baos.toByteArray();

        String res = Arrays.toString(result);
        System.out.println("Recieved from client : " + res);
        return result;
    }

    private void connectClientSocket() throws IOException {
        this.socket = server.accept();
        // takes input from the client socket
        this.in = new DataInputStream(socket.getInputStream());
        //writes on client socket
        this.out = new DataOutputStream(socket.getOutputStream());
        System.out.println("Client accepted");
    }

    private void start(int port) throws IOException {
        server = new ServerSocket(port);
        System.out.println("Server started");
        System.out.println("Waiting for a client ...");
    }
}
