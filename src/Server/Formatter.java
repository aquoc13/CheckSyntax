package Server;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.File;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static Services.StringUtils.convertEscapeCharacters;
import static Services.FileHandler.read;

public class Formatter {
    String URL = "https://www.tutorialspoint.com/";
    private String script, language;
    final private WebClient webClient;

    /**
     * @param language Truyền đúng cú pháp: java, python3, cpp, php, c
     */
    public Formatter (String script, String language) {
        this.script = convertEscapeCharacters(script);

        if (language.equals("python3"))
            language = "python";
        else if (language.equals("cpp"))
            language= "c";
        this.language = language;
        this.URL = URL + "online_" + language + "_formatter.htm";
        //System.out.println(this.URL);

        webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
    }

    public String format() {
        String javaScriptCode, formated = "";
        try {
            final HtmlPage page = webClient.getPage(URL);

            //Đẩy script lên
            javaScriptCode = "editor.setValue('" + script + "')";
            page.executeJavaScript(javaScriptCode);

            //Click nút format
            HtmlElement btn_Beautify = page.getHtmlElementById("beautify");
            btn_Beautify.click();

            //Lấy script formated
            webClient.waitForBackgroundJavaScript(2*1000); //Đợi page thực thi format
            javaScriptCode = "outputeditor.getValue()";
            formated = page.executeJavaScript(javaScriptCode).getJavaScriptResult().toString();
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        return formated;
    }

    public static void main (String[] args) {
        // testcase
        Formatter formatter = new Formatter(read("demoFiles/helloName.java"), "java");
        StringTokenizer string;
        String temp;
        long totalTime = 0;
        int times;
        for (times=1; times<=100; times++) {
            long start = System.currentTimeMillis();
            string = new StringTokenizer(formatter.format(), "\n");
            temp = string.nextToken();
            temp = temp.substring(0, temp.length()-1);
            System.out.println(temp);
            if (!temp.equals("import java.io.*")) { // So sánh với hàng đầu tiên của script đã truyền vào
                System.out.println("false");
                break;
            }
            long leftTime = System.currentTimeMillis()-start;
            System.out.println(times + " " + leftTime);
            totalTime += leftTime;
        }
        System.out.println("average time: " + totalTime/times);
    }
}
