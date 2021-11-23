package Services;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class FileHandler {
    public static String latestFile;
    /**
     * Mở Dialog để người dùng chọn file muốn thao tác
     * @param frame truyền vào JFrame là parent cho dialog
     * @return String - đường dẫn File, trả về NULL nếu hủy/thoát Dialog
     */
    public static String fileChooser(JFrame frame) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                    "Generic Files (.java, .cs, .cpp, .py)", //mô tả các loại file được phép
                                    "java", "cs", "cpp", "py"); //khai báo các loại file được phép
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);

        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) { //APPROVE_OPTION là kết quả trả về nếu nhấn nút yes/OK trên Dialog
            File file = fileChooser.getSelectedFile(); //lấy file đã chọn
            return file.getAbsolutePath(); //lầy đường dẫn path từ file
        }
        return null;
    }

    /**
     * Đọc dữ liệu từ File có đường dẫn path
     * @param path đường dẫn FILE
     * @return String - dữ liệu đọc từ FIle, trả về NULL nếu không thể mở file
     */
    @SuppressWarnings("MismatchedQueryAndUpdateOfStringBuilder")
    public static String read(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            Scanner scanner = new Scanner(fileInputStream);
            //Đọc và thêm từng dòng dữ liệu từ file vào StringBuilder
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
            fileInputStream.close();
            latestFile = path; //lưu lại đường dẫn file vừa mở
            return builder.toString();
        } catch (IOException ignored) {
            return null; //trả về chuỗi null nếu không thể đọc file
        }
    }

    /**
     * Đọc dữ liệu từ File trên web qua url
     * @param url đường dẫn web lưu FILE
     * @return String - dữ liệu đọc từ FIle, trả về NULL nếu không thể tải file
     */
    public static String readURL(String url) {
        try {
            URL website = new URL(url); //tạo URL
            URLConnection connection = website.openConnection(); //kết nối
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder builder = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                builder.append(inputLine).append("\n");;

            in.close();
            return builder.toString();
        } catch (IOException ignored) {
            return null; //trả về chuỗi null nếu không tải dữ liệu từ web
        }
    }

    /**
     * Viết dữ liệu vào File có đường dẫn path
     * @param path đường dẫn FILE
     * @param data dữ liệu đầu vào kiểu String muốn viết vào FILE
     * @return Boolean - True nếu viết thành công và FALSE nếu thất bại
     */
    public static Boolean write(String path, String data) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            //Chuyễn dữ liệu kiểu String sang byte[] để viết vào file
            byte[] bytes = data.getBytes();
            fileOutputStream.write(bytes);
            fileOutputStream.close();
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }
}
