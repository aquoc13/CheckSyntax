package Client;

import Server.ServerDataPacket;

import java.awt.*;

/**
 * Tạo ra một thread mới để kết nối và xử lý lắng nghe từ phía Server
 */
public class ClientListener extends Thread implements Runnable{
    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (true) {
                String response = Client.receive(); //Chờ thông điệp từ Server rồi xử lý
                System.out.println("Receive packet: " + response + "\n");

                ServerDataPacket serverPacket = Client.responseHandle(response);
                System.out.println("Result:\n" + serverPacket.pack());

                //noinspection EnhancedSwitchMigration
                switch (serverPacket.getDescription()) { //ĐỌc HEADER
                    case "COMPILE":
                        if (serverPacket.getMemory().equals("null")
                            || serverPacket.getCpuTime().equals("null")
                            || serverPacket.getOutput().toLowerCase().contains("syntax"))
                            Client.Frame.compiler.setForeground(new Color(231, 76, 60));
                        else Client.Frame.compiler.setForeground(new Color(117, 236, 99));
                        Client.Frame.compiler.setText(serverPacket.getOutput() + "\n");
                        Client.Frame.compiler.append("Status code: " + serverPacket.getStatusCode() + "\n");
                        Client.Frame.compiler.append("Memory usage: " + serverPacket.getMemory() + "\n");
                        Client.Frame.compiler.append("CPU time: " + serverPacket.getCpuTime() + "\n");
                        break;

                    case "FORMAT":
                        Client.Frame.prettifyCode.setText(serverPacket.getFormat() + "\n");
                        break;

                    case "IMAGE":
                        Client.Frame.sourceCode.setText(serverPacket.getOutput() + "\n");
                        break;

                    case "CHAT":
                        Client.Frame.process.append("Server: " + serverPacket.getOutput() + "\n");
                        break;
                }

            }
        } catch (Exception e) {
            Client.close();
            System.out.println("Server closed.");
            Client.Frame.process.append("Disconnected.\n");
        }
    }
}
