package hiappvn.com.hitaxi.manager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import hiappvn.com.hitaxi.R;
import hiappvn.com.hitaxi.adapters.AdapterThongBao;
import hiappvn.com.hitaxi.models.ModelThongBao;
import hiappvn.com.hitaxi.sql.SQLiteConfig;

/**
 * Created by nongthan on 1/9/17.
 */

public class FramentThongBao extends Fragment {
    AdapterThongBao adapterThongBao;
    List<ModelThongBao> listThongBao;
    ListView lvThongBao;
    SQLiteConfig sqLiteConfig;
    private static View viewMain;
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
            viewMain = LayoutInflater.from(getContext()).inflate(R.layout.frament_thong_bao_layout, container, false);
            addControler(viewMain);
        }catch (Exception e){
            Log.e(getClass().getSimpleName(), "onCreateView => " + e.toString());
        }
        return viewMain;
    }

    private void addControler(View view) {
        lvThongBao = (ListView) view.findViewById(R.id.lvThongBao);
        listThongBao = sqLiteConfig.DanhSachThongBao();
        adapterThongBao = new AdapterThongBao(getContext(), R.layout.item_thong_bao, listThongBao);
        lvThongBao.setAdapter(adapterThongBao);
    }
}
