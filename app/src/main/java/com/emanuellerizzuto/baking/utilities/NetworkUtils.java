package com.emanuellerizzuto.baking.utilities;

import com.emanuellerizzuto.baking.config.Config;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils
{
    public static String getRecipes() throws IOException {
        URL url = getUrl();
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        try {
            scanner.useDelimiter("\\A");
            String result = "";
            while (scanner.hasNext()) {
                result += scanner.next();
            }

            if (result != "") {
                return  result;
            } else {
                scanner.close();
                return null;
            }

        } finally {
            scanner.close();
            urlConnection.disconnect();
        }
    }

    private static URL getUrl() {
        Uri buildUri = Uri.parse(Config.API_URL).buildUpon().build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
