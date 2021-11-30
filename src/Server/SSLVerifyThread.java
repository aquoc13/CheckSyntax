package Server;

import javax.net.ssl.SSLSocket;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.StringTokenizer;

public class SSLVerifyThread extends Thread implements Runnable {
    protected final SSLSocket sslSocket;
    private final DataInputStream inSSL;

    public SSLVerifyThread(SSLSocket sslClientSocket) throws IOException {
        this.sslSocket = sslClientSocket;
        inSSL = new DataInputStream(sslSocket.getInputStream());
    }

    @Override
    public void run() {
        try {
            String data = inSSL.readUTF();
            if (!data.contains("|")) return;
            StringTokenizer tokenizer = new StringTokenizer(data,"|",false);
            if (tokenizer.countTokens() != 2) return;
            String uid = tokenizer.nextToken();
            String secretKey = tokenizer.nextToken();
            if (!Server.secretKeyList.containsKey(uid)) {
                Server.secretKeyList.putIfAbsent(uid, secretKey);
                Server.secretKeyTimer.putIfAbsent(uid, System.currentTimeMillis());
                Server.checkKeyList();
            }

            inSSL.close();
            sslSocket.close();
        } catch (IOException ignored) {}
    }
}

