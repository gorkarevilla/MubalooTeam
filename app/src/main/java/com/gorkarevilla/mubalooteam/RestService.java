package com.gorkarevilla.mubalooteam;

import android.os.AsyncTask;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Web Service to connect to the website to get the JSON
 *
 * @Author Gorka Revilla
 */
public class RestService extends AsyncTask<String, String, String> {

    private String content;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {


        try {
            URL url = new URL(params[0]);

            //Conection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            //Read
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder sb = new StringBuilder();

            //Store line by line in the string
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            content = sb.toString();


        } catch (IOException e) {
            e.printStackTrace();
        }



        return content;
    }


    protected void onPostExecute(Void result) {
        super.onPreExecute();

    }


    public String getContent() {
        return content;
    }
}
