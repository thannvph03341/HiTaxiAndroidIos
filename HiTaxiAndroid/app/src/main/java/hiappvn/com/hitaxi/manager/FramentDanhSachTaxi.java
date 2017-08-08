package hiappvn.com.hitaxi.manager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import hiappvn.com.hitaxi.R;
import hiappvn.com.hitaxi.adapters.AdapterHangTaxi;
import hiappvn.com.hitaxi.asynctask.AsyncResponse;
import hiappvn.com.hitaxi.asynctask.HangTaxiTheoTinhThanhAsyntask;
import hiappvn.com.hitaxi.models.ModelHangTaxi;
import hiappvn.com.hitaxi.thuvien.LinkData;

/**
 * Created by nongthan on 1/9/17.
 */

public class FramentDanhSachTaxi extends Fragment implements AsyncResponse{

    RecyclerView recyDanhSachTaxi;
    AdapterHangTaxi adapterHangTaxi;
    List<ModelHangTaxi> listHang;
    private static View viewMain;
    CheckBox ckTotNhat, ckReNhat;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (viewMain != null) {
            ViewGroup group = (ViewGroup) viewMain.getParent();
            if (group != null) {
                group.removeView(viewMain);
            }
        }
        try {
            viewMain = LayoutInflater.from(getContext()).inflate(R.layout.frament_danh_sach_taxi_layout, container, false);
            addControllView(viewMain);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "onCreateView => " + e.toString());
        }
        return viewMain;


    }

    private void addControllView(View view) {

        listHang = new ArrayList<>();
        try {
            listHang = new HangTaxiTheoTinhThanhAsyntask(getContext()).execute(LinkData.linkHangTaxi, "danang").get();
            recyDanhSachTaxi = (RecyclerView) view.findViewById(R.id.recyDanhSachTaxi);
            recyDanhSachTaxi.setHasFixedSize(true);
            recyDanhSachTaxi.setLayoutManager(new LinearLayoutManager(getContext()));
            adapterHangTaxi = new AdapterHangTaxi(getContext(), listHang);
            ckTotNhat = (CheckBox) view.findViewById(R.id.ckTotNhat);
            ckReNhat = (CheckBox) view.findViewById(R.id.ckReNhat);
            recyDanhSachTaxi.setAdapter(adapterHangTaxi);
            ckTotNhat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocHangTaxi();
                }
            });

            ckReNhat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocHangTaxi();
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


    private void LocHangTaxi(){
        List<ModelHangTaxi> hangTaxis = new ArrayList<>();

           if (ckReNhat.isChecked() && ckTotNhat.isChecked()){
               Collections.sort(listHang, new Comparator<ModelHangTaxi>() {
                   @Override
                   public int compare(ModelHangTaxi t0, ModelHangTaxi t1) {
                       return t1.getRate().compareTo(t0.getRate());
                   }
               });

               if (listHang.size() > 0){
                   hangTaxis.add(listHang.get(0));

                   Collections.sort(listHang, new Comparator<ModelHangTaxi>() {
                       @Override
                       public int compare(ModelHangTaxi t0, ModelHangTaxi t1) {
                           return t0.getGiakmsau().compareTo(t1.getGiakmsau());
                       }
                   });

                   hangTaxis.add(listHang.get(0));
                   adapterHangTaxi = new AdapterHangTaxi(getContext(), hangTaxis);
                   recyDanhSachTaxi.setAdapter(adapterHangTaxi);
               }
           } else {
               if (ckTotNhat.isChecked()){

                   Collections.sort(listHang, new Comparator<ModelHangTaxi>() {
                       @Override
                       public int compare(ModelHangTaxi t0, ModelHangTaxi t1) {
                           return t1.getRate().compareTo(t0.getRate());
                       }
                   });

                   if (listHang.size() > 0){
                       hangTaxis.add(listHang.get(0));
                       adapterHangTaxi = new AdapterHangTaxi(getContext(), hangTaxis);
                       recyDanhSachTaxi.setAdapter(adapterHangTaxi);
                   }

               }

               if (ckReNhat.isChecked()){
                   Collections.sort(listHang, new Comparator<ModelHangTaxi>() {
                       @Override
                       public int compare(ModelHangTaxi t0, ModelHangTaxi t1) {
                           return t0.getGiakmdau().compareTo(t1.getGiakmdau());
                       }
                   });
                   if (listHang.size() > 0){
                       hangTaxis.add(listHang.get(0));
                       adapterHangTaxi = new AdapterHangTaxi(getContext(), hangTaxis);
                       recyDanhSachTaxi.setAdapter(adapterHangTaxi);
                   }

               }

               if (!ckReNhat.isChecked() && !ckTotNhat.isChecked()){
                   Collections.sort(listHang, new Comparator<ModelHangTaxi>() {
                       @Override
                       public int compare(ModelHangTaxi t0, ModelHangTaxi t1) {
                           return t1.getRate().compareTo(t0.getRate());
                       }
                   });

                   adapterHangTaxi = new AdapterHangTaxi(getContext(), listHang);
                   recyDanhSachTaxi.setAdapter(adapterHangTaxi);
               }
           }

    }

    @Override
    public String TinhThanhs(String tinhThanh) {
        ckReNhat.setChecked(false);
        ckTotNhat.setChecked(false);
        try {
            listHang = new HangTaxiTheoTinhThanhAsyntask(getContext()).execute(LinkData.linkHangTaxi , tinhThanh.trim()).get();

            Collections.sort(listHang, new Comparator<ModelHangTaxi>() {
                @Override
                public int compare(ModelHangTaxi t0, ModelHangTaxi t1) {
                    return t1.getRate().compareTo(t0.getRate());
                }
            });

            adapterHangTaxi = new AdapterHangTaxi(getContext(), listHang);
            recyDanhSachTaxi.setAdapter(adapterHangTaxi);

        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "setListTinhThanh => " + e.toString());
        }
        return null;
    }


}
