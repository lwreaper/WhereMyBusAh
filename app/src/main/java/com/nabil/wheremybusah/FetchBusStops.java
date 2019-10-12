package com.nabil.wheremybusah;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchBusStops extends AsyncTask<Void, Void, JSONObject> {

    String busStopNumber;
    String TAG = this.getClass().getSimpleName();
    JSONObject busStopInfo;
    Activity application;

    public FetchBusStops(Activity application, String busStopNumber){
        this.busStopNumber = busStopNumber;
        this.application = application;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {

        JSONObject result = null;

        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
                .url("https://raw.githubusercontent.com/cheeaun/busrouter-sg/master/data/3/stops.json")
                .build();

        try{
            Response res = client.newCall(req).execute();
            result = new JSONObject(res.body().string());
        }catch (JSONException | IOException e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if(jsonObject != null){
            try{
                this.busStopInfo = jsonObject.getJSONObject(this.busStopNumber);
                ((TextView) application.findViewById(R.id.bus_stop_name)).setText(this.busStopInfo.getString("name"));
            }catch(JSONException e){
                e.printStackTrace();
            }
        }else{
            Log.e(TAG, "ERROR FETCHING BUS STOPS");
        }
    }

}
