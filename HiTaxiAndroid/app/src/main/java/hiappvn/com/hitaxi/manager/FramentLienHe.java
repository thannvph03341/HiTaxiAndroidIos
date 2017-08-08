package hiappvn.com.hitaxi.manager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import hiappvn.com.hitaxi.R;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by nongthan on 1/9/17.
 */

public class FramentLienHe extends Fragment {
    String urlLienHe = "http://hitaxi.vn/dmThongTin/GetThongTin";
    TextView textViewLienHe;
    private static View viewMain;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //  View view = LayoutInflater.from(getContext()).inflate(R.layout.frament_gioi_thieu_layout, container, false);

        if (viewMain != null) {
            ViewGroup group = (ViewGroup) viewMain.getParent();
            if (group != null) {
                group.removeView(viewMain);
            }
        }
        try {
            viewMain = LayoutInflater.from(getContext()).inflate(R.layout.frament_lien_he_layout, container, false);
            textViewLienHe = (TextView) viewMain.findViewById(R.id.txt);
            new LienHe().execute(urlLienHe,"idLienHe");
            addControllView(viewMain);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "onCreateView => " + e.toString());
        }
        return viewMain;
    }

    private void addControllView(View viewMain) {

    }

    public class LienHe extends AsyncTask<String,Void,String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            textViewLienHe.setText(s);


        }

        @Override
        protected String doInBackground(String... params) {
            //String t= "";

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("idThongTin",params[1]);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
                Request request = new Request.Builder().url(params[0]).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                JSONObject js = new JSONObject(response.body().string());
                return js.getJSONObject("data").getString("thongTin");
               //s t = jsonObject.getJSONObject("data").getString("thongTin");

            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
    }
}