package Server;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import static Services.StringUtils.convertEscapeCharacters;

public class Compiler {
    private HttpURLConnection JdoodleConnection;
    private String request, console, statusCode, memory, cpuTime;

    /**
     *
     * @param stdin Truyền sẵn dữ liệu đầu vào
     * @param language Truyền đúng cú pháp: java, python3, cpp, php, c
     */
    public Compiler (String script, String stdin, String language) {
        //API: https://docs.jdoodle.com/compiler-api/compiler-api
        String clientId = "e1dc961e8571a5bd10efc68f1fa66d48"; //Đăng nhập rồi lấy ở https://www.jdoodle.com/compiler-api/
        String clientSecret = "99d6af4497da794a0e1e1fe5dd45e9cedb80487feb9da835c5e6153c9d9463a5"; //Đăng nhập rồi lấy ở https://www.jdoodle.com/compiler-api/
        String versionIndex = getVersionIndex(language);
        script = convertEscapeCharacters(script, language);

        try {
            URL url = new URL("https://api.jdoodle.com/v1/execute");
            JdoodleConnection = (HttpURLConnection) url.openConnection();
            JdoodleConnection.setDoOutput(true);
            JdoodleConnection.setRequestMethod("POST");
            JdoodleConnection.setRequestProperty("Content-Type", "application/json");

            request = "{\"clientId\": \"" + clientId
                    + "\",\"clientSecret\":\"" + clientSecret
                    + "\",\"script\":\"" + script
                    + "\",\"stdin\":\"" + stdin
                    + "\",\"language\":\"" + language
                    + "\",\"versionIndex\":\"" + versionIndex + "\"} ";
        } catch (Exception ignored) {}
    }

    /**
     *
     * @return Console
     * @throws IOException Kết nối API thất bại
     */
    public String compile() throws IOException{
        OutputStream outputStream = JdoodleConnection.getOutputStream();
        outputStream.write(request.getBytes());
        outputStream.flush();

        if (JdoodleConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            JdoodleConnection.disconnect();
            console ="Please check your inputs : HTTP error code : " + JdoodleConnection.getResponseCode();
            statusCode = "null";
            memory = "null";
            cpuTime = "null";
            return console;
        }

        BufferedReader bf_InputStream = new BufferedReader(
                new InputStreamReader((JdoodleConnection.getInputStream())));
        String response;
        try {
            if ((response = bf_InputStream.readLine()) != null) {
                JSONObject jsonObject = new JSONObject(response);
                //System.out.println(response);
                console = jsonObject.get("output").toString();
                statusCode = jsonObject.get("statusCode").toString();
                memory = jsonObject.get("memory").toString();
                cpuTime = jsonObject.get("cpuTime").toString();
            }
        } catch (JSONException ignored) {}

        JdoodleConnection.disconnect();
        return console;
    }

    /**
     *
     * @return sec
     */
    public String getCpuTime() {
        return cpuTime;
    }

    /**
     *
     * @return kilobyte
     */
    public String getMemory() {
        return memory;
    }

    public String getStatusCode() {
        return statusCode;
    }

    private String getVersionIndex (String language) {
        switch (language) {
            case "java":
            case "python3":
            case "php":
                return "3";
            case "c":
            case "cpp":
                return "4";
            default:
                return "0";
        }
    }

    // My test ----------------------------------------------------------------------------------------------------------
    /*public static String readFile(String filePatch) throws IOException{
        File file = new File(filePatch);
        Scanner scanner = new Scanner(file);
        String script = "";
        while (scanner.hasNextLine()) {
            script += scanner.nextLine() + "\n";
        }
        return script;
    }

    public static void main(String[] args) throws IOException{
        Compiler compiler;

        System.out.println("java");
        compiler = new Compiler(
                readFile("scriptFIles\\sum.java"), "", "java");
        System.out.println(compiler.compile());
        System.out.println("cpuTime: " + compiler.getCpuTime());
        System.out.println("memory: " + compiler.getMemory());

        System.out.println("\npython");
        compiler = new Compiler(
                readFile("scriptFIles\\sum.py"), "", "python3");
        System.out.println(compiler.compile());
        System.out.println("cpuTime: " + compiler.getCpuTime());
        System.out.println("memory: " + compiler.getMemory());

        System.out.println("\nC++");
        compiler = new Compiler(
                readFile("scriptFIles\\sum.cpp"), "", "cpp");
        System.out.println(compiler.compile());
        System.out.println("cpuTime: " + compiler.getCpuTime());
        System.out.println("memory: " + compiler.getMemory());

        System.out.println("\nC");
        compiler = new Compiler(
                readFile("scriptFiles\\sum.c"), "", "c");
        System.out.println(compiler.compile());
        System.out.println("cpuTime: " + compiler.getCpuTime());
        System.out.println("memory: " + compiler.getMemory());

        System.out.println("\nphp");
        compiler = new Compiler(
                "", "", "php");
        System.out.println(compiler.compile());
        System.out.println("cpuTime: " + compiler.getCpuTime());
        System.out.println("memory: " + compiler.getMemory());
    }*/
}
