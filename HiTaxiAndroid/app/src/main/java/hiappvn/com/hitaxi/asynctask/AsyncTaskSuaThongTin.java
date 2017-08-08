package hiappvn.com.hitaxi.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by VAIO-LAPTOP on 4/24/2017.
 */

public class AsyncTaskSuaThongTin extends AsyncTask<String, Void , Void> {

    Context context ;

    public AsyncTaskSuaThongTin(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        try{

            OkHttpClient okHttpClient  = new OkHttpClient();

            RequestBody requestBody  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(params[1]));
            Request request = new Request.Builder().url(params[0]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            Log.e(getClass().getSimpleName(), response.body().string());

        }catch (Exception e){
            Toast.makeText(context, "Lỗi Không Thể Cập Nhật Thông Tin", Toast.LENGTH_SHORT).show();
            Log.e(getClass().getSimpleName(), e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context, "Sửa Thông Tin Thành Công ", Toast.LENGTH_SHORT).show();
    }
}
