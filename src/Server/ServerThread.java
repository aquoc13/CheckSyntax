package Server;

import Client.ClientDataPacket;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ServerThread extends Thread implements Runnable{
    protected final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final String fromIP;
    public String UID;
    public String secretKey;

    /**
     * Tạo ra một thread mới để kết nối và xử lý yêu cầu từ phía Client
     * @param clientSocket truyền socket kết nối Client vào
     */
    public ServerThread(Socket clientSocket) throws IOException {
        socket = clientSocket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        fromIP = clientSocket.getInetAddress() //tách InetAddress từ socket
                                  .getHostAddress() //in địa chỉ IP từ InetAddress của client kết nối tới
                    + ":" + clientSocket.getPort(); //in port của client kết nối tới từ socket
                                                    //vd: 127.0.0.1:5013
    }

    /**
     * Sau khi gọi hàm start() từ Thread sẽ tự động chày hàm run()
     */
    @Override
    public void run() {
        try {
            String verifyStatus;
            do {
                if ((UID = receive()) == null)
                    throw new IOException();
                if (Server.secretKeyList.containsKey(UID)) {
                    secretKey = Server.secretKeyList.get(UID);
                    verifyStatus = "UIDVerified";
                } else verifyStatus = "UIDExpired";
                send(verifyStatus);
            } while (verifyStatus.equals("UIDExpired"));
            System.out.println("Client " + fromIP + " is connecting" + " - ID: " + UID + "- Key: " + secretKey);
            //Lặp liên tục để nhận request từ phía Client
            while(true) {
                try {
                    // Server nhận dữ liệu từ client qua stream
                    String line = receive();
                    if (line.equals(Server.BREAK_CONNECT_KEY)) {
                        Server.secretKeyList.remove(UID);
                        Server.secretKeyTimer.remove(UID);
                        Server.checkKeyList();
                        break; //Vòng lặp sẽ ngừng khi Client gửi lệnh "bye"
                    }
                    System.out.println("Server get: " + line + " from " + fromIP + " - ID: " + UID);

                    //Xử lý dữ liệu bằng class ServerHandler method responseHandle
                    ClientDataPacket packet = Server.requestHandle(line, secretKey);
                    line = Server.responseHandle(packet, secretKey);

                    // Server gửi phản hồi ngược lại cho client (chuỗi đảo ngược)
                    send(line);
                    System.out.println("Server response: " + line + " to " + fromIP + " - ID: " + UID);
                } catch (SocketException | NullPointerException ignore) {
                    break;
                }
            }

            //Thực hiện đóng kết nối socket và đóng cổng đầu vào (in) + đầu ra (out).
            close();
            System.out.println("Server closed connection with " + fromIP + "- ID: " + UID);
        } catch (IOException ignored) {}
    }

    public void send(String data) throws IOException, SocketException {
        out.write(data);
        out.newLine();
        out.flush();
    }

    public String receive() throws IOException, NullPointerException {
        return in.readLine();
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
