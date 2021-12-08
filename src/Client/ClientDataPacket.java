package Client;

import com.google.gson.*;

public class ClientDataPacket {
    private final String description;   //mô tả xử lý
    private final String language;      //ngôn ngữ xử lý vd:java
    private final String stdin;         //input của người dùng
    private final String script;        //Source code

    public ClientDataPacket(String description, String language, String stdin, String script) {
        this.description = description;
        this.language = language;
        this.stdin = stdin;
        this.script = script;
    }

    /**
     * Đóng gói data packet thành chuỗi dữ liệu JSON
     * @return String - dữ liệu dạng JSON
     */
    public String pack() {
        JsonObject packet = new JsonObject();
        packet.addProperty("description", description);
        packet.addProperty("language", language);
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
                packet.get("description").getAsString(),
                packet.get("language").getAsString(),
                packet.get("stdin").getAsString(),
                packet.get("script").getAsString());
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
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
