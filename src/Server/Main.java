package Server;

import Services.StringUtils;

import java.io.IOException;
import java.net.Socket;

public class Main {
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        try {
            Server.open(); // mở server ở cổng PORT(5000)
            System.out.println("Server started.");
            System.out.print("Public Key: ");
            System.out.println(StringUtils.getStringFromKey(Server.keyPair.getPublic()));
            System.out.print("Private Key: ");
            System.out.println(StringUtils.getStringFromKey(Server.keyPair.getPrivate()));
            while (true) {
                Socket socket = Server.waitClient(); //chờ kết nối từ Client
                new ServerThread(socket).start(); //Tạo một luồng thread mới xử lý kết nối từ Client vừa accept()
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
