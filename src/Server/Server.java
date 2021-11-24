package Server;

import Client.ClientDataPacket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 5000;
    public static final String BREAK_CONNECT_KEY = "bye";
    private static ServerSocket serverSocket;

    public static void open() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }

    public static Socket waitClient() throws IOException {
        return serverSocket.accept();
    }

    public static void close() throws IOException {
        serverSocket.close();
    }

    /**
     * Hàm dùng để xử lý dữ liệu từ Client gửi tới
     * @param data dữ liệu đầu vào kiểu String
     * @return String - dữ liệu đã qua xử lý
     */
    public static String responseHandle(String data) {
        ClientDataPacket clientPacket = ClientDataPacket.unpack(data);
        ServerDataPacket serverPacket = new ServerDataPacket(
                "Demo run - "
                        + clientPacket.getLanguage()
                        + clientPacket.getVersionIndex()
                        + " - input: "
                        + clientPacket.getStdin(),
                clientPacket.getScript(),
                clientPacket.toString(),
                "000",
                "000",
                "0ms");
        return serverPacket.pack();
    }
}
