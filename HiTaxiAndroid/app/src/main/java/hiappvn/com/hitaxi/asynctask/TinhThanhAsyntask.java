package hiappvn.com.hitaxi.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import hiappvn.com.hitaxi.models.ModelTinhThanh;
import hiappvn.com.hitaxi.sql.SQLiteConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nongthan on 1/10/17.
 */

public class TinhThanhAsyntask extends AsyncTask<String, Void, List<ModelTinhThanh>> {

    List<ModelTinhThanh> listTinhThanhs;
    SQLiteConfig sqLiteConfig;
    Context context;

    public TinhThanhAsyntask(Context context) {
      this.context = context;
    }

    @Override
    protected List<ModelTinhThanh> doInBackground(String... strings) {

        try{
            sqLiteConfig = new SQLiteConfig(context);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder().url(strings[0]).build();
            Response  response = okHttpClient.newCall(request).execute();
            JSONArray jsonArray = new JSONArray(response.body().string());
            listTinhThanhs = new ArrayList<>();
            listTinhThanhs.add(new ModelTinhThanh("danang", "Đà Nẵng"));
            for (int i = 0; i < jsonArray.length(); i++){
                if (!jsonArray.getJSONObject(i).getString("MaTinhthanh").trim().equals("danang")) {
                    listTinhThanhs.add(new ModelTinhThanh(jsonArray.getJSONObject(i).getString("MaTinhthanh").trim(),
                            jsonArray.getJSONObject(i).getString("Tentinhthanh").trim()));
                }

            }
            sqLiteConfig.ThemTinhThanh(listTinhThanhs);
        }catch (Exception e){
            listTinhThanhs = sqLiteConfig.listTinhTHanh();
            Log.e(getClass().getSimpleName(), e.toString());
        }

        return listTinhThanhs;
    }


    @Override
    protected void onPostExecute(List<ModelTinhThanh> modelTinhThanhs) {
        super.onPostExecute(modelTinhThanhs);
//        delegate.setListTinhThanh(modelTinhThanhs);
    }
}
