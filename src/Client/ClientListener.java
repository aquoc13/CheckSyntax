package Client;

import Security.AES_Encryptor;
import Server.ServerDataPacket;

import java.awt.*;
import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Tạo ra một thread mới để kết nối và xử lý lắng nghe từ phía Server
 */
public class ClientListener extends Thread implements Runnable{
    public static String[] syntaxError = new String[]{"error", "syntax", "invalid", "exception", "wrong", "incorrect"};

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (true) {
                String response = Client.receive(); //Chờ thông điệp từ Server rồi xử lý
                System.out.println("Receive packet: " + response + "\n");
                if (response == null)
                    break;

                if (response.equalsIgnoreCase("Expired")) {
                    System.out.println("Secret Key of this Client expired. Try to make new...");
                    Client.create(Client.line);
                    //Thực hiện kết nối SSL socket tới server verifier.
                    Client.openVerify();

                    Client.sendVerify(); //Gửi lại UID + key
                    try {
                        Client.waitVerify(); //chờ phản hồi ""
                    } catch (SocketTimeoutException e) {
                        e.printStackTrace();
                        throw new IOException("Server verifier not reply.");
                    }

                    System.out.println("Sent " + Client.UID + "|" + Client.secretKey + " to server.");
                    Client.send("renewed");
                    Client.receive();
                    Client.send(Client.currentData);
                    continue;
                }

                ServerDataPacket serverPacket = responseHandle(response);
                System.out.println("Result:\n" + serverPacket.pack());

                //noinspection EnhancedSwitchMigration
                switch (serverPacket.getDescription()) { //ĐỌc HEADER
                    case "COMPILE":
                        String output = serverPacket.getOutput().toLowerCase();
                        boolean isError = false;
                        //check syntax output
                        for (String listItem : syntaxError){
                            if(output.contains(listItem)){
                                isError = true;
                                break;
                            }
                        }
                        if (serverPacket.getMemory().equals("null")
                            || serverPacket.getCpuTime().equals("null")
                            || isError)
                            Client.Frame.compiler.setForeground(new Color(231, 76, 60));
                        else
                            Client.Frame.compiler.setForeground(new Color(117, 236, 99));

                        Client.Frame.compiler.setText(serverPacket.getOutput() + "\n");
                        Client.Frame.prettifyCode.setText(serverPacket.getFormat());
                        Client.Frame.compiler.append("Status code: " + serverPacket.getStatusCode() + "\n");
                        Client.Frame.compiler.append("Memory usage: " + serverPacket.getMemory() + "\n");
                        Client.Frame.compiler.append("CPU time: " + serverPacket.getCpuTime() + "\n");
                        break;

                    case "FORMAT":
                        Client.Frame.prettifyCode.setText(serverPacket.getFormat() + "\n");
                        Client.Frame.appendProcess("Formatted. (" + serverPacket.getCpuTime() + "ms)\n");
                        break;

                    case "IMAGE":
                        Client.Frame.sourceCode.setText(serverPacket.getOutput() + "\n");
                        break;

                    case "CHAT":
                        Client.Frame.appendProcess("Server: " + serverPacket.getOutput());
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Client.close();
            System.out.println("Server closed.");
            Client.Frame.appendProcess("Disconnected.");
        }
    }

    /**
     * Hàm dùng để xử lý dữ liệu để gửi cho client
     * @param language ngôn ngữ lập trình
     * @param stdin đầu vào do người dùng nhập
     * @param script source code
     * @return String - dữ liệu đã qua xử lý và mã hóa
     */
    public static String requestHandle(String description, String language, String stdin, String script) {
        ClientDataPacket clientPacket = new ClientDataPacket(description, language,stdin,script);
        System.out.println("Created client data packet:\n" + clientPacket.pack());
        return AES_Encryptor.encrypt(clientPacket.pack(), Client.secretKey); //mã hóa bằng secret key trước khi gửi
    }

    /**
     * Hàm dùng để xử lý dữ liệu từ Server gửi tới
     * @param data dữ liệu trừ server đã bị mã hóa
     * @return ServerDataPacket - Gói dữ liệu Server
     */
    public static ServerDataPacket responseHandle(String data) {
        return ServerDataPacket.unpack(AES_Encryptor.decrypt(data, Client.secretKey)); //giả mã bằng secret key
    }
}
