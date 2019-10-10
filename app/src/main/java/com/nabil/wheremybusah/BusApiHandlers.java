package com.nabil.wheremybusah;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
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

        @Override
        protected void onPreExecute() {
            activity.findViewById(R.id.progress_bar_loading_data).setVisibility(View.VISIBLE);
        }

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
                res.close();
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
            activity.findViewById(R.id.progress_bar_loading_data).setVisibility(View.INVISIBLE);

            if(jsonArray != null && jsonArray.length() > 0){
                // DataHandler is a class which is self-explanatory, needs the activity to set the activity!
                dataHandler = new DataHandler(activity, jsonArray);
            }else if(jsonArray == null){
                Toast.makeText(activity.getApplicationContext(), "Check your internet connection and try again later!", Toast.LENGTH_SHORT).show();
            }else if(jsonArray.length() == 0){
                Toast.makeText(activity.getApplicationContext(), "Please enter a valid bus-stop code!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(activity.getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
