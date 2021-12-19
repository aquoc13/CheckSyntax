package Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class AES_Encryptor {
    public static final String ALGORITHM = "AES";
    public static final int KEY_BIT_LENGTH = 16; //secretKey phải 16 bit

    /**
     * Khởi tạo UID và secretKey cho Client
     */
    public static String generate() {
        try {
            String secretKey;
            do {
                String keyID = UUID.randomUUID().toString();
                MessageDigest salt = MessageDigest.getInstance("SHA-256");
                salt.update(keyID.getBytes(StandardCharsets.UTF_8));
                secretKey = Long.toHexString(ByteBuffer.wrap(salt.digest()).getLong());
            } while (secretKey.length() != KEY_BIT_LENGTH);
            return secretKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "0x00000000000000";
        }
    }

    /**
     * Mã hóa chuỗi bằng thuật toán AES với secret key
     * @param plaintext chuỗi cần mã hóa
     * @param secretKey khóa bí mật
     * @return chuỗi đã bị mã hóa
     */
    public static String encrypt(String plaintext, String secretKey) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] byteEncrypted = cipher.doFinal(plaintext.getBytes());
            return Base64.getEncoder().encodeToString(byteEncrypted);
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * Giải mã chuỗi bị mã hóa bằng thuật toán AES với secret key
     * @param ciphertext chuỗi bị mã hóa
     * @param secretKey khóa bí mật
     * @return chuỗi giải mã
     */
    public static String decrypt(String ciphertext, String secretKey) {
        try {
            byte[] byteEncrypted = Base64.getDecoder().decode(ciphertext);
            SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] byteDecrypted = cipher.doFinal(byteEncrypted);
            return new String(byteDecrypted);
        } catch (Exception e) {
            return "";
        }
    }
}
