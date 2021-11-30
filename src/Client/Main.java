package Client;

import ClientGUI.GUI;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //System.err.close();
        GUI clientFrame = new GUI();
        try {
            UIManager.setLookAndFeel("Window");
        } catch (Exception ignored) {}

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                clientFrame.setVisible(true);
            }
        });

        try {
            Client.config();
            Client.connectServer();
        } catch (IOException e) {
            clientFrame.appendProcess(Client.FAIL_CONNECT);
            clientFrame.appendProcess("Click RUN to reconnect !");
            clientFrame.setEnabled(true);
        }

        if (Client.checkConnection()) {
            clientFrame.appendProcess(Client.SUCCESS_CONNECT);
            clientFrame.setEnabled(true);
        }

    }
}
