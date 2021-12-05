package Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES_Encryptor {
    public static final String Algorithm = "AES";

    /**
     * Mã hóa chuỗi bằng thuật toán AES với secret key
     * @param plaintext chuỗi cần mã hóa
     * @param secretKey khóa bí mật
     * @return chuỗi đã bị mã hóa
     */
    public static String encrypt(String plaintext, String secretKey) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes(), Algorithm);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] byteEncrypted = cipher.doFinal(plaintext.getBytes());
            return Base64.getEncoder().encodeToString(byteEncrypted);
        } catch (Exception ignored) {
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
            SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes(), Algorithm);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] byteDecrypted = cipher.doFinal(byteEncrypted);
            return new String(byteDecrypted);
        } catch (Exception ignored) {
            return "";
        }
    }
}
