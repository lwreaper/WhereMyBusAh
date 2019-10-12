package com.nabil.wheremybusah;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchTimingsByCode extends AsyncTask<Void, Void, JSONArray> {

    Activity activity;
    String url;

    public FetchTimingsByCode(Activity _activity, String bus_stop_code){
        this.activity = _activity;
        this.url = "https://arrivelah.herokuapp.com/?id=" + bus_stop_code;
    }


    // Get the data!
    @Override
    protected JSONArray doInBackground(Void... voids) {
        JSONArray data = null;

        OkHttpClient client = new OkHttpClient();

        Request req = new Request.Builder()
                .url(url)
                .build();

        try {
            System.out.println(url);
            Response res = client.newCall(req).execute();
            data = new JSONObject(res.body().string()).getJSONArray("services");
            res.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    // After getting data
    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        if(jsonArray != null && jsonArray.length() > 0){

            TimingAdapter adapter = new TimingAdapter(activity, jsonArray);
            ((SwipeRefreshLayout) activity.findViewById(R.id.pull_to_refresh)).setRefreshing(false);
            ((ListView) activity.findViewById(R.id.listView)).setAdapter(adapter);

        }else if(jsonArray == null){
            Toast.makeText(activity.getApplicationContext(), "Check your internet connection and try again later!", Toast.LENGTH_SHORT).show();
        }else if(jsonArray.length() == 0){
            Toast.makeText(activity.getApplicationContext(), "Please enter a valid bus-stop code!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(activity.getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
        }

    }
}