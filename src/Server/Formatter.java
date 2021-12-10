package Server;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.File;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Formatter {
    final String URL = "https://www.tutorialspoint.com/online_java_formatter.htm";
    final String filePath;

    public Formatter (String filePath) {
        this.filePath = filePath;
    }

    public String format() {
        String formated = "";
        String javaScriptCode;
        Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        try {
            final HtmlPage page = webClient.getPage(URL);

            // Bốc lấy form uploadFile
            Thread.sleep(4000); // Chờ page tải xong toàn bộ trang
            final HtmlFileInput fileInput = page.getHtmlElementById("uploadFile");
            File scriptFile = new File(filePath);
            fileInput.setFiles(scriptFile);

            //Xem đoạn script đã tải lên
            Thread.sleep(3000); // Chờ page load xong file
            javaScriptCode = "editor.getValue()";
            page.executeJavaScript(javaScriptCode).getJavaScriptResult().toString();
            //System.out.println(formated);


            //Click nút format
            HtmlElement btn_Beautify = page.getHtmlElementById("beautify");
            btn_Beautify.click();

            //Lấy script formated
            Thread.sleep(4000); // Chờ page thực thi format xong
            javaScriptCode = "outputeditor.getValue()";
            formated = page.executeJavaScript(javaScriptCode).getJavaScriptResult().toString();
            //System.out.println(formated);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        return formated;
    }

    public static void main (String[] args) {
//        long start = System.currentTimeMillis();
//        Formatter formatter = new Formatter("demoFiles/helloName.java");
//        System.out.println(System.currentTimeMillis() - start);

        // testcase
        Formatter formatter = new Formatter("demoFiles/helloName.java");
        StringTokenizer string;
        String temp;
        for (int i=1; i<=100; i++) {
            long start = System.currentTimeMillis();
            string = new StringTokenizer(formatter.format(), "\n");
            temp = string.nextToken();
            temp = temp.substring(0, temp.length()-1);
            System.out.println(temp);
            if (!temp.equals("import java.io.*;")) {
                System.out.println("false");
                break;
            }
            System.out.println(i + " " + (System.currentTimeMillis()-start));
        }
        System.out.println("Done");
    }
}
