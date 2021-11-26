package Security;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA_Encryptor {
    public static final String Algorithm = "RSA";

    public static String encrypt(String plaintext, PublicKey publicKey) {
        try {
            // Mã hoá dữ liệu
            Cipher cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptOut = cipher.doFinal(plaintext.getBytes());
            return Base64.getEncoder().encodeToString(encryptOut);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String plaintext, String encodedPublicKey) {
        try {
            PublicKey publicKey = getPublicKeyFromString(encodedPublicKey);
            // Mã hoá dữ liệu
            Cipher cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptOut = cipher.doFinal(plaintext.getBytes());
            return Base64.getEncoder().encodeToString(encryptOut);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String ciphertext, PrivateKey privateKey) {
        try {
            // Giải mã dữ liệu
            Cipher cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptOut = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
           return new String(decryptOut);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static String decrypt(String ciphertext, String encodedPrivateKey) {
        try {
            PrivateKey privateKey = getPrivateKeyFromString(encodedPrivateKey);
            // Giải mã dữ liệu
            Cipher cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptOut = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
            return new String(decryptOut);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static PublicKey getPublicKeyFromString(String encodedKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicBytes = Base64.getDecoder().decode(encodedKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithm);
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey getPrivateKeyFromString(String encodedKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] privateBytes = Base64.getDecoder().decode(encodedKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithm);
        return keyFactory.generatePrivate(keySpec);
    }
}
