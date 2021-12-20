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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerListener extends Thread implements Runnable{
    protected User user;
    protected Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final String IP;
    private final String fromIP;

    private final ExecutorService formatExecutors = Executors.newSingleThreadExecutor();;

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

            //Xử lý xác minh uid gửi từ Client trước khi cho phép trao đổi data.
            verify();

            //Lặp liên tục để nhận request từ phía Client.
            while(true) {
                try {
                    //Chờ thông điệp từ Client rồi xử lý
                    String line = receive();
                    if (line == null || line.isEmpty() || line.isBlank())
                        continue;

                    System.out.println("Server get: " + line
                            + " from " + fromIP
                            + " - ID: " + user.getUID());
                    //Vòng lặp sẽ ngừng khi Client gửi lệnh "bye"
                    if (line.equals(Server.BREAK_CONNECT_KEY)) {
                        break;
                    }

                    if (line.equalsIgnoreCase("renewed")) {
                        reloadUser(); //đọc lại user từ list user.
                        recheckIfTargetAtManager(user);//For server manager (bỏ qua)
                        continue;
                    }

                    if (user.getSessionTime() == -1) {
                        System.out.println("Secret Key of " + user.getUID() + " expired !");
                        send("Expired");
                        continue;
                    }

                    //Xử lý dữ liệu bằng class ServerHandler method responseHandle
                    ClientDataPacket packet = requestHandle(line, user.getSecretKey());
                    line = responseHandle(packet, user.getSecretKey());

                    // Server gửi phản hồi ngược lại cho client (chuỗi đảo ngược)
                    send(line);

                    System.out.println("Server response: " + line
                            + " to " + fromIP
                            + " - ID: " + user.getUID());
                } catch (Exception e) {
                    //Có exception thì break vòng lặp để close socket.
                    break;
                }
            }

            //Thực hiện đóng kết nối socket và đóng cổng đầu vào (in) + đầu ra (out).
            close();
            System.out.println("Server closed connection with " + fromIP + "- ID: " + user.getUID());
        } catch (IOException | NullPointerException e) {
            //Thực hiện đóng kết nối socket và đóng cổng đầu vào (in) + đầu ra (out).
            try {
                in.close();
                out.close();
                socket.close();
                System.out.println("Server closed connection with " + fromIP + ".");
            } catch (IOException ignored) {}
        }
    }

    private void verify() throws IOException, NullPointerException {
        String verifyStatus = "Expired";
        do {
            try {
                user = new User(receive());
            } catch (IllegalArgumentException e) {
                send(verifyStatus);
                continue;
            }

            //check xem user có status như nào: Expired, Online, Banned.
            //check trong list ban.
            if (Server.banList.containsKey(IP)
                ||Server.banList.containsValue(user.getUID())) {
                send("Banned");
                close();
                throw new IOException();
            }

            //check trong list user.
            for (User u : Server.users) {
                if (u.equals(user)) {
                    if (u.getStatus().equalsIgnoreCase("online")) {
                        verifyStatus = "Duplicated";
                        break;
                    }

                    user = u;
                    user.setSocket(socket);
                    user.setStatus("online");
                    if (user.getSessionTime() != -1)
                        verifyStatus = "Verified";
                    break;
                }
            }

            send(verifyStatus);
        } while (!verifyStatus.equals("Verified"));
        System.out.println("Client " + fromIP
                + " - " + verifyStatus + " with ID: " + user.getUID()
                + " | Key: " + user.getSecretKey());

        //For server manager (bỏ qua)
        recheckIfTargetAtManager(user);
    }

    public void reloadUser() {
        //check trong list user.
        for (User u : Server.users) {
            if (u.getUID().equals(user.getUID())) {
                user = u;
            }
        }
    }

    //For server manager (bỏ qua)
    public static void recheckIfTargetAtManager(User user) {
        String managerCurrentTarget = Server.manager.textField.getText();
        String IP = user.getSocket()
                .getInetAddress().getHostAddress()
                + ":" + user.getSocket().getPort();
        //vd: 127.0.0.1:5013
        if (managerCurrentTarget.equals(user.getUID())
                || IP.contains(managerCurrentTarget)) {
            Server.manager._btnCheck.doClick();
        }
    }

    public void send(String data) throws IOException, NullPointerException {
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

    public void close() throws IOException, NullPointerException {
        in.close();
        out.close();
        //For server manager (bỏ qua)
        if (user.getStatus().equals("online"))
            user.setStatus("offline");

        user.getSocket().close();

        //For server manager (bỏ qua)
        recheckIfTargetAtManager(user);
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
    public String responseHandle(ClientDataPacket dataPacket, String secretKey) throws IOException, NullPointerException, InterruptedException {
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
                formatExecutors.execute(new Runnable() {
                    @Override
                    public void run() {
                        long startTime = System.currentTimeMillis();
                        Formatter formatter = new Formatter(dataPacket.getScript(), dataPacket.getLanguage());
                        String format = formatter.format();
                        String cpuTime = String.valueOf(System.currentTimeMillis() - startTime);
                        ServerDataPacket serverPacket = new ServerDataPacket(
                                        "FORMAT", format, "RESULT", "000", "1 thread", cpuTime);
                        user.addResponseList(JsonParser.parseString(serverPacket.pack()).toString());
                        try {
                            String data = AES_Encryptor.encrypt(serverPacket.pack(), secretKey);
                            send(data);
                            System.out.println("Server response: " + data
                                    + " to " + fromIP
                                    + " - ID: " + user.getUID());
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("failed format");
                        }
                    }
                });
                return "wait";
        }

        ServerDataPacket serverPacket = new ServerDataPacket(description, format, output, statusCode, memory, cpuTime);
        user.addResponseList(JsonParser.parseString(serverPacket.pack()).toString());
        return AES_Encryptor.encrypt(serverPacket.pack(), secretKey); //mã hóa bằng secret key trước khi gửi
    }
}
