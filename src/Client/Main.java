package Client;

import ClientGUI.GUI;
import com.apple.eawt.Application;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //System.err.close();

        //Khởi tạo giao diện
        FlatIntelliJLaf.setup();
        FlatLightLaf.installLafInfo();
        FlatDarkLaf.installLafInfo();
        FlatIntelliJLaf.installLafInfo();
        FlatDarculaLaf.installLafInfo();
        try {
            Image image = ImageIO.read(new File("image/icon.png"));
            Application.getApplication().setDockIconImage(image);
        } catch (IOException ignored) {}


        Client.Frame = new GUI();
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
