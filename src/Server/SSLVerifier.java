package Server;

import Security.ClientKeyGenerator;

import javax.net.ssl.SSLSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.UUID;

public class SSLVerifier extends Thread implements Runnable {
    protected final SSLSocket sslSocket;
    private final DataInputStream inSSL;
    private final DataOutputStream outSSL;

    /**
     * Thread nhận UID và secret key từ Client và lưu trữ nếu đúng định dạng.
     * @param sslClientSocket truyền SSL socket kết nối Client vào
     */
    public SSLVerifier(SSLSocket sslClientSocket) throws IOException {
        sslSocket = sslClientSocket;
        inSSL = new DataInputStream(sslSocket.getInputStream());
        outSSL = new DataOutputStream(sslClientSocket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            String data = inSSL.readUTF();
            //Kiếm tra định dạng: UID|secretKey <- Lưu ý !!!
            if (!data.contains("|"))
                return;

            StringTokenizer tokenizer = new StringTokenizer(data,"|",false);
            if (tokenizer.countTokens() != 2)
                return;

            String uid = tokenizer.nextToken();
            uid = UUID.fromString(uid).toString();
            String secretKey = tokenizer.nextToken();
            if (secretKey.length() != ClientKeyGenerator.KEY_BIT_LENGTH) //secretKey phải 16 bit
                return;

            User user = new User(uid);
            user.setSecretKey(secretKey);
            user.setModifiedDate(LocalDateTime.now().toString());
            user.setSessionTime(System.currentTimeMillis());
            user.setStatus("offline");
            if (!Server.users.add(user)) {
                for (User u : Server.users) {
                    if (u.equals(user)) {
                        u.setSecretKey(user.getSecretKey());
                        u.setSessionTime(user.getSessionTime());
                        break;
                    }
                }
            }

            outSSL.writeUTF("");

            inSSL.close();
            outSSL.close();
            sslSocket.close();
        } catch (IOException | IllegalArgumentException ignored) {}
    }
}

