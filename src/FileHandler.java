import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class FileHandler {
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
            return file.getAbsolutePath(); //lầy đường dẫn url từ file
        }
        return null;
    }

    /**
     * Đọc dữ liệu từ File có đường dẫn url
     * @param url đường dẫn FILE
     * @return String - dữ liệu đọc từ FIle, trả về NULL nếu không thể mở file
     */
    @SuppressWarnings("MismatchedQueryAndUpdateOfStringBuilder")
    public static String read(String url) {
        StringBuilder builder = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(url);
            Scanner scanner = new Scanner(fileInputStream);
            //Đọc và thêm từng dòng dữ liệu từ file vào StringBuilder
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
            }
            scanner.close();
            fileInputStream.close();
            return builder.toString();
        } catch (IOException ignored) {
            return null; //trả về chuỗi null nếu không thể đọc file
        }
    }

    /**
     * Viết dữ liệu vào File có đường dẫn url
     * @param url đường dẫn FILE
     * @param data dữ liệu đầu vào kiểu String muốn viết vào FILE
     * @return Boolean - True nếu viết thành công và FALSE nếu thất bại
     */
    public static Boolean write(String url, String data) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(url);
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
