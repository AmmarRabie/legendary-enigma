package com.android.example.studyStation.server;

/**
 * Created by AmmarRabie on 21/09/2017.
 */

import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;


/**
 * this class to deal with the server and the database,, it should call AppData directory a lot :)
 */
public class ServerUtility {

    public static final String BASE_URL = "http://192.168.1.22:5000";


    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = ServerUtility.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a  object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private ServerUtility() {
    }

    /**
     * use GET method to retrieve a token from the given url of this email and password
     *
     * @param requestUrl
     * @param email
     * @param pass
     * @return the token in json form ({"token":theactualtoken})
     */
    public static String getToken(String requestUrl, String email, String pass) {
        // Create URL object
        requestUrl = requestUrl.replaceAll(" ", "%20");
        URL url = createUrl(BASE_URL + requestUrl);

//        String encoding = Base64.encodeToString(("test1:test1").getBytes(‌"UTF‌​-8"​));

        String authStr = email + ":" + pass;
        String authEncoded = Base64.encodeToString(authStr.getBytes(), 0);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url, null, authEncoded);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        //
        return jsonResponse;
    }


    /**
     * tested = true
     * use GET method to retrieve a response from given url
     *
     * @param requestUrl the url specified to your insertion function
     * @return the response returned from the api if succeeded, null other wise
     */
    public static String getResponse(String requestUrl, @Nullable String token) {
        // Create URL object
        requestUrl = requestUrl.replaceAll(" ", "%20");
        URL url = createUrl(BASE_URL + requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url, token, null);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        //
        return jsonResponse;
    }


    /**
     * tested = true
     * use post method to send data to api
     *
     * @param url       the url specified to your insertion function
     * @param inputJson data in json form
     * @return the response returned from the api if succeeded, null other wise
     */
    public static String getResponse(String url, String inputJson, @Nullable String token) {
        url = url.replaceAll(" ", "%20");
        InputStream inputStream = null;
        String result = "";
        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(BASE_URL + url);


            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(inputJson, "UTF-8");

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            if (token != null && !token.isEmpty())
                httpPost.setHeader("x-access-token", token);

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null)
                result = readFromStream(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
//            String encodedUrl = URLEncoder.encode(stringUrl, "UTF-8");
            url = new URL(stringUrl);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url, String token, String authorization) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");

            if (token != null && !token.isEmpty())
                urlConnection.setRequestProperty("x-access-token", token);

            if (authorization != null)
                urlConnection.setRequestProperty("Authorization", "Basic " + authorization);

            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            int status = urlConnection.getResponseCode();
            if (status >= 200 && status < 300) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    /**
     * to get the time from the server not from the system (it is more accurate and ensure the data will be valid)
     *
     * @return
     */
    public static long getCurrentTime() {
        // TODO: connect to the server and get the standard time
        // note that, the caller should cast this time as he want to get the time in his location and its required format

        // for now return the time of the system
        return System.currentTimeMillis();

    }

}
