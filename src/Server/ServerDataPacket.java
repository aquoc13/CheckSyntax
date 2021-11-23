package Server;

import com.google.gson.*;

public class ServerDataPacket {
    private final String description;   //mô tả xử lý
    private final String output;        //kết quả trả về
    private final String statusCode;    //mã trạng thái
    private final String memory;        //bộ nhớ sử dụng
    private final String cpuTime;       //thời gian xử lý

    /**
     * Khởi tạo data packet cho server từ dữ liệu JSON
     * @param dataPacketJSON dữ liệu dạng JSON
     */
    public ServerDataPacket(String dataPacketJSON) {
        JsonObject packet = JsonParser.parseString(dataPacketJSON).getAsJsonObject();
        this.description = packet.get("description").getAsString();
        this.output = packet.get("output").getAsString();
        this.statusCode = packet.get("statusCode").getAsString();
        this.memory = packet.get("memory").getAsString();
        this.cpuTime = packet.get("cpuTime").getAsString();
    }

    public ServerDataPacket(String Description, String output, String statusCode, String memory, String cpuTime) {
        this.description = Description;
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
        packet.addProperty("output", output);
        packet.addProperty("statusCode", statusCode);
        packet.addProperty("memory", memory);
        packet.addProperty("cpuTime", cpuTime);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(packet.toString());
        return gson.toJson(je);
    }

    public String getDescription() {
        return description;
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
