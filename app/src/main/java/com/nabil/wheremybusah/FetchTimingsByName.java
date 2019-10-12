package com.nabil.wheremybusah;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchTimingsByName extends AsyncTask<Void, Void, JSONObject> {

    Activity activity;
    String bus_stop_name;

    public FetchTimingsByName (Activity _activity, String bus_stop_name){
        this.activity = _activity;
        this.bus_stop_name = bus_stop_name;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {

        JSONObject result = null;

        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
                .url("https://raw.githubusercontent.com/nabilcreates/BusService/master/data/json_name_code.json")
                .build();

        try{
            Response res = client.newCall(req).execute();
            result = new JSONObject(res.body().string());
        }catch(JSONException | IOException e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if(jsonObject != null){
            try{
                String bus_stop_code = jsonObject.getString(bus_stop_name);
                new FetchTimingsByCode(activity, bus_stop_code).execute();
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
}