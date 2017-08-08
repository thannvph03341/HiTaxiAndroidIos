package hiappvn.com.hitaxi.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import hiappvn.com.hitaxi.R;
import hiappvn.com.hitaxi.models.ModelThongBao;

/**
 * Created by nongthan on 1/10/17.
 */

public class AdapterThongBao extends ArrayAdapter<ModelThongBao> {

    Context context;
    int resource;
    List<ModelThongBao> objects;



    public AdapterThongBao(Context context, int resource, List<ModelThongBao> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(this.resource, parent, false);
        ModelThongBao thongBao = this.objects.get(position);
        TextView txtTieuDe = (TextView) view.findViewById(R.id.txtTieuDeTB);
        TextView txtNoiDungTB = (TextView) view.findViewById(R.id.txtNoiDungTB);
        txtTieuDe.setText(thongBao.getTieuDeThongBao());
        txtNoiDungTB.setText(thongBao.getNoiDungThongBao());
        return view;
    }
}
