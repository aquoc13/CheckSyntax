package Client;

import ClientGUI.GUI;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            Client.connectServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
}
