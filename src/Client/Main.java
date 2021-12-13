package Client;

import ClientGUI.GUI;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //System.err.close();

        //Khởi tạo giao diện
        FlatIntelliJLaf.setup();
        Client.Frame = new GUI();
        try {
            UIManager.setLookAndFeel("Window");
        } catch (Exception ignored) {}
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Client.Frame.setVisible(true);
            }
        });

        //Cấu trúc Client và kết nối tới Server
        try {
            Client.connectServer();
        } catch (IOException ignored) { //Nếu kết nói thất bại
            Client.Frame.appendProcess(Client.FAIL_CONNECT);
            Client.Frame.appendProcess("Click RUN to reconnect !");
            Client.Frame.setEnabled(true);
        }

        if (Client.checkConnection()) {
            Client.Frame.appendProcess(Client.SUCCESS_CONNECT);
            Client.Frame.setEnabled(true);
        }
    }
}
