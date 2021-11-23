package Client;

import java.io.*;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        try {
            Client.connectServer();
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            while(true) {
                try {
                    // Client nhận dữ liệu từ keyboard và gửi vào stream -> server
                    System.out.print("Input: ");
                    String line = stdIn.readLine();
                    Client.send(line);
                    if (line.equals(Client.BREAK_CONNECT_KEY))
                        break;

                    // Client nhận phản hồi từ server
                    line = Client.receive();
                    System.out.println(line);
                } catch (SocketException | NullPointerException ignore) {
                    System.out.println(Client.FAIL_CONNECT);
                    break;
                }
            }
            stdIn.close();
            Client.close();
            System.out.println(Client.CLOSE_CONNECT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
