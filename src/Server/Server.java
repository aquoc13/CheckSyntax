package Server;

import Client.ClientDataPacket;
import Security.AES_Encryptor;
import Security.ServerKeyGenerator;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;

public class Server {
    public static final int PORT = 5000;
    public static final String BREAK_CONNECT_KEY = "bye";
    private static ServerSocket serverSocket;
    public static KeyPair keyPair;

    public static void open() throws IOException {
        serverSocket = new ServerSocket(PORT);
        keyPair = ServerKeyGenerator.create();
    }

    public static Socket waitClient() throws IOException {
        return serverSocket.accept();
    }

    public static void close() throws IOException {
        serverSocket.close();
    }

    /**
     * Hàm dùng để xử lý dữ liệu từ Client gửi tới
     * @param data dữ liệu từ client đã bị mã hóa
     * @return ClientDataPacket - Gói dữ liệu Client
     */
    public static ClientDataPacket requestHandle(String data, String secretKey) {
        return ClientDataPacket.unpack(AES_Encryptor.decrypt(data, secretKey));
    }

    /**
     * Hàm dùng để xử lý dữ liệu trả về cho client
     * @param dataPacket Gói dự liệu client
     * @return String - dữ liệu đã qua xử lý
     */
    public static String responseHandle(ClientDataPacket dataPacket, String secretKey) {
        ServerDataPacket serverPacket = new ServerDataPacket(
                "Demo run - "
                        + dataPacket.getLanguage()
                        + " version: "+ dataPacket.getVersionIndex()
                        + " - input: "
                        + dataPacket.getStdin(),
                dataPacket.getScript(),
                dataPacket.toString(),
                "000",
                "000",
                "0ms");
        return AES_Encryptor.encrypt(serverPacket.pack(), secretKey);
    }
}
