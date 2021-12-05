package Client;

import Server.ServerDataPacket;

import java.io.IOException;

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
                Client.Frame.process.append("Receive response.\n");

                ServerDataPacket serverPacket = Client.responseHandle(response);
                Client.Frame.process.append("Print result.\n");
                System.out.println("Result:\n" + serverPacket.pack());

                switch (serverPacket.getDescription()) { //ĐỌc HEADER
                    case "COMPILE" -> {
                        Client.Frame.compiler.setText(serverPacket.getOutput() + "\n");
                        Client.Frame.compiler.append("Status code: " + serverPacket.getStatusCode() + "\n");
                        Client.Frame.compiler.append("Memory usage: " + serverPacket.getMemory() + "\n");
                        Client.Frame.compiler.append("CPU time:" + serverPacket.getCpuTime() + "\n");
                    }

                    case "FORMAT" -> Client.Frame.prettifyCode.setText(serverPacket.getFormat() + "\n");

                    case "IMAGE" -> Client.Frame.sourceCode.setText(serverPacket.getOutput() + "\n");
                }

            }
        } catch (IOException | NullPointerException ignored) {
            Client.Frame.process.append("Disconnected.\n");
        }
    }
}
