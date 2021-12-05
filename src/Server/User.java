package Server;

import com.google.gson.*;

import java.net.Socket;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class User {
    private final String UID;
    private Socket socket;
    private String secretKey;
    private long sessionTime;
    private String status;
    private String modifiedDate;
    private final HashMap<String, String> requestList;
    private final HashMap<String, String> responseList;

    public User(String UID) {
        this.UID = UUID.fromString(UID).toString();
        requestList = new HashMap<>();
        responseList = new HashMap<>();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUID() {
        return UID;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(long sessionTime) {
        this.sessionTime = sessionTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public HashMap<String, String> getRequestList() {
        return requestList;
    }

    public void addRequestList(String data, String date) {
        this.requestList.put(data,date);
    }

    public HashMap<String, String> getResponseList() {
        return responseList;
    }

    public void addResponseList(String data, String date) {
        this.responseList.put(data,date);
    }

    @Override
    public String toString() {
        JsonObject user = new JsonObject();
        user.addProperty("UID", UID);
        user.addProperty("socket", socket.toString());
        user.addProperty("secretKey", secretKey);
        user.addProperty("sessionTime", sessionTime);
        user.addProperty("status", status);
        user.addProperty("modifiedDate", modifiedDate);
        user.addProperty("requestList", requestList.toString());
        user.addProperty("responseList", responseList.toString());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(user.toString());
        return gson.toJson(je);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUID().equals(user.getUID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUID());
    }
}
