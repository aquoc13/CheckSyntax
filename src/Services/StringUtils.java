package Services;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class StringUtils {
    /*
      Cắt đoạn string ra thành 1 dòng cuối cùng và phần còn lại.
      @param data source code cần xử lý
     * @return String[0]: đoạn code còn lại,
     *         String[1]: dòng cuối từ đoạn code,
     *         NULL nếu dòng cuối không có ký tự nào
     */
    /*public static String[] cutLastLine(String data) {
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
    }*/

    /*
     *  Return the number of lines of text, including wrapped lines.
     */
    public static int getWrappedLines(JTextArea component)
    {
        View view = component.getUI().getRootView(component).getView(0);
        int preferredHeight = (int)view.getPreferredSpan(View.Y_AXIS);
        int lineHeight = component.getFontMetrics( component.getFont() ).getHeight();
        return preferredHeight / lineHeight;
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

    /**
     * Chuyển Key sang String
     */
    public static String getStringFromKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * Chuyển Byte[] sang String
     */
    public static String getStringFromBytes(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Chuyển String sang Byte[]
     */
    public static byte[] getBytesFromString(String str) {
        return Base64.getDecoder().decode(str);
    }
    /**
     * Format chuỗi code trước khi đẩy api đưa theo ngôn ngữ
     * @return Chuỗi đã format phù hợp để đẩy lên api
     */
    public static String convertEscapeCharacters(String script) {
        script = script
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\t", "\\t");
        return script;
    }

    /**
     * Hàm băm chuỗi
     */
    public static String applySha256(String str, String strSalt) {
        String hashString;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(strSalt.getBytes());
            byte[] bytes = md.digest(str.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes)
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            hashString = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        return hashString;
    }
}
