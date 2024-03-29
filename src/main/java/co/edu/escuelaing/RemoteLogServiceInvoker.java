package co.edu.escuelaing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RemoteLogServiceInvoker {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static String[] get_url = null;
    private static int instance = 0;

    public RemoteLogServiceInvoker(String[] invokerUrls) {
        get_url = invokerUrls;
    }

    public static String invoke(String param) throws IOException {
        URL obj = RoundRobin();
        String urlParams = obj.toString() + "?param=" + param;

        HttpURLConnection con = (HttpURLConnection) new URL(urlParams).openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        // The following invocation perform the connection implicitly before getting the
        // code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        StringBuffer response = new StringBuffer();

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        return response.toString();
    }

    /**
     * Select the URL to be invoked
     * 
     * @return the URL of the next log service to be invoked
     * @throws MalformedURLException if there is a malformed URL
     */
    public static URL RoundRobin() throws MalformedURLException {
        if (instance < 2) {
            instance += 1;
        } else {
            instance = 0;
        }
        URL url = new URL(get_url[instance]);
        return url;
    }

}
