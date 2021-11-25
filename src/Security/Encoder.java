package Security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encoder {
    public static String encode(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }

    public static String decode(String encoded) {
        byte[] decodedBytes = Base64.getDecoder().decode(encoded);
        return new String (decodedBytes, StandardCharsets.UTF_8);
    }
}
