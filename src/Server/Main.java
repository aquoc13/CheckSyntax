package Server;

import com.apple.eawt.Application;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLSocket;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        try {
            Image image = ImageIO.read(new File("image/server_icon.png"));
            Application.getApplication().setDockIconImage(image);
        } catch (Exception ignored) {}

        ServerManagerGUI.AuthenticationFrame();
        Server.manager = new ServerManagerGUI();
    }

    public static void run() {
        try {
            Server.open();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        //Thread nhận đăng ký UID và secret key
        Server.verifier = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Server.sslExecutor = Executors.newCachedThreadPool();
                        SSLSocket sslSocket = Server.verifyClient(); //chờ kết nối từ Client để xác minh
                        Server.sslExecutor.execute(new SSLVerifier(sslSocket)); //Tạo một luồng thread mới xử lý kết nối từ Client vừa accept()
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Server.verifier.start();

        //Thread bấm giờ để don dẹp bộ nhớ UID-secretKey
        Server.timer = new ServerTimer(Server.TIMER_LOOP, Server.TIMER_SESSION); //run mỗi 10p và thời gian sống của UID client là 60p
        Server.timer.start();

        //Thread accept kết nối từ Client
        Server.accepter = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Server ready to accept connections.\n");
                while (true) {
                    try {
                        //Tạo executor quản lý thread pool
                        Server.executor = new ThreadPoolExecutor(
                                Server.EXECUTOR_CORE,       //Số thread một lúc
                                Server.EXECUTOR_MAX,        //số thread tối đa khi server quá tải
                                Server.EXECUTOR_ALIVE_TIME, //thời gian một thread được sống nếu không làm gì
                                TimeUnit.MINUTES,           //đơn vị phút
                                new ArrayBlockingQueue<>(Server.EXECUTOR_CAPACITY)); //Blocking queue để cho request đợi

                        Socket socket = Server.acceptClient();
                        Server.executor.execute(new ServerListener(socket)); //Tạo một luồng thread mới xử lý kết nối từ Client vừa accept()
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Server.accepter.start();
    }
}
