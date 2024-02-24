import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class NetworkUtil {

    public static void main(String[] args) 
	{
		System.out.println("Hello World!");
        try {
            String postData = "mode=view&ver=NKRV&ver2=NKRV&book" + URLEncoder.encode("1", "UTF-8") + "&chap=" + URLEncoder.encode("44", "UTF-8");
            String response = NetworkUtil.performHttpPost("https://icnu.kr/bible/", postData);
            if (response != null) {
                System.out.println(response);
            } else {
                // 오류 처리
                System.out.println("error");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

	}

    public static String performHttpPost(String urlString, String postData) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String result = null;

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(postData);
            wr.flush();
            wr.close();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();

            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            result = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}

