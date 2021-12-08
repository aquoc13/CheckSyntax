package Client;

import ClientGUI.GUI;
import Security.AES_Encryptor;
import Security.ClientKeyGenerator;
import Server.ServerDataPacket;
import Services.FileHandler;
import Services.StringUtils;
import org.openeuler.com.sun.net.ssl.internal.ssl.Provider;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.security.Security;
import java.time.LocalDateTime;
import java.util.StringTokenizer;
import java.util.UUID;

public class Client {
    public static GUI Frame;
    public static Thread Listener;

    public static String UID;
    public static String secretKey;
    public static Socket socket;
    public static BufferedReader in;
    public static BufferedWriter out;
    public static SSLSocket sslSocket;
    public static SSLSocketFactory sslsocketfactory;
    public static DataInputStream inSSL;
    public static DataOutputStream outSSL;

    public static final int MAIN_PORT = 5000;
    public static final int VERIFY_PORT = 5005;
    public static final String SERVER_IP = "localhost";
    public static final String TRUST_STORE_NAME = "myTrustStore.jts";
    public static final String FILE_CONFIG_NAME = "config";
    public static final String CLIENT_SIDE_PATH = "workspace/Client.Side/";
    public static final String TRUST_STORE_PASSWORD = "checksyntax";
    public static final boolean SSL_DEBUG_ENABLE = false;
    public static final String[] supportedLanguage = new String[] { "java", "python3", "php", "c", "cpp" };
    public static final String BREAK_CONNECT_KEY = "bye";
    public static final String SUCCESS_CONNECT = "Connected.";
    public static final String FAIL_CONNECT = "Server closed.";

    /**
     * Load UID và secretKey cho Client tạo mới nếu chưa tồn tại
     * @return True nếu đã có UID và ngược lại
     */
    public static boolean config() {
        try {
            String config = FileHandler.read(CLIENT_SIDE_PATH + FILE_CONFIG_NAME);
            //Định dạng: UID|secretKey|hash|date
            StringTokenizer tokenizer = new StringTokenizer(config,"|",false);
            UID = tokenizer.nextToken();
            secretKey = tokenizer.nextToken();
            String hash = tokenizer.nextToken();

            //hash là hàm băm từ chuỗi UID có salt là secretKey
            //Nếu băm UID + secretKey ra chuỗi hash không chuỗi hash cũ tức là dữ liệu đã bị thay đổi
            // -> không hợp lệ
            // -> tạo UID + secretKey mới với hàm create()
            if (!StringUtils.applySha256(UID,secretKey).equals(hash))
                throw new Exception();

            System.out.println("ClientID: " + UID);
            System.out.println("Secret key: " + secretKey);
            return true;
        } catch (Exception ignored) {
            UID = UUID.randomUUID().toString(); //Tạo UID cho Client;
            System.out.println("new ClientID: " + UID);
            return false;
        }
    }

    /**
     * Tạo secretKey cho Client
     */
    public static void create() {
        secretKey = ClientKeyGenerator.create();
        String hash = StringUtils.applySha256(UID,secretKey);
        String config = UID + "|" + secretKey  + "|" + hash + "|" + LocalDateTime.now();
        FileHandler.write(CLIENT_SIDE_PATH + FILE_CONFIG_NAME, config);
        System.out.println("new Secret key: " + secretKey);
    }

    private static void addProvider(String password) {
        /*Adding the JSSE (Java Secure Socket Extension) provider which provides SSL and TLS protocols
        and includes functionality for data encryption, server authentication, message integrity,
        and optional client authentication.*/
        Security.addProvider(new Provider());
        //specifing the trustStore file which contains the certificate & public of the server
        System.setProperty("javax.net.ssl.trustStore", CLIENT_SIDE_PATH + TRUST_STORE_NAME);
        //specifing the password of the trustStore file
        System.setProperty("javax.net.ssl.trustStorePassword", password);
        //This optional and it is just to show the dump of the details of the handshake process
        if (SSL_DEBUG_ENABLE)
            System.setProperty("javax.net.debug","all");
    }

