package Services;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.security.Key;
import java.util.Base64;

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
     * Copy chuỗi
     * @param textToCopy chuỗi muốn copy
     */
    public static void copyToClipboard(String textToCopy) {
        StringSelection stringSelection = new StringSelection(textToCopy);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public static String getStringFromKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     *
     * @return Chuỗi đã format phù hợp để đẩy lên api
     */
    public static String convertEscapeCharacters(String script, String language) {
        script = script
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\t", "\\t");
        if (!language.equals("php")) //php thì k cần chuyển char[']
            script = script.replace("\'", "\\\'");

        return script;
    }
}
