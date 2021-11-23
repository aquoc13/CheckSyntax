package Client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class Client {
    public static final int PORT = 5000;
    public static final String SERVER_IP = "localhost";
    public static final String BREAK_CONNECT_KEY = "bye";
    public static final String FAIL_CONNECT = "Server closed.";
    public static final String CLOSE_CONNECT = "Client closed connection.";
    private static Socket socket;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void connectServer() throws IOException {
        socket = new Socket(SERVER_IP, PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public static void send(String data) throws IOException, SocketException {
        out.write(data);
        out.newLine();
        out.flush();
    }

    public static String receive() throws IOException, NullPointerException {
        return in.readLine();
    }

    public static void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }


}
