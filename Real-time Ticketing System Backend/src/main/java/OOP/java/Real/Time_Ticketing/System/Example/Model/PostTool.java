package OOP.java.Real.Time_Ticketing.System.Example.Model;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class PostTool {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public void sendPostRequestWithJSON(String urlString, String json) {

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            try(OutputStream outputStream = connection.getOutputStream()){
                byte[] input = json.getBytes();
                outputStream.write(input);

        } //print response msg
            int responseCode = connection.getResponseCode();
            System.out.println("POST request sent to " + responseCode);

            connection.disconnect();
        }catch (Exception e) {
            LoggerFileHandler.Logging(logger,"Error while trying to send post request to"+ urlString,true);

        }

    }

}
