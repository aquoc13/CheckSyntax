package Services;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class FileHandler {
    public static final String[] supportedExtension = new String[] { "java", "py", "cs", "cpp" };
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
        return "";
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
            return builder.toString();
        } catch (IOException ignored) {
            return ""; //trả về chuỗi null nếu không thể đọc file
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
                builder.append(inputLine).append("\n");

            in.close();
            return builder.toString();
        } catch (IOException ignored) {
            return ""; //trả về chuỗi null nếu không tải dữ liệu từ web
        }
    }

    /**
     * Viết dữ liệu vào File có đường dẫn path
     * @param path đường dẫn FILE
     * @param data dữ liệu đầu vào kiểu String muốn viết vào FILE
     * @return Boolean - True nếu viết thành công và FALSE nếu thất bại
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static Boolean write(String path, String data) {
        try {
            File yourFile = new File(path);
            yourFile.createNewFile(); //Tạo file nếu ko tồn tại
            FileOutputStream fileOutputStream = new FileOutputStream(yourFile, false);
            //Chuyễn dữ liệu kiểu String sang byte[] để viết vào file
            byte[] bytes = data.getBytes();
            fileOutputStream.write(bytes);
            fileOutputStream.close();
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }

    /**
     * Lấy định dạng của File ví dụ: py cho file Python abc.py
     * @param path đường dẫn file
     * @return String - đuôi file,
     *         NULL nếu url không đúng định dạng
     */
    public static String getFileExtension(String path) {
        final int lastDot_Index = path.lastIndexOf(".");      //vị trí của "." cuối cùng
        final int start_Index = 0;                               //vị trí bắt đầu chuỗi
        final int end_Index = path.length() - 1;                 //vị trí kết thúc chuỗi

        //"." cuối cùng không được nằm ở đầu hay cuối đoạn code
        if (lastDot_Index != start_Index &&
                lastDot_Index != end_Index && lastDot_Index != -1) {
            //Cắt chuỗi string từ dấu chấm "." cuối cùng tới hết.
            return path.substring(lastDot_Index + 1);
        }
        return "";
    }
}
