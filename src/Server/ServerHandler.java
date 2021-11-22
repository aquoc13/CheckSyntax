public class ServerHandler {
    /**
     * Hàm dùng để xử lý dữ liệu từ Client gửi tới
     * @param input dữ liệu đầu vào kiểu String
     * @return String - dữ liệu đã qua xử lý
     */
    public static String requestHandle(String input) {
        //demo only
        StringBuilder builder = new StringBuilder(input);
        return builder.reverse().toString();
    }
}
