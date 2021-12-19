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
