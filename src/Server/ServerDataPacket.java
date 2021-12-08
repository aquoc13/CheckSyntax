package Server;

import com.google.gson.*;

public class ServerDataPacket {
    private final String description;   //mô tả xử lý
    private final String format;        //code sau khi format
    private final String output;        //kết quả trả về
    private final String statusCode;    //mã trạng thái
    private final String memory;        //bộ nhớ sử dụng
    private final String cpuTime;       //thời gian xử lý

    public ServerDataPacket(String Description, String format, String output, String statusCode, String memory, String cpuTime) {
        this.description = Description;
        this.format = format;
        this.output = output;
        this.statusCode = statusCode;
        this.memory = memory;
        this.cpuTime = cpuTime;
    }

    /**
     * Đóng gói data packet thành chuỗi dữ liệu JSON
     * @return String - dữ liệu dạng JSON
     */
    public String pack() {
        JsonObject packet = new JsonObject();
        packet.addProperty("description", description);
        packet.addProperty("format", format);
        packet.addProperty("output", output);
        packet.addProperty("statusCode", statusCode);
        packet.addProperty("memory", memory);
        packet.addProperty("cpuTime", cpuTime);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(packet.toString());
        return gson.toJson(je);
    }

    /**
     * Khởi tạo data packet cho server từ dữ liệu JSON
     * @param dataPacketJSON dữ liệu dạng JSON
     */
    public static ServerDataPacket unpack(String dataPacketJSON) {
        JsonObject packet = JsonParser.parseString(dataPacketJSON).getAsJsonObject();
        return new ServerDataPacket(
                    packet.get("description").getAsString(),
                    packet.get("format").getAsString(),
                    packet.get("output").getAsString(),
                    packet.get("statusCode").getAsString(),
                    packet.get("memory").getAsString(),
                    packet.get("cpuTime").getAsString());
    }

    public String getDescription() {
        return description;
    }

    public String getFormat() {
        return format;
    }

    public String getOutput() {
        return output;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getMemory() {
        return memory;
    }

    public String getCpuTime() {
        return cpuTime;
    }

    @Override
    public String toString() {
        return pack();
    }
}
