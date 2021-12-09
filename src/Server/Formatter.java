package Server;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

public class Formatter {
    final String URL = "https://techiedelight.com/tools/java";
    public Formatter () {
        try {
            final WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            final HtmlPage page = webClient.getPage(URL);
            //System.out.println(page.getTitleText());
            final HtmlTextArea textArea = (HtmlTextArea) page.getFirstByXPath("/html/body/div[2]/div/table/tbody/tr[1]/td[1]/div/div/div/div[2]/div[1]/div/textarea");
            textArea.setText("Hello");
            System.out.println(textArea.getText());
        } catch (Exception ignore) {}
    }

    public static void main (String[] args) {
        Formatter formatter = new Formatter();
    }
}
