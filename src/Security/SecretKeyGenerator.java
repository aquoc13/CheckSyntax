package Security;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class SecretKeyGenerator {
    public static String secretKey;
    public static int KEY_BIT_LENGTH = 16; //secretKey phải 16 bit

    /**
     * Khởi tạo UID và secretKey cho Client
     */
    public static String create() {
        try {
            do {
                String keyID = UUID.randomUUID().toString();
                MessageDigest salt = MessageDigest.getInstance("SHA-256");
                salt.update(keyID.getBytes(StandardCharsets.UTF_8));
                secretKey = Long.toHexString(ByteBuffer.wrap(salt.digest()).getLong());
            } while (secretKey.length() != KEY_BIT_LENGTH);
            return secretKey;
        } catch (NoSuchAlgorithmException ignored) {
            return "0x00000000000000";
        }
    }
}
