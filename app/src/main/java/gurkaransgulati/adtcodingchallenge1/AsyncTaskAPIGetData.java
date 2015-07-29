package gurkaransgulati.adtcodingchallenge1;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by gurkarangulati on 7/28/15.
 */
public class AsyncTaskAPIGetData extends AsyncTask<Void, Void, String> {

    URL url;
    String asyncResponse;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        String url_string = "http://api.nytimes.com/svc/topstories/v1/home.json?api-key=15629235341919a7b4b8b6e9344f9bca:6:72095783";
        try {
            url = new URL(url_string);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (url != null){
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            conn.setDoInput(true);
            // Starts the query
            try {
                conn.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int response = 0;
            try {
                response = conn.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println( "The response is: " + response);
            try {
                is = conn.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Convert the InputStream into a string
            try {
                asyncResponse = readIt(is, len);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("asyncResponse : " + asyncResponse);
        return asyncResponse;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
    }

    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
