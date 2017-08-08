package hiappvn.com.hitaxi.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import hiappvn.com.hitaxi.models.ModelHangTaxi;
import hiappvn.com.hitaxi.sql.SQLiteConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nongthan on 1/11/17.
 */

public class HangTaxihAsyntask extends AsyncTask<String,Void, List<ModelHangTaxi>> {

    List<ModelHangTaxi> listhangTaxi;

    Context context;
    SQLiteConfig sqLiteConfig;
    public HangTaxihAsyntask(Context context) {
        this.context = context;
    }

    @Override
    protected List<ModelHangTaxi> doInBackground(String... strings) {
        try{
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            sqLiteConfig = new SQLiteConfig(context);
            Request request = new Request.Builder().url(strings[0]).build();
            Response response = okHttpClient.newCall(request).execute();
            JSONArray jsonArray = new JSONArray(response.body().string());
            listhangTaxi = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++){
                listhangTaxi.add(new ModelHangTaxi(
                        jsonArray.getJSONObject(i).getString("Mahangtaxi"),
                        jsonArray.getJSONObject(i).getString("Tenhangtaxi"),
                        jsonArray.getJSONObject(i).getString("Sodienthoai"),
                        jsonArray.getJSONObject(i).getString("Sodienthoai2"),
                        jsonArray.getJSONObject(i).getString("Diachi"),
                        jsonArray.getJSONObject(i).getString("Website"),
                        jsonArray.getJSONObject(i).getString("Khoanggia"),
                        jsonArray.getJSONObject(i).getString("Facebook"),
                        jsonArray.getJSONObject(i).getString("MaTinhthanh"),
                        jsonArray.getJSONObject(i).getString("Hinhanh"),
                        Float.parseFloat(jsonArray.getJSONObject(i).getString("Rate")),
                        Float.parseFloat(jsonArray.getJSONObject(i).getString("Giamocua")),
                        Float.parseFloat(jsonArray.getJSONObject(i).getString("Giakmdau")),
                        Float.parseFloat(jsonArray.getJSONObject(i).getString("Giakmsau")),
                        Float.parseFloat(jsonArray.getJSONObject(i).getString("Khoanggia7cho")),
                        Float.parseFloat(jsonArray.getJSONObject(i).getString("Giamocua7")),
                        Float.parseFloat(jsonArray.getJSONObject(i).getString("Giakmdau7")),
                        Float.parseFloat(jsonArray.getJSONObject(i).getString("Giakmsau7"))));

            }
            sqLiteConfig.ThemHangTaxi(listhangTaxi);
        }catch (Exception e){
            listhangTaxi = sqLiteConfig.listHangTaxi();
            Log.e(getClass().getSimpleName(), "doInBackground => " + e.toString());
        }
        return listhangTaxi;
    }


    @Override
    protected void onPostExecute(List<ModelHangTaxi> modelHangTaxis) {
        super.onPostExecute(modelHangTaxis);

    }
}

