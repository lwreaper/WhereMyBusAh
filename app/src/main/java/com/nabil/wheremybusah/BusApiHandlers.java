package com.nabil.wheremybusah;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BusApiHandlers {

    // Variable for activity to access views in MainActivity
    Activity activity;
    DataHandler dataHandler;

    // Constructor
    public BusApiHandlers(Activity _activity) {

        // Pass the activity for the onPostExectute method!
        this.activity = _activity;
    }

    // main fetchApi method which runs the FetchApi Class
    public void fetchApi(String bus_stop_code) {
        String URL = "https://arrivelah.herokuapp.com/?id=" + bus_stop_code;

        // Run the AsyncTask
        new FetchApi().execute(URL);
    }

    // AsyncTask
    public class FetchApi extends AsyncTask<String, Void, JSONArray> {

        JSONArray data = null;

        // Get the data!
        @Override
        protected JSONArray doInBackground(String... params) {
            String url = params[0];

            OkHttpClient client = new OkHttpClient();

            Request req = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response res = client.newCall(req).execute();
                data = new JSONObject(res.body().string()).getJSONArray("services");
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }

            // DEBUG ONLY! (To see response)
//            System.out.println(data);

            return data;
        }

        // After getting data
        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if(jsonArray != null && jsonArray.length() > 0){
                Toast.makeText(activity.getApplicationContext(), "DONE!", Toast.LENGTH_SHORT).show();

                // DataHandler is a class which is self-explanatory, needs the activity to set the activity!
                dataHandler = new DataHandler(activity, jsonArray);
            }else{
                Toast.makeText(activity.getApplicationContext(), "CHECK INTERNET CONNECTION OR WRONG BUS STOP NUMBER!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
