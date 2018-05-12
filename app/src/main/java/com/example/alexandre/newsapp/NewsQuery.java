package com.example.alexandre.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class NewsQuery {

    private final static String GUARDIAN_URL =
            "https://content.guardianapis.com/search?show-fields=thumbnail&order-by=newest&q=news&api-key=test";

    private NewsQuery() {

    }

    public static List<News> extractNews() {

        URL url = createUrl(GUARDIAN_URL);
        String jsonResponse = null;
        Bitmap jsonImage = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("LOG", "Error making http request ", e);
        }

        ArrayList<News> news = new ArrayList<>();
        JSONObject root;

        try {
            root = new JSONObject(jsonResponse);
            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);
                String section = obj.getString("sectionName");
                String date = obj.getString("webPublicationDate");
                String dateb = date.substring(0, 10) + "  " + date.substring(11, 16);
                String title = obj.getString("webTitle");
                String urli = obj.getString("webUrl");
                JSONObject fields = obj.getJSONObject("fields");
                String img = fields.optString("thumbnail");
                jsonImage = extractBmp(img);
                news.add(new News(title, section, dateb, urli, jsonImage));
            }
        } catch (JSONException e) {
            Log.e("LOG", "Error on json response ", e);
        }
        return news;
    }

    private static Bitmap extractBmp(String urli) {
        URL url = createUrl(urli);
        Bitmap jsonImage = null;
        try {
            jsonImage = makeHttpRequestBmp(url);
        } catch (IOException e) {
            Log.e("LOG", "Error getting image: ", e);
        }
        return jsonImage;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("LOG", "Error response code " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("LOG", "Problem retrieving json response ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

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


    private static Bitmap makeHttpRequestBmp(URL url) throws IOException {
        Bitmap jsonImage = null;

        if (url == null) {
            return jsonImage;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonImage = BitmapFactory.decodeStream(inputStream);
            } else {
                Log.e("LOG", "Error response code bitmapHttp " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("LOG", "Problem retrieving json response ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonImage;
    }

    private static URL createUrl(String urls) {
        URL url = null;
        try {
            url = new URL(urls);
        } catch (MalformedURLException e) {
            Log.e("LOG", "Error creating URL ", e);
        }
        return url;
    }

}
