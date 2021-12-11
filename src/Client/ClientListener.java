package Client;

import Server.ServerDataPacket;

import java.awt.*;
import java.util.Arrays;

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

                ServerDataPacket serverPacket = Client.responseHandle(response);
                System.out.println("Result:\n" + serverPacket.pack());

                //noinspection EnhancedSwitchMigration
                switch (serverPacket.getDescription()) { //ĐỌc HEADER
                    case "COMPILE":
                        String output = serverPacket.getOutput().toLowerCase();
                        boolean isError = false;
                        for(String listItem : syntaxError){
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
                        Client.Frame.appendProcess("Formatted (" + serverPacket.getCpuTime() + "ms)\n");
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
            Client.close();
            System.out.println("Server closed.");
            Client.Frame.appendProcess("Disconnected.");
        }
    }
}
