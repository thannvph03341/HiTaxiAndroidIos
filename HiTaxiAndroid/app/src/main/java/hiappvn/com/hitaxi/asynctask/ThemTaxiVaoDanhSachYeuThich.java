package hiappvn.com.hitaxi.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Dominions on 13/1/2017.
 */

public class ThemTaxiVaoDanhSachYeuThich extends AsyncTask<String, Void, String> {
    String trangThai = "";
    @Override
    protected String doInBackground(String... strings) {

        try{
            OkHttpClient okHttpClient = new OkHttpClient();
            JSONObject object = new JSONObject();
            object.put("Manguoidung", strings[1]);
            object.put("Mahangtaxi", strings[2]);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(object));
            Request request = new Request.Builder().url(strings[0]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            trangThai = response.body().string();

        }catch (Exception e){
            Log.e(getClass().getSimpleName(), "doInBackground => " + e.toString());
        }

        return trangThai;
    }
}
