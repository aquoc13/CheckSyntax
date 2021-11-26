package Client;

import Security.AES_Encryptor;
import Security.ClientKeyGenerator;
import Security.RSA_Encryptor;
import Server.ServerDataPacket;

import java.io.*;
import java.net.Socket;

public class Client {
    public static String UID;
    public static String secretKey;
    public static final int PORT = 5000;
    public static final String SERVER_IP = "localhost";
    public static final String[] supportedLanguage = new String[] { "java", "python3", "csharp", "cpp" };
    public static final String versionIndex = "3";
    public static final String BREAK_CONNECT_KEY = "bye";
    public static final String SUCCESS_CONNECT = "Connected.";
    public static final String FAIL_CONNECT = "Server closed.";
    private static Socket socket = null;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void connectServer() throws IOException {
        socket = new Socket(SERVER_IP, PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        Client.send("");
        String serverPublicKey = Client.receive();
        secretKey = ClientKeyGenerator.create();
        String encryptedKey = RSA_Encryptor.encrypt(secretKey, serverPublicKey);
        Client.send(encryptedKey);
    }

    public static boolean checkConnection() {
        return socket != null;
    }

    public static void send(String data) throws IOException {
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

    /**
     * Hàm dùng để xử lý dữ liệu để gửi cho client
     * @param language ngôn ngữ lập trình
     * @param versionIndex phiên bản của ngôn ngữ lập trình mặc định 3
     * @param stdin đầu vào do người dùng nhập
     * @param script source code
     * @return String - dữ liệu đã qua xử lý và mã hóa
     */
    public static String requestHandle(String language, String versionIndex, String stdin, String script) {
        ClientDataPacket clientPacket = new ClientDataPacket(language,versionIndex,stdin,script);
        System.out.println("Created client data packet:\n" + clientPacket.pack());
        return AES_Encryptor.encrypt(clientPacket.pack(), secretKey);
    }

    /**
     * Hàm dùng để xử lý dữ liệu từ Server gửi tới
     * @param data dữ liệu trừ server đã bị mã hóa
     * @return ServerDataPacket - Gói dữ liệu Server
     */
    public static ServerDataPacket responseHandle(String data) {
        return ServerDataPacket.unpack(AES_Encryptor.decrypt(data, secretKey));
    }
}
