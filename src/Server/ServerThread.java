import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ServerThread extends Thread{
    protected Socket socket;
    private final String fromIP;
    private static final String BREAK_CONNECT_KEY = "bye"; //Key để break vòng lặp và ngắt kết nối từ Client tới Server

    /**
     * Tạo ra một thread mới để kết nối và xử lý yêu cầu từ phía Client
     * @param clientSocket truyền socket kết nối Client vào
     */
    public ServerThread(Socket clientSocket) {
        this.socket = clientSocket;
        this.fromIP = clientSocket.getInetAddress() //tách InetAddress từ socket
                                  .getHostAddress() //in địa chỉ IP từ InetAddress của client kết nối tới
                    + ":" + clientSocket.getPort(); //in port của client kết nối tới từ socket
                                                    //vd: 127.0.0.1:5013
    }

    public void run() {
        System.out.println("Client " + fromIP + " is connecting.");
        //vd: Client 127.0.0.1:5013 is connected.

        try {
            //khởi tạo cổng đầu vào (in) + đầu ra (out)
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //Lặp liên tục để nhận request từ phía Client
            while(true) {
                try {
                    // Server nhận dữ liệu từ client qua stream
                    String line;
                    line = in.readLine();
                    if (line.equals(BREAK_CONNECT_KEY))
                        break; //Vòng lặp sẽ ngừng khi Client gửi lệnh "bye"
                    System.out.println("Server get: " + line + " from " + fromIP);

                    //Xử lý dữ liệu bằng class ServerHandler method requestHandle
                    line = ServerHandler.requestHandle(line);

                    // Server gửi phản hồi ngược lại cho client (chuỗi đảo ngược)
                    out.write(line);
                    out.newLine();
                    out.flush();
                    System.out.println("Server response: " + line + " to " + fromIP);
                } catch (SocketException | NullPointerException ignore) {
                    break;
                }
            }

            //Thực hiện đóng kết nối socket và đóng cổng đầu vào (in) + đầu ra (out).
            in.close();
            out.close();
            socket.close();
            System.out.println("Server closed connection with " + fromIP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
