package Server;

import Client.ClientDataPacket;
import Security.AES_Encryptor;
import Services.StringUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.UUID;

public class ServerListener extends Thread implements Runnable{
    protected User user;
    protected Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final String fromIP;

    /**
     * Tạo ra một thread mới để kết nối và xử lý yêu cầu từ phía Client
     * @param clientSocket truyền socket kết nối Client vào
     */
    public ServerListener(Socket clientSocket) throws IOException {
        socket = clientSocket;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
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
            verify();
            //Lặp liên tục để nhận request từ phía Client
            while(true) {
                try {
                    for (User u:Server.users) {
                        System.out.println(u);
                    }
                    //Chờ thông điệp từ Client rồi xử lý
                    String line = receive();

                    if (line.equals(Server.BREAK_CONNECT_KEY)) {
                        break; //Vòng lặp sẽ ngừng khi Client gửi lệnh "bye"
                    }
                    System.out.println("Server get: " + line + " from " + fromIP + " - ID: " + user.getUID());

                    //Xử lý dữ liệu bằng class ServerHandler method responseHandle
                    ClientDataPacket packet = requestHandle(line, user.getSecretKey());
                    line = responseHandle(packet, user.getSecretKey());

                    // Server gửi phản hồi ngược lại cho client (chuỗi đảo ngược)
                    send(line);
                    System.out.println("Server response: " + line + " to " + fromIP + " - ID: " + user.getUID());
                } catch (SocketException | NullPointerException ignore) {
                    break;
                }
            }

            //Thực hiện đóng kết nối socket và đóng cổng đầu vào (in) + đầu ra (out).
            close();
            System.out.println("Server closed connection with " + fromIP + "- ID: " + user.getUID());
        } catch (IOException ignored) {}
    }

    private void verify() throws IOException {
        String verifyStatus = "Expired";
        do {
            user = new User(receive());
            if (Server.users.contains(user)) {
                for (User u : Server.users) {
                    if (u.equals(user)) {
                        user = u;
                        break;
                    }
                }
                user.setSocket(socket);
                user.setStatus("online");
                if (!user.getSecretKey().equals("Expired"))
                    verifyStatus = "Verified";
            }

            send(verifyStatus);
        } while (verifyStatus.equals("Expired"));
        System.out.println("Client " + fromIP + " is connecting" + " - ID: " + user.getUID() + "- Key: " + user.getSecretKey());
    }

    public void send(String data) throws IOException, SocketException {
        out.write(data);
        out.newLine();
        out.flush();
    }

    public String receive() throws IOException, NullPointerException {
        return in.readLine();
    }

    public String receiveImage(String bytesImage) throws IOException, NullPointerException {
        String path = Server.SERVER_SIDE_PATH + user.getUID() + ".jpg";
        byte[] imageAr = StringUtils.getBytesFromString(bytesImage);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
        ImageIO.write(image, "jpg", new File(path));
        return path;
    }

    public void close() throws IOException {
        in.close();
        out.close();
        user.setStatus("offline");
        user.getSocket().close();
    }

    /**
     * Hàm dùng để xử lý dữ liệu từ Client gửi tới
     * @param data dữ liệu từ client đã bị mã hóa
     * @return ClientDataPacket - Gói dữ liệu Client
     */
    public ClientDataPacket requestHandle(String data, String secretKey) {
        String decryptJson = AES_Encryptor.decrypt(data, secretKey);
        user.addRequestList(JsonParser.parseString(decryptJson).toString(), LocalDateTime.now().toString());
        return ClientDataPacket.unpack(decryptJson); //giả mã bằng secret key
    }

    /**
     * Hàm dùng để xử lý dữ liệu trả về cho client
     * @param dataPacket Gói dự liệu client
     * @return String - dữ liệu đã qua xử lý
     */
    public String responseHandle(ClientDataPacket dataPacket, String secretKey) throws IOException {
        String description = dataPacket.getDescription();
        String format = "CODE";
        String output = "RESULT";
        String statusCode = "000";
        String memory = "000";
        String cpuTime = "0ms";

        switch (dataPacket.getDescription()) { //ĐỌc HEADER
            case "IMAGE" -> {
                String path = receiveImage(dataPacket.getScript());
                output = ImageConverter.toText(path);
            }

            case "COMPILE" -> {
                Compiler compiler = new Compiler(dataPacket.getScript(), dataPacket.getStdin(), dataPacket.getLanguage());
                output = compiler.compile();
                statusCode = compiler.getStatusCode();
                memory = compiler.getMemory();
                cpuTime = compiler.getCpuTime();
            }

            case "FORMAT" -> {}
        }

        ServerDataPacket serverPacket = new ServerDataPacket(description, format, output, statusCode, memory, cpuTime);
        user.addResponseList(JsonParser.parseString(serverPacket.pack()).toString(), LocalDateTime.now().toString());
        return AES_Encryptor.encrypt(serverPacket.pack(), secretKey); //mã hóa bằng secret key trước khi gửi
    }
}
