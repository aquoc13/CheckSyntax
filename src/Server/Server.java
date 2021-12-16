package Server;

import Services.StringUtils;
import Security.AES_Encryptor;
import com.google.gson.JsonParser;
import org.openeuler.com.sun.net.ssl.internal.ssl.Provider;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.Certificate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    public static final int MAIN_PORT = 5000;
    public static final int VERIFY_PORT = 5005;

    public static ServerManagerGUI manager;
    protected static String keyStore_password;

    public static Thread accepter;
    public static Thread verifier;
    public static Thread timer;
    public static ExecutorService sslExecutor;
    public static ThreadPoolExecutor executor;
    public static final int TIMER_LOOP = 10;            //đơn vị phút
    public static final int TIMER_SESSION = 60;          //đơn vị phút
    public static final int EXECUTOR_CORE = 2;          //Số thread một lúc
    public static final int EXECUTOR_MAX = 5;           //số thread tối đa khi server quá tải
    public static final int EXECUTOR_ALIVE_TIME = 1;    //thời gian một thread được sống nếu không làm gì
    public static final int EXECUTOR_CAPACITY = 10;     //Số lượng hàng chờ có thể chứa của executor

    public static final Set<User> users = new LinkedHashSet<>();
    public static final HashMap<String, String> banList = new HashMap<>();

    private static ServerSocket serverSocket;
    private static SSLServerSocket sslServerSocket;

    public static final String BREAK_CONNECT_KEY = "bye";
    private static final String KEY_STORE_NAME = "myKeyStore.jks";
    public static final String SERVER_SIDE_PATH = "workspace/Server.Side/";
    private static final String KEY_STORE_ALIAS = "mykey";
    public static final String KEY_STORE_HASH = "0b1957259ce60db4f9cb5c51cb76a000cefe7234f922a515f56b977951eb6f84";
    public static final String KEY_STORE_SALT = "5ae877676f3efe25";
    private static final boolean SSL_DEBUG_ENABLE = false;

    /**
     * Khai báo bảo mật cho SSL Socket với Java Secure Socket Extension
     * Key Store: certificate và private ket, public key
     */
    private static void addProvider() {
        /*Adding the JSSE (Java Secure Socket Extension) provider which provides SSL and TLS protocols
        and includes functionality for data encryption, server authentication, message integrity,
        and optional client authentication.*/
        Security.addProvider(new Provider());

        //specifing the keystore file which contains the certificate/public key and the private key
        System.setProperty("javax.net.ssl.keyStore", SERVER_SIDE_PATH + KEY_STORE_NAME);

        //specifing the password of the keystore file
        System.setProperty("javax.net.ssl.keyStorePassword", Server.keyStore_password);

        //This optional and it is just to show the dump of the details of the handshake process
        if (SSL_DEBUG_ENABLE)
            System.setProperty("javax.net.debug","all");
    }

    /**
     * Mở server
     */
    public static void open() throws IOException {
        addProvider();
        getKey(Server.keyStore_password);
        //SSLSSocketFactory thiết lập the ssl context and tạo SSLSocket
        SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        //Tạo SSLSocket bằng SSLServerFactory đã thiết lập ssl context và kết nối tới server
        sslServerSocket = (SSLServerSocket) sslServerSocketfactory.createServerSocket(VERIFY_PORT);
        serverSocket = new ServerSocket(MAIN_PORT);
    }

    /**
     * Chờ chứng chỉ từ Client
     */
    public static SSLSocket verifyClient() throws IOException {
        return (SSLSocket) sslServerSocket.accept();
    }

    /**
     * Chờ Client kết nối tới
     */
    public static Socket acceptClient() throws IOException {
        return serverSocket.accept();
    }

    public static String messageHandle(String header, String body, User to) throws IOException {
        /*switch (header) { //ĐỌc HEADER
            case "CHAT":
                break;
        }*/
        ServerDataPacket serverPacket = new ServerDataPacket(header, "", body, "", "", "");
        to.addRequestList(JsonParser.parseString("{ \"Description\": \"server chat\" }").toString());
        to.addResponseList(JsonParser.parseString(serverPacket.pack()).toString());
        to.addDateList(LocalDateTime.now().toString());
        return AES_Encryptor.encrypt(serverPacket.pack(), to.getSecretKey()); //mã hóa bằng secret key trước khi gửi
    }

    /**
     * Lấy chứng chỉ, public key, private key từ Key Store myKeyStore.jks
     * @param password mật khẩu
     */
    private static void getKey(String password) {
        try {
            KeyStore ks = KeyStore.getInstance("jks");
            ks.load(new FileInputStream(SERVER_SIDE_PATH + KEY_STORE_NAME), Server.keyStore_password.toCharArray());
            Key key = ks.getKey(KEY_STORE_ALIAS, Server.keyStore_password.toCharArray());
            final Certificate cert = ks.getCertificate("mykey");
            System.out.println("--- Certificate START ---");
            System.out.println(cert);
            System.out.println("--- Certificate END ---\n");
            System.out.println("Public key: " + StringUtils.getStringFromKey(cert.getPublicKey()));
            System.out.println("Private key: " + StringUtils.getStringFromKey(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