    public static void connectServer() throws IOException {
        boolean haveUID = config();

        socket = new Socket(SERVER_IP, MAIN_PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        boolean isVerified = false; //Đã đăng ký phiên làm việc thành công chưa
        socket.setSoTimeout(10 * 1000);
        do {
            send(UID); //Gửi UID để server kiểm tra

            String verify = receive(); //Nhận kết quả kiểm tra từ Server
            if (verify.equals("Banned")) {
                Client.close();
                Client.Frame.appendProcess("UID got banned.");
                throw new IOException();
            }
            if (verify.equals("Verified")) { //Thông qua có thể dùng UID và key hiện có
                System.out.println(verify + ": " + UID + " - Key: " + secretKey);
                isVerified = true;
            }
            else if (verify.equals("Expired")) { //Ko thông qua -> tạo UID và key mới thử lại
                if (haveUID) {
                    System.out.println(verify + ": " + UID + " - Key: " + secretKey);
                }


                //Tạo SSL socket để gửi UID và secretKey một cách an toàn
                addProvider(TRUST_STORE_PASSWORD);
                //SSLSSocketFactory establishes the ssl context and creates SSLSocket
                sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
                //Create SSLSocket using SSLServerFactory already established ssl context and connect to server
                sslSocket = (SSLSocket) sslsocketfactory.createSocket(SERVER_IP, VERIFY_PORT);
                sslSocket.setSoTimeout(10 * 1000); //set timeout 10s cho read() của socket
                inSSL = new DataInputStream(sslSocket.getInputStream());
                outSSL = new DataOutputStream(sslSocket.getOutputStream());

                create(); //Tạo key mới
                sendVerify(); //Gửi lại UID + key
                waitVerify(); //chờ phản hồi ""
                System.out.println("Sent " + UID + "|" + secretKey + " to server.");
            }
        } while (!isVerified);

        //sau khi kết nối thành công
        //-> Tạo Thread lắng nghe thông điệp từ server
        socket.setSoTimeout(0);
        Listener = new ClientListener();
        Listener.start();
    }

    public static boolean checkConnection() {
        return socket != null;
    }

    /**
     * Gửi UID + secret key cho server để đăng ký phiên làm việc
     */
    private static void sendVerify() throws IOException {
        outSSL.writeUTF(UID + "|" + secretKey);
    }

    /**
     * Gửi UID + secret key cho server để đăng ký phiên làm việc
     */
    private static void waitVerify() throws IOException {
        inSSL.readUTF();
    }

    public static void send(String data) throws IOException {
        out.write(data);
        out.newLine();
        out.flush();
    }

    public static String receive() throws IOException, NullPointerException {
        return in.readLine();
    }

    public static void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ignored) {}
    }

    public static void sendImage(String path) throws IOException {
        BufferedImage image;
        if (FileHandler.isWebURL(path))
            image = ImageIO.read(new URL(path));
        else image = ImageIO.read(new File(path));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        String imageBytes = StringUtils.getStringFromBytes(byteArrayOutputStream.toByteArray());
        send(requestHandle("IMAGE","jpg", "", imageBytes));
    }

    /**
     * Hàm dùng để xử lý dữ liệu để gửi cho client
     * @param language ngôn ngữ lập trình
     * @param stdin đầu vào do người dùng nhập
     * @param script source code
     * @return String - dữ liệu đã qua xử lý và mã hóa
     */
    public static String requestHandle(String description, String language, String stdin, String script) {
        ClientDataPacket clientPacket = new ClientDataPacket(description, language,stdin,script);
        System.out.println("Created client data packet:\n" + clientPacket.pack());
        return AES_Encryptor.encrypt(clientPacket.pack(), secretKey); //mã hóa bằng secret key trước khi gửi
    }

    /**
     * Hàm dùng để xử lý dữ liệu từ Server gửi tới
     * @param data dữ liệu trừ server đã bị mã hóa
     * @return ServerDataPacket - Gói dữ liệu Server
     */
    public static ServerDataPacket responseHandle(String data) {
        return ServerDataPacket.unpack(AES_Encryptor.decrypt(data, secretKey)); //giả mã bằng secret key
    }
}
