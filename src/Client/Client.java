import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class Client {
    private static final int PORT = 5000;
    private static final String SERVER_IP = "113.173.124.13";
    private static final String BREAK_CONNECT_KEY = "bye";


    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_IP, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            while(true) {
                try {
                    // Client nhận dữ liệu từ keyboard và gửi vào stream -> server
                    System.out.print("Input: ");
                    String line = stdIn.readLine();
                    out.write(line);
                    out.newLine();
                    out.flush();
                    if (line.equals(BREAK_CONNECT_KEY))
                        break;

                    // Client nhận phản hồi từ server
                    line = in.readLine();
                    System.out.println(line);
                } catch (SocketException ignore) {
                    System.out.println("Server closed.");
                    break;
                }
            }
            in.close();
            out.close();
            stdIn.close();
            socket.close();
            System.out.println("Client closed connection.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
