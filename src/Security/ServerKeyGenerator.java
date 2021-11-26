package Security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class ServerKeyGenerator {
    public static KeyPair keyPair;
    public static PublicKey publicKey;
    public static PrivateKey privateKey;

    public static KeyPair create() {
        try {
            SecureRandom secureRandom = new SecureRandom();
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024, secureRandom);

            // Khởi tạo cặp khóa
            KeyPair pair = keyPairGenerator.genKeyPair();
            // PublicKey
            publicKey = pair.getPublic();
            // PrivateKey
            privateKey = pair.getPrivate();
            keyPair = pair;
            return pair;
        } catch (Exception ignored) {
            return null;
        }
    }
}