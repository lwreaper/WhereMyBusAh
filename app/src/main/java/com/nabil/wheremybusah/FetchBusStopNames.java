package com.nabil.wheremybusah;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchBusStopNames extends AsyncTask<Void, Void, JSONObject> {

    String busStopName;
    Activity activity;

    public FetchBusStopNames(Activity _activity, String busStopName){
        this.busStopName = busStopName;
        this.activity = _activity;
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
                String bus_stop_code = jsonObject.getString(busStopName);
                Intent i = new Intent(activity.getApplicationContext(), DisplayTiming.class);
                i.putExtra("busStopCode", bus_stop_code);
                activity.startActivity(i);
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
}
