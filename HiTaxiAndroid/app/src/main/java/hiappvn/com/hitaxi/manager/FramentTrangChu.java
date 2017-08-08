package hiappvn.com.hitaxi.manager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import hiappvn.com.hitaxi.R;
import hiappvn.com.hitaxi.adapters.AdapterTrangChuMain;
import hiappvn.com.hitaxi.models.ModelHangTaxi;
import hiappvn.com.hitaxi.sql.SQLiteConfig;
import hiappvn.com.hitaxi.thuvien.LinkData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nongthan on 1/9/17.
 */

public class FramentTrangChu extends Fragment {

    RecyclerView recyMainTrangChu;
    HashMap<Integer, List<ModelHangTaxi>> hashMapTrangChu;
    public static List<ModelHangTaxi> listHangTaxi;
    AdapterTrangChuMain adapterTrangChuMain;
    private static View viewMain;
    SQLiteConfig sqLiteConfig;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sqLiteConfig = new SQLiteConfig(getContext());
        if (viewMain != null) {
            ViewGroup group = (ViewGroup) viewMain.getParent();
            if (group != null) {
                group.removeView(viewMain);
            }
        }
        try {
            viewMain = LayoutInflater.from(getContext()).inflate(R.layout.frament_trang_chu_layout, container, false);
            addControler(viewMain);
        }catch (Exception e){
            Log.e(getClass().getSimpleName(), "onCreateView => " + e.toString());
        }
        return viewMain;
    }

    private void addControler(View view) {
        recyMainTrangChu = (RecyclerView) view.findViewById(R.id.recyMainTrangChu);
//        refreshLayoutTrangChu = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayoutTrangChu);
        recyMainTrangChu.setHasFixedSize(true);
        recyMainTrangChu.setLayoutManager(new LinearLayoutManager(getContext()));
        hashMapTrangChu = new HashMap<>();
        adapterTrangChuMain = new AdapterTrangChuMain(getContext(), hashMapTrangChu);
        recyMainTrangChu.setAdapter(adapterTrangChuMain);
        TextView textView = (TextView) view.findViewById(R.id.txtthongBao);
        textView.setSelected(true);
        /**
         *
         * */

        /**
         * getData taxi
         * */

        try {
            listHangTaxi = new ArrayList<>();

            if (listHangTaxi.size() <= 0) {
                new LayDataHangTaxi().execute(LinkData.linkHangTaxi);
            } else {
                adapterTrangChuMain.notifyDataSetChanged();
            }
        }catch (Exception e) {
            Log.e(getClass().getSimpleName(), "addControler 'xem gia tri tren json tra ve co dung khong! ' => " + e.toString());
        }


    }



    class LayDataHangTaxi extends AsyncTask<String, Void, List<ModelHangTaxi>> {

        List<ModelHangTaxi> listRanh;
        @Override
        protected List<ModelHangTaxi> doInBackground(String... strings) {
            try{
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .writeTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build();
                Request request = new Request.Builder().url(strings[0]).build();
                Response response = okHttpClient.newCall(request).execute();
                JSONArray jsonArray = new JSONArray(response.body().string());

                listHangTaxi.clear();
                for (int i = 0; i < jsonArray.length(); i++){
                    //String mahangtaxi, String tenhangtaxi, String sodienthoai, String sodienthoai2, String diachi, String website, String khoanggia, String facebook, String maTinhthanh, String hinhanh, Float rate, Float giamocua, Float giakmdau, Float giakmsau
                    listHangTaxi.add(new ModelHangTaxi(
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
                /**
                 * Thêm dữ liệu vào data
                 * */
                sqLiteConfig.ThemHangTaxi(listHangTaxi);
            }catch (Exception e){
                listHangTaxi = sqLiteConfig.listHangTaxi();
                Log.e(getClass().getSimpleName(), e.toString());
            }
            return listHangTaxi;
        }

        @Override
        protected void onPostExecute(List<ModelHangTaxi> modelHangTaxis) {
            super.onPostExecute(modelHangTaxis);
            if (listHangTaxi.size() > 0) {

                Collections.sort(modelHangTaxis, new Comparator<ModelHangTaxi>() {
                    @Override
                    public int compare(ModelHangTaxi t0, ModelHangTaxi t1) {
                        return t0.getRate().compareTo(t1.getRate());
                    }
                });

                for (int i = 0; i < 3; i++) {
                    switch (i) {
                        case 0:
                            listRanh = new ArrayList<>();
                            listRanh.clear();

                            //String mahangtaxi, String tenhangtaxi, String sodienthoai, String sodienthoai2, String diachi, String website, String khoanggia, String facebook, String maTinhthanh, String hinhanh, Float rate, Float giamocua, Float giakmdau, Float giakmsau
                            for (int j = 1; j < 4; j++) {
                                listRanh.add(new ModelHangTaxi(modelHangTaxis.get(listHangTaxi.size() - j).getMahangtaxi(),
                                        modelHangTaxis.get(listHangTaxi.size() - j).getTenhangtaxi(),
                                        modelHangTaxis.get(listHangTaxi.size() - j).getSodienthoai(),
                                        modelHangTaxis.get(listHangTaxi.size() - j).getSodienthoai2(),
                                        modelHangTaxis.get(listHangTaxi.size() - j).getDiachi(),
                                        modelHangTaxis.get(listHangTaxi.size() - j).getWebsite(),
                                        modelHangTaxis.get(listHangTaxi.size() - j).getKhoanggia(),
                                        modelHangTaxis.get(listHangTaxi.size() - j).getFacebook(),
                                        modelHangTaxis.get(listHangTaxi.size() - j).getMaTinhthanh(),
                                        modelHangTaxis.get(listHangTaxi.size() - j).getHinhanh(),
                                        modelHangTaxis.get(listHangTaxi.size() - j).getRate(),
                                        modelHangTaxis.get(listHangTaxi.size() - j).getGiamocua(),
                                        modelHangTaxis.get(listHangTaxi.size() - j).getGiakmdau(),
                                        modelHangTaxis.get(listHangTaxi.size() - j).getGiakmsau(),
                                        modelHangTaxis.get(listHangTaxi.size() - j).getKhoanggia7cho(),
                                        modelHangTaxis.get(listHangTaxi.size() - j).getGiamocua7(),
                                        modelHangTaxis.get(listHangTaxi.size() - j).getGiakmdau7(),
                                        modelHangTaxis.get(listHangTaxi.size() - j).getGiakmsau7()));
                            }

                            Collections.sort(listRanh, new Comparator<ModelHangTaxi>() {
                                @Override
                                public int compare(ModelHangTaxi tt1, ModelHangTaxi tt2) {
                                    return tt2.getRate().compareTo(tt1.getRate());
                                }
                            });
                            hashMapTrangChu.put(0, listRanh);
                            break;
                        case 1:
                            listRanh = new ArrayList<>();
                            listRanh.clear();
                            Collections.sort(modelHangTaxis, new Comparator<ModelHangTaxi>() {
                                @Override
                                public int compare(ModelHangTaxi t0, ModelHangTaxi t1) {
                                    return t0.getGiamocua().compareTo(t1.getGiamocua());
                                }
                            });
                            //String mahangtaxi, String tenhangtaxi, String sodienthoai, String sodienthoai2, String diachi, String website, String khoanggia, String facebook, String maTinhthanh, String hinhanh, Float rate, Float giamocua, Float giakmdau, Float giakmsau
                            for (int j = 0; j < 3; j++) {
                                listRanh.add(new ModelHangTaxi(modelHangTaxis.get(j).getMahangtaxi(),
                                        modelHangTaxis.get(j).getTenhangtaxi(),
                                        modelHangTaxis.get(j).getSodienthoai(),
                                        modelHangTaxis.get(j).getSodienthoai2(),
                                        modelHangTaxis.get(j).getDiachi(),
                                        modelHangTaxis.get(j).getWebsite(),
                                        modelHangTaxis.get(j).getKhoanggia(),
                                        modelHangTaxis.get(j).getFacebook(),
                                        modelHangTaxis.get(j).getMaTinhthanh(),
                                        modelHangTaxis.get(j).getHinhanh(),
                                        modelHangTaxis.get(j).getRate(),
                                        modelHangTaxis.get(j).getGiamocua(),
                                        modelHangTaxis.get(j).getGiakmdau(),
                                        modelHangTaxis.get(j).getGiakmsau(),
                                        modelHangTaxis.get(j).getKhoanggia7cho(),
                                        modelHangTaxis.get(j).getGiamocua7(),
                                        modelHangTaxis.get(j).getGiakmdau7(),
                                        modelHangTaxis.get(j).getGiakmsau7()));
                            }

                            Collections.sort(listRanh, new Comparator<ModelHangTaxi>() {
                                @Override
                                public int compare(ModelHangTaxi tt1, ModelHangTaxi tt2) {
                                    return tt1.getGiamocua().compareTo(tt2.getGiamocua());
                                }
                            });

                            hashMapTrangChu.put(1, listRanh);
                            break;

                        case 2:
                            listRanh = new ArrayList<>();
                            listRanh.clear();
                            Collections.sort(modelHangTaxis, new Comparator<ModelHangTaxi>() {
                                @Override
                                public int compare(ModelHangTaxi t0, ModelHangTaxi t1) {
                                    return t0.getRate().compareTo(t1.getRate());
                                }
                            });
                            //String mahangtaxi, String tenhangtaxi, String sodienthoai, String sodienthoai2, String diachi, String website, String khoanggia, String facebook, String maTinhthanh, String hinhanh, Float rate, Float giamocua, Float giakmdau, Float giakmsau
                            for (int j = 0; j < 3; j++) {

                                listRanh.add(new ModelHangTaxi(modelHangTaxis.get(j).getMahangtaxi(),
                                        modelHangTaxis.get(j).getTenhangtaxi(),
                                        modelHangTaxis.get(j).getSodienthoai(),
                                        modelHangTaxis.get(j).getSodienthoai2(),
                                        modelHangTaxis.get(j).getDiachi(),
                                        modelHangTaxis.get(j).getWebsite(),
                                        modelHangTaxis.get(j).getKhoanggia(),
                                        modelHangTaxis.get(j).getFacebook(),
                                        modelHangTaxis.get(j).getMaTinhthanh(),
                                        modelHangTaxis.get(j).getHinhanh(),
                                        modelHangTaxis.get(j).getRate(),
                                        modelHangTaxis.get(j).getGiamocua(),
                                        modelHangTaxis.get(j).getGiakmdau(),
                                        modelHangTaxis.get(j).getGiakmsau(),
                                        modelHangTaxis.get(j).getKhoanggia7cho(),
                                        modelHangTaxis.get(j).getGiamocua7(),
                                        modelHangTaxis.get(j).getGiakmdau7(),
                                        modelHangTaxis.get(j).getGiakmsau7()));
                            }
                            Collections.sort(listRanh, new Comparator<ModelHangTaxi>() {
                                @Override
                                public int compare(ModelHangTaxi tt1, ModelHangTaxi tt2) {
                                    return tt1.getRate().compareTo(tt2.getRate());
                                }
                            });
                            hashMapTrangChu.put(2, listRanh);
                            break;
                    }
                }

                adapterTrangChuMain.notifyDataSetChanged();
//            refreshLayoutTrangChu.setRefreshing(false);
            }
        }
    }


}
