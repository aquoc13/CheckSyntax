package Server;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ServerManager.AuthenticationFrame();
        Server.manager = new ServerManager();
    }

    public static void run(String keyStore_password) {
        try {
            Server.open(keyStore_password);
        } catch (IOException ignored) {}

        //Thread nhận đăng ký UID và secret key
        Server.verifier = new Thread(() -> {
            while (true) {
                try {
                    Server.sslExecutor = Executors.newCachedThreadPool();
                    SSLSocket sslSocket = Server.verifyClient(); //chờ kết nối từ Client để xác minh
                    Server.sslExecutor.execute(new SSLVerifier(sslSocket)); //Tạo một luồng thread mới xử lý kết nối từ Client vừa accept()
                } catch (IOException ignored) {}
            }
        });
        Server.verifier.start();

        //Thread bấm giờ để don dẹp bộ nhớ UID-secretKey
        Server.timer = new ServerTimer(Server.TIMER_LOOP, Server.TIMER_SESSION); //run mỗi 10p và thời gian sống của UID client là 60p
        Server.timer.start();

        //Thread accept kết nối từ Client
        Server.listener = new Thread(() -> {
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
                } catch (IOException ignored) {}
            }
        });
        Server.listener.start();
    }
}
