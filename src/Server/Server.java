package Server;

import Client.ClientDataPacket;
import Security.AES_Encryptor;
import Services.StringUtils;
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
import java.util.HashMap;

public class Server {
    private static ServerSocket serverSocket;
    private static SSLServerSocket sslServerSocket;
    public static HashMap<String, String> secretKeyList = new HashMap<>();
    public static HashMap<String, Long> secretKeyTimer = new HashMap<>();

    public static final int MAIN_PORT = 5000;
    public static final int VERIFY_PORT = 5005;
    public static final String BREAK_CONNECT_KEY = "bye";
    public static final String KEY_STORE_NAME = "myKeyStore.jks";
    public static final String SERVER_SIDE_PATH = "workspace/Server.Side/";
    public static final String KEY_STORE_ALIAS = "mykey";
    public static final String KEY_STORE_HASH = "0b1957259ce60db4f9cb5c51cb76a000cefe7234f922a515f56b977951eb6f84";
    public static final String KEY_STORE_SALT = "5ae877676f3efe25";
    public static final boolean SSL_DEBUG_ENABLE = false;

    private static void addProvider(String keyStore, String password) {
        /*Adding the JSSE (Java Secure Socket Extension) provider which provides SSL and TLS protocols
        and includes functionality for data encryption, server authentication, message integrity,
        and optional client authentication.*/
        Security.addProvider(new Provider());

        //specifing the keystore file which contains the certificate/public key and the private key
        System.setProperty("javax.net.ssl.keyStore", keyStore);

        //specifing the password of the keystore file
        System.setProperty("javax.net.ssl.keyStorePassword", password);

        //This optional and it is just to show the dump of the details of the handshake process
        if (SSL_DEBUG_ENABLE)
            System.setProperty("javax.net.debug","all");
    }

    public static void open(String password) throws IOException {
        addProvider(SERVER_SIDE_PATH + KEY_STORE_NAME, password);
        getKey(SERVER_SIDE_PATH + KEY_STORE_NAME, KEY_STORE_ALIAS, password);
        try {
            //SSLServerSocketFactory establishes the ssl context and creates SSLServerSocket
            SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            //Create SSLServerSocket using SSLServerSocketFactory established ssl context
            sslServerSocket = (SSLServerSocket) sslServerSocketfactory.createServerSocket(VERIFY_PORT);
        } catch (Exception ignored) {}

        serverSocket = new ServerSocket(MAIN_PORT);
    }

    public static SSLSocket verifyClient() throws IOException {
        return (SSLSocket) sslServerSocket.accept();
    }

    public static Socket acceptClient() throws IOException {
        return serverSocket.accept();
    }

    private static void getKey(String keyStore, String alias, String password) {
        try {
            KeyStore ks = KeyStore.getInstance("jks");
            ks.load(new FileInputStream(keyStore), password.toCharArray());
            Key key = ks.getKey(alias, password.toCharArray());
            final Certificate cert = ks.getCertificate("mykey");
            System.out.println("--- Certificate START ---");
            System.out.println(cert);
            System.out.println("--- Certificate END ---\n");
            System.out.println("Public key: " + StringUtils.getStringFromKey(cert.getPublicKey()));
            System.out.println("Private key: " + StringUtils.getStringFromKey(key));
        } catch (Exception ignored) {}
    }

    public static void checkKeyList() {
        System.out.println("IDList: " + Server.secretKeyList + "\nIDTimer: " + Server.secretKeyTimer);
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
                        + " version: "+ dataPacket.getVersionIndex(),
                dataPacket.getScript(),
                dataPacket.getStdin(),
                "000",
                "000",
                "0ms");
        return AES_Encryptor.encrypt(serverPacket.pack(), secretKey);
    }
}
