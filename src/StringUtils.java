public class StringUtils {
    /**
     * Cắt đoạn string ra thành 1 dòng cuối cùng và phần còn lại.
     * @param data source code cần xử lý
     * @return String[0]: đoạn code còn lại,
     *         String[1]: dòng cuối từ đoạn code,
     *         NULL nếu dòng cuối không có ký tự nào
     */
    public static String[] cutLastLine(String data) {
        final int NUMBER_OF_STRING_WANT_TO_CUT = 2;                     //cắt chuỗi ra làm 2 phần
        final int lastLineBreak_Index = data.lastIndexOf("\n");     //vị trí của "\n" cuối cùng
        final int start_Index = 0;                                     //vị trí bắt đầu chuỗi
        final int end_Index = data.length() - 1;                       //vị trí kết thúc chuỗi

        //"\n" cuối cùng không được nằm ở đầu hay cuối đoạn code
        if (lastLineBreak_Index != start_Index &&
            lastLineBreak_Index != end_Index) {
            String[] str = new String[NUMBER_OF_STRING_WANT_TO_CUT];
            //Cắt chuỗi string từ đầu tới dấu xuống dòng "\n" cuối cùng.
            str[0] = data.substring(0, lastLineBreak_Index - 1);
            //Cắt chuỗi string từ dấu xuống dòng "\n" cuối cùng tới hết.
            str[1] = data.substring(lastLineBreak_Index + 1);
            return str;
        }
        return null;
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
            lastDot_Index != end_Index) {
            //Cắt chuỗi string từ dấu chấm "." cuối cùng tới hết.
            return path.substring(lastDot_Index + 1);
        }
        return null;
    }
}
