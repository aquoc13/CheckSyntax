package Client;

import ClientGUI.GUI;
import Security.ClientKeyGenerator;
import Security.RSA_Encryptor;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        GUI clientFrame = new GUI();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                clientFrame.setVisible(true);
            }
        });

        try {
            Client.connectServer();
        } catch (IOException e) {
            clientFrame.appendProcess(Client.FAIL_CONNECT);
            clientFrame.appendProcess("Click Run to reconnect !");
            clientFrame.setEnabled(true);
        }

        if (Client.checkConnection()) {
            clientFrame.appendProcess(Client.SUCCESS_CONNECT);
            clientFrame.setEnabled(true);
        }
    }
}
