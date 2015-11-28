package com.example.ivantishchenko.cloud;

import android.app.DownloadManager;
import android.net.http.AndroidHttpClient;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Ivan Tishchenko on 23.11.2015.
 */
public class HttpManager {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String getData(String uri) throws IOException {
        URL obj = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }

    public static void addData(String uri, String jsonStr) throws IOException, JSONException {/*
        URL url = new URL("http://130.233.42.100:8080/events/create");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");

        con.setRequestProperty("Content-Type", "application/json");
        JSONObject json = new JSONObject();
        json.put("name", "foo" "place", "foo");
        json.put("priority", "foo");
        json.put("with", "foo@gmail.com");
        // application/x-www-form-urlencoded; charset=UTF-8
        String params = json.toString();

        Log.d("HttpManager", "addData   " + params);

        con.setDoOutput(true);
        con.setDoInput(true);
        con.connect();

        OutputStream stream = con.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(stream);
        writer.write(URLEncoder.encode(params, "UTF-8"));
        writer.flush();*/

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url(uri)
                .post(body)
                .build();
        client.newCall(request).execute();
        }

    public static void deleteAllEvents(String uri) throws IOException {

        URL url = null;
        try {
            url = new URL(uri);
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        }
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            httpURLConnection.setRequestMethod("DELETE");
            System.out.println(httpURLConnection.getResponseCode());
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

    }


}
