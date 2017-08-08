package hiappvn.com.hitaxi.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import hiappvn.com.hitaxi.R;
import hiappvn.com.hitaxi.models.ModelHangTaxi;


/**
 * Created by nongthan on 1/9/17.
 */

public class AdapterTrangChuMain extends RecyclerView.Adapter<AdapterTrangChuMain.ItemHomeView>{

    Context context;
    HashMap<Integer, List<ModelHangTaxi>> hashMapTrangChu;



    public AdapterTrangChuMain(Context context, HashMap<Integer, List<ModelHangTaxi>> hashMapTrangChu) {
        this.context = context;
        this.hashMapTrangChu = hashMapTrangChu;
    }

    @Override
    public ItemHomeView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_trang_chu_main, parent, false);
        return new ItemHomeView(view);
    }

    @Override
    public void onBindViewHolder(ItemHomeView holder, int position) {
        AdapterHangTaxi adapterHangTaxi = new AdapterHangTaxi(context, hashMapTrangChu.get(position));
        holder.recyXepHangTaxi.setAdapter(adapterHangTaxi);
        switch (position)
        {
            case 0:
                holder.txtDanhMucHang.setText("Taxi Tốt Nhất");
                adapterHangTaxi.notifyDataSetChanged();
                break;
            case 1:
                holder.txtDanhMucHang.setText("Taxi Rẻ Nhất");
                adapterHangTaxi.notifyDataSetChanged();
                break;
            case 2:
                holder.txtDanhMucHang.setText("Taxi Hạn Chế");
                adapterHangTaxi.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return (null != hashMapTrangChu ? hashMapTrangChu.size(): 0);
    }

    class ItemHomeView extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtDanhMucHang;
        public RecyclerView recyXepHangTaxi;

        public ItemHomeView(View itemView) {
            super(itemView);
            txtDanhMucHang = (TextView) itemView.findViewById(R.id.txtDanhMucHang);
            recyXepHangTaxi = (RecyclerView) itemView.findViewById(R.id.recyXepHangTaxi);
            recyXepHangTaxi.setHasFixedSize(true);
            recyXepHangTaxi.setLayoutManager(new LinearLayoutManager(context));
        }

        @Override
        public void onClick(View view) {

        }
    }



}
