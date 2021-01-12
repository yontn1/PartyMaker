package com.example.partymaker;
// Copied form BigNerd Book Chapter 25.

import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkFetcher {

    private static final String TAG = "NetworkFetcher";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url= new URL(urlSpec);
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out= new ByteArrayOutputStream();
            InputStream in= connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer= new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();

            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public String fetchItems(String param) {
        try {
            String url= Uri.parse(param).buildUpon().build().toString();
            return getUrlString(url);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
            return null;
        }
    }
}