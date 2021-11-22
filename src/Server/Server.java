import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 5000;

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(PORT); // mở server ở cổng PORT(5000)
            System.out.println("Server started.");
            while (true) {
                Socket socket = server.accept(); //chờ kết nối từ Client
                new ServerThread(socket).start(); //Tạo một luồng thread mới xử lý kết nối từ Client vừa accept()
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
