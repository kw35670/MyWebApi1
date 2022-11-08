package com.example.mywebapi1;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// https://beightlyouch.com/blog/programming/asynctask/
public class MyAsync extends AsyncTask<String,Void, String> {
    private String apiUrlStr = "https://kentuck.net/myapisample1.php";
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private MainActivity mainActivity;

    public MyAsync() { }
    public MyAsync(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(apiUrlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection() ;
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect();
            BufferedReader reader = new BufferedReader((new InputStreamReader(con.getInputStream())));
            String line = null;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            Log.i("getAPI", result.toString());
        } catch (Exception e) {
            Log.e("getAPI", e.getMessage());
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            Log.i("onPostExecute", result.toString());
            JSONObject json = new JSONObject(result);
            JSONArray sampleArray = json.getJSONArray("samplelist");
            Log.i("getAPI","sampleArray.length():"+sampleArray.length());
            List<Sample> sampleList = new ArrayList<>();
            for (int i=0; i<sampleArray.length(); i++) {
                JSONObject object = sampleArray.getJSONObject(i);
                String name = object.getString("name");
                JSONArray phoneArray = object.getJSONArray("phones");
                List<String> phoneList = new ArrayList<>();
                for (int j=0; j<phoneArray.length(); j++) {
                    phoneList.add(phoneArray.getString(j));
                }
                sampleList.add(new Sample(name, phoneList));
            }
            mainActivity.setData(sampleList);
        } catch(JSONException e) {
            Log.e("getAPI", e.getMessage());
        }
    }
}
