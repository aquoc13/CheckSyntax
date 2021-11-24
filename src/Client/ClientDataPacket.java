package Client;

import Server.ServerDataPacket;
import com.google.gson.*;

public class ClientDataPacket {
    private final String language;      //ngôn ngữ xử lý vd:java
    private final String versionIndex;  //phiên bản của ngôn ngữ vd: 3 của java là JDK 11.0.4
    private final String stdin;         //input của người dùng
    private final String script;        //Source code

    /**
     * Khởi tạo data packet cho client từ dữ liệu JSON
     * @param dataPacketJSON dữ liệu dạng JSON
     */
    public ClientDataPacket(String dataPacketJSON) {
        JsonObject packet = JsonParser.parseString(dataPacketJSON).getAsJsonObject();
        this.language = packet.get("language").getAsString();
        this.versionIndex = packet.get("versionIndex").getAsString();
        this.stdin = packet.get("stdin").getAsString();
        this.script = packet.get("script").getAsString();
    }

    public ClientDataPacket(String language, String versionIndex, String stdin, String script) {
        this.language = language;
        this.versionIndex = versionIndex;
        this.stdin = stdin;
        this.script = script;
    }

    /**
     * Đóng gói data packet thành chuỗi dữ liệu JSON
     * @return String - dữ liệu dạng JSON
     */
    public String pack() {
        JsonObject packet = new JsonObject();
        packet.addProperty("language", language);
        packet.addProperty("versionIndex", versionIndex);
        packet.addProperty("stdin", stdin);
        packet.addProperty("script", script);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(packet.toString());
        return gson.toJson(je);
    }

    /**
     * Khởi tạo data packet cho client từ dữ liệu JSON
     * @param dataPacketJSON dữ liệu dạng JSON
     */
    public static ClientDataPacket unpack(String dataPacketJSON) {
        JsonObject packet = JsonParser.parseString(dataPacketJSON).getAsJsonObject();
        return new ClientDataPacket(
                packet.get("language").getAsString(),
                packet.get("versionIndex").getAsString(),
                packet.get("stdin").getAsString(),
                packet.get("script").getAsString());
    }

    public String getLanguage() {
        return language;
    }

    public String getVersionIndex() {
        return versionIndex;
    }

    public String getStdin() {
        return stdin;
    }

    public String getScript() {
        return script;
    }

    @Override
    public String toString() {
        return pack();
    }
}
