package Server;

import java.io.File;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


@SuppressWarnings({"deprecation", "ResultOfMethodCallIgnored"})
public class ImageConverter {
    //api-ninjas.com - image to text api
    private static final String API_URL = "https://api.api-ninjas.com/v1/imagetotext";
    private static final String API_KEY = "2WynZNZLApbR2Mei2lSXOQ==gUkhfpHcJ2QC05HG";

    /**
     * Chuyển ảnh sang text bằng đường dẫn tới FILE ảnh
     * @param path đường dẫn FILE ảnh.
     * @return String - chữ lấy từ ảnh.
     */
    public static String toText(String path) {
        String result = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

            HttpPost httppost = new HttpPost(API_URL);
            httppost.setHeader("X-Api-Key", API_KEY);
            File file = new File(path);

            MultipartEntity mpEntity = new MultipartEntity();
            ContentBody cbFile = new FileBody(file, "image/jpeg");
            mpEntity.addPart("image", cbFile);

            httppost.setEntity(mpEntity);
            //System.out.println("executing request " + httppost.getRequestLine());
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            //System.out.println(response.getStatusLine());
            if (resEntity != null) {
                StringBuilder builder = new StringBuilder();
                String data = EntityUtils.toString(resEntity);
                JSONArray jsonArray = new JSONArray(data);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    builder.append(jsonObject.getString("text")).append("\n");
                }
                result = builder.substring(0, builder.length()/2);

                resEntity.consumeContent();
            }

            httpclient.getConnectionManager().shutdown();
            file.delete();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}