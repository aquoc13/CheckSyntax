package Server;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Formatter {
    final String URL = "https://www.tutorialspoint.com/online_java_formatter.htm";

    public Formatter (String script) {
        String javaScriptCode;
        Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        try {
            final HtmlPage page = webClient.getPage(URL);
            // Truyền script lên tutorialspoint server
//            javaScriptCode = "editor.setValue(" + script + ")";
//            page.executeJavaScript(javaScriptCode);

            //HtmlElement btn_Beautify = page.getHtmlElementById("beautify");
            //btn_Beautify.click();

            // Để chờ page load xong javaScript
            //System.out.println("Khoan, dừng khoảng chừng là 2s");
            Thread.sleep(1000);
            javaScriptCode = "outputeditor.getValue()";
            String formated = "";
            while (true) {
                formated = page.executeJavaScript(javaScriptCode).getJavaScriptResult().toString();
                if (formated.equals("\n")) {
                    //System.out.println("1");
                    Thread.sleep(200);
                }
                else
                    break;
            }
            System.out.println(formated);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    public static void main (String[] args) {
        //long start = System.currentTimeMillis();
        Formatter formatter = new Formatter("hello");
        //System.out.println(System.currentTimeMillis() - start);
    }
}
