package Server;

import Services.StringUtils;

import javax.net.ssl.SSLSocket;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        verify();
    }

    @SuppressWarnings({"InfiniteLoopStatement", "BusyWait"})
    public static void run(String keyStore_password) {
        try {
            Server.open(keyStore_password);

            new Thread(() -> {
                while (true) {
                    try {
                        ExecutorService sslExecutor = Executors.newCachedThreadPool();
                        SSLSocket sslSocket = Server.verifyClient(); //chờ kết nối từ Client để xác minh
                        sslExecutor.execute(new SSLVerifyThread(sslSocket)); //Tạo một luồng thread mới xử lý kết nối từ Client vừa accept()
                    } catch (IOException ignored) {}
                }
            }).start();

            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(10*60*1000); //10min
                        for (Map.Entry<String, Long> entry : Server.secretKeyTimer.entrySet()) {
                            Long currentTime = System.currentTimeMillis();
                            if (currentTime - entry.getValue() >= (60*60*1000)) { //60min
                                Server.secretKeyList.remove(entry.getKey());
                                Server.secretKeyTimer.remove(entry.getKey());
                                Server.checkKeyList();
                            }
                        }
                    } catch (InterruptedException ignored) {}
                }
            }).start();

            ThreadPoolExecutor executor = new ThreadPoolExecutor(
                    2,           //Số thread một lúc
                    5,       //số thread tối đa khi server quá tải
                    1,         //thời gian một thread được sống nếu không làm gì
                    TimeUnit.MINUTES,       //đơn vị phút
                    new ArrayBlockingQueue<>(10)); //Blocking queue để cho request đợi

            System.out.println("Server ready to accept connections.\n");

            while (true) {
                Socket socket = Server.acceptClient(); //chờ kết nối từ Client trao đổi data
                executor.execute(new ServerThread(socket)); //Tạo một luồng thread mới xử lý kết nối từ Client vừa accept()
            }
        } catch (IOException ignored) {}
    }

    public static void verify() {
        //nhập pass và mở server ở cổng MAIN_PORT(5000)
        JFrame frame = new JFrame("Authentication");
        JLabel lblPassword = new JLabel("Password: ");
        lblPassword.setForeground(Color.white);
        JPasswordField pfPassword = new JPasswordField(19);
        pfPassword.setText("hellokitty"); // << password ở đây
        pfPassword.setBackground(new Color(61, 72, 96));
        pfPassword.setForeground(Color.white);
        lblPassword.setLabelFor(pfPassword);

        JButton btnGet = new JButton("Start");
        btnGet.setMargin(new Insets(5,30,5,30));
        btnGet.setBackground(new Color(13, 21, 37));
        btnGet.setFont(new Font("Roboto", Font.BOLD, 14)); // NOI18N
        btnGet.setForeground(Color.white);
        btnGet.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGet.setFocusPainted(false);
        btnGet.addActionListener(e -> {
            String keyStore_password;
            keyStore_password = new String(pfPassword.getPassword());
            //hash password nhập vào và kiểm tra
            String hash = StringUtils.applySha256(keyStore_password, Server.KEY_STORE_SALT);
            if (hash.equals(Server.KEY_STORE_HASH)) {
                frame.dispose();
                run(keyStore_password);
            } else {
                pfPassword.setText("");
                lblPassword.setText("Wrong! ");
            }
        });

        JPanel panel = new JPanel();
        panel.setBackground(new Color(13, 21, 37));
        panel.setLayout(new FlowLayout());
        panel.add(lblPassword);
        panel.add(pfPassword);
        panel.add(btnGet);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 140);
        frame.setIconImage(new ImageIcon("image/icon.png").getImage());
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
