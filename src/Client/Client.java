package Client;

import Security.AES_Encryptor;
import Security.ClientKeyGenerator;
import Server.ServerDataPacket;
import Services.FileHandler;
import Services.StringUtils;
import org.openeuler.com.sun.net.ssl.internal.ssl.Provider;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.security.Security;
import java.time.LocalDateTime;
import java.util.StringTokenizer;

public class Client {
    public static String UID;
    public static String secretKey;
    private static Socket socket;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static SSLSocket sslSocket;
    private static SSLSocketFactory sslsocketfactory;
    private static DataOutputStream outSSL;

    public static final int MAIN_PORT = 5000;
    public static final int VERIFY_PORT = 5005;
    public static final String SERVER_IP = "localhost";
    public static final String TRUST_STORE_NAME = "myTrustStore.jts";
    public static final String FILE_CONFIG_NAME = "config";
    public static final String CLIENT_SIDE_PATH = "workspace/Client.Side/";
    public static final String TRUST_STORE_PASSWORD = "checksyntax";
    public static final boolean SSL_DEBUG_ENABLE = false;
    public static final String[] supportedLanguage = new String[] { "java", "python3", "csharp", "cpp" };
    public static final String versionIndex = "3";
    public static final String BREAK_CONNECT_KEY = "bye";
    public static final String SUCCESS_CONNECT = "Connected.";
    public static final String FAIL_CONNECT = "Server closed.";

    public static void config() {
        try {
            String config = FileHandler.read(CLIENT_SIDE_PATH + FILE_CONFIG_NAME);
            StringTokenizer tokenizer = new StringTokenizer(config,"|",false);
            UID = tokenizer.nextToken();
            secretKey = tokenizer.nextToken();
            String hash = tokenizer.nextToken();
            if (!StringUtils.applySha256(UID,secretKey).equals(hash))
                throw new Exception();
            System.out.println("ClientID: " + UID);
            System.out.println("Secret key: " + secretKey);
        } catch (Exception ignored) {
            UID = "new";
            secretKey = "new";
        }
    }

    public static void create() {
        secretKey = ClientKeyGenerator.create();
        String hash = StringUtils.applySha256(UID,secretKey);
        String config = UID + "|" + secretKey  + "|" + hash + "|" + LocalDateTime.now();
        FileHandler.write(CLIENT_SIDE_PATH + FILE_CONFIG_NAME, config);
        System.out.println("new ClientID: " + UID);
        System.out.println("new Secret key: " + secretKey);
    }

    private static void addProvider(String trustStore, String password) {
        /*Adding the JSSE (Java Secure Socket Extension) provider which provides SSL and TLS protocols
        and includes functionality for data encryption, server authentication, message integrity,
        and optional client authentication.*/
        Security.addProvider(new Provider());
        //specifing the trustStore file which contains the certificate & public of the server
        System.setProperty("javax.net.ssl.trustStore", trustStore);
        //specifing the password of the trustStore file
        System.setProperty("javax.net.ssl.trustStorePassword", password);
        //This optional and it is just to show the dump of the details of the handshake process
        if (SSL_DEBUG_ENABLE)
            System.setProperty("javax.net.debug","all");
    }

    public static void connectServer() throws IOException {
        socket = new Socket(SERVER_IP, MAIN_PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        boolean isVerified = false;
        boolean isRegister = false;
        do {
            send(UID);
            String verify = receive();
            if (verify.equals("UIDVerified")) {
                System.out.println(verify + ": " + UID + " - Key: " + secretKey);
                isVerified = true;
            }
            else if (verify.equals("UIDExpired") && !isRegister) {
                if (!UID.equals("new"))
                    System.out.println(verify + ": " + UID + " - Key: " + secretKey);

                addProvider(CLIENT_SIDE_PATH + TRUST_STORE_NAME, TRUST_STORE_PASSWORD);
                //SSLSSocketFactory establishes the ssl context and creates SSLSocket
                sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
                //Create SSLSocket using SSLServerFactory already established ssl context and connect to server
                sslSocket = (SSLSocket) sslsocketfactory.createSocket(SERVER_IP, VERIFY_PORT);
                outSSL = new DataOutputStream(sslSocket.getOutputStream());

                create();
                verify();
                isRegister = true;
                System.out.println("Sent " + UID + "|" + secretKey + " to server.");
            }
        } while (!isVerified);
    }

    public static boolean checkConnection() {
        return socket != null;
    }

    private static void verify() throws IOException {
        outSSL.writeUTF(UID + "|" + secretKey);
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
        FileHandler.write(CLIENT_SIDE_PATH + FILE_CONFIG_NAME, "");
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
