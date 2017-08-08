package hiappvn.com.hitaxi.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import hiappvn.com.hitaxi.thuvien.LinkData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Dominions on 11/1/2017.
 */

public class LayThongTinQuangDuong extends AsyncTask<String, Void, JSONObject> {
    JSONObject jsonObject;
    @Override
    protected JSONObject doInBackground(String... strings) {

        try{
            jsonObject = new JSONObject();
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder().url(LinkData.linkLayKhoangCach(strings[0], strings[1])).build();
            Response response = okHttpClient.newCall(request).execute();
            jsonObject = new JSONObject(response.body().string());
        }catch (Exception e){
            Log.e(getClass().getSimpleName(), "LayThongTinQuangDuong => " + e.toString());
        }
        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }
}
