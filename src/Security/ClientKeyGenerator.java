package Security;

import Client.Client;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ClientKeyGenerator {
    public static String secretKey;
    public static int KEY_BIT_LENGTH = 16; //secretKey phải 16 bit

    /**
     * Khởi tạo UID và secretKey cho Client
     */
    public static String create() {
        try {
            do {
                MessageDigest salt = MessageDigest.getInstance("SHA-256");
                salt.update(Client.UID.getBytes(StandardCharsets.UTF_8));
                secretKey = Long.toHexString(ByteBuffer.wrap(salt.digest()).getLong());
            } while (secretKey.length() != KEY_BIT_LENGTH);
            return secretKey;
        } catch (NoSuchAlgorithmException ignored) {
            return "0x00000000000000";
        }
    }
}
