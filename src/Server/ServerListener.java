package Server;

import Client.ClientDataPacket;
import Security.AES_Encryptor;
import Services.StringUtils;
import com.google.gson.JsonParser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class ServerListener extends Thread implements Runnable{
    protected User user;
    protected Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final String IP;
    private final String fromIP;

    /**
     * Tạo ra một thread mới để kết nối và xử lý yêu cầu từ phía Client
     * @param clientSocket truyền socket kết nối Client vào
     */
    public ServerListener(Socket clientSocket) throws IOException {
        socket = clientSocket;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        IP = clientSocket.getInetAddress() //tách InetAddress từ socket
                .getHostAddress(); //in địa chỉ IP từ InetAddress của client kết nối tới
        fromIP = IP + ":" + clientSocket.getPort(); //in port của client kết nối tới từ socket
                                                    //vd: 127.0.0.1:5013
    }

    /**
     * Sau khi gọi hàm start() từ Thread sẽ tự động chày hàm run()
     */
    @Override
    public void run() {
        try {
            System.out.println("Client " + fromIP + " is connected.");
            verify();
            //Lặp liên tục để nhận request từ phía Client
            while(true) {
                try {
                    //Chờ thông điệp từ Client rồi xử lý
                    String line = receive();

                    if (line.equals(Server.BREAK_CONNECT_KEY)) {
                        break; //Vòng lặp sẽ ngừng khi Client gửi lệnh "bye"
                    }
                    System.out.println("Server get: " + line
                            + " from " + fromIP
                            + " - ID: " + user.getUID());

                    //Xử lý dữ liệu bằng class ServerHandler method responseHandle
                    ClientDataPacket packet = requestHandle(line, user.getSecretKey());
                    line = responseHandle(packet, user.getSecretKey());

                    // Server gửi phản hồi ngược lại cho client (chuỗi đảo ngược)
                    send(line);
                    System.out.println("Server response: " + line
                            + " to " + fromIP
                            + " - ID: " + user.getUID());
                } catch (Exception e) {
                    break;
                }
            }

            //Thực hiện đóng kết nối socket và đóng cổng đầu vào (in) + đầu ra (out).
            close();
            System.out.println("Server closed connection with " + fromIP + "- ID: " + user.getUID());
        } catch (IOException | NullPointerException ignored) {
            //Thực hiện đóng kết nối socket và đóng cổng đầu vào (in) + đầu ra (out).
            try {
                in.close();
                out.close();
                socket.close();
                System.out.println("Server closed connection with " + fromIP + ".");
            } catch (IOException ignored1) {}
        }
    }

    private void verify() throws IOException, NullPointerException {
        String verifyStatus = "Expired";
        do {
            user = new User(receive());
            //check ban
            if (Server.banList.containsKey(IP)
                ||Server.banList.containsValue(user.getUID())) {
                send("Banned");
                close();
                return;
            }

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
        System.out.println("Client " + fromIP
                + " - " + verifyStatus + " with ID: " + user.getUID()
                + " | Key: " + user.getSecretKey());

        //For server manager (ignore)
        recheckIfTargetAtManager();
    }

    //For server manager (ignore)
    private void recheckIfTargetAtManager() {
        String managerCurrentTarget = Server.manager.textField.getText();
        if (managerCurrentTarget.equals(user.getUID())
                || fromIP.contains(managerCurrentTarget)) {
            Server.manager._btnCheck.doClick();
        }
    }

    public void send(String data) throws IOException {
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
        //For server manager (ignore)
        if (user.getStatus().equals("online"))
            user.setStatus("offline");

        user.getSocket().close();

        //For server manager (ignore)
        recheckIfTargetAtManager();
    }

    /**
     * Hàm dùng để xử lý dữ liệu từ Client gửi tới
     * @param data dữ liệu từ client đã bị mã hóa
     * @return ClientDataPacket - Gói dữ liệu Client
     */
    public ClientDataPacket requestHandle(String data, String secretKey) {
        String decryptJson = AES_Encryptor.decrypt(data, secretKey);
        user.addRequestList(JsonParser.parseString(decryptJson).toString());
        user.addDateList(LocalDateTime.now().toString());
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
            case "IMAGE":
                String path = receiveImage(dataPacket.getScript());
                output = ImageConverter.toText(path);
                break;

            case "COMPILE":
                Compiler compiler = new Compiler(dataPacket.getScript(), dataPacket.getStdin(), dataPacket.getLanguage());
                output = compiler.compile();
                statusCode = compiler.getStatusCode();
                memory = compiler.getMemory();
                cpuTime = compiler.getCpuTime();
                break;

            case "FORMAT":
                break;
        }

        ServerDataPacket serverPacket = new ServerDataPacket(description, format, output, statusCode, memory, cpuTime);
        user.addResponseList(JsonParser.parseString(serverPacket.pack()).toString());
        return AES_Encryptor.encrypt(serverPacket.pack(), secretKey); //mã hóa bằng secret key trước khi gửi
    }
}
