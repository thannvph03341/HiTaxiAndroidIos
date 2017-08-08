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
import hiappvn.com.hitaxi.models.ModelHangTaxi;

/**
 * Created by Dominions on 11/1/2017.
 */

public class AdapterSpinerHangTaxi extends ArrayAdapter<ModelHangTaxi> {

    Context context;
    int resource;
    List<ModelHangTaxi> objects;
    public AdapterSpinerHangTaxi(Context context, int resource, List<ModelHangTaxi> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ModelHangTaxi modelHangTaxi = this.objects.get(position);
        View view = LayoutInflater.from(context).inflate(this.resource, parent, false);
        TextView tenHang = (TextView) view.findViewById(R.id.txtTenHangsp);
        tenHang.setText(modelHangTaxi.getTenhangtaxi());
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ModelHangTaxi modelHangTaxi = this.objects.get(position);
        View view = LayoutInflater.from(context).inflate(this.resource, parent, false);
        TextView tenHang = (TextView) view.findViewById(R.id.txtTenHangsp);
        tenHang.setText(modelHangTaxi.getTenhangtaxi());
        return view;
    }
}
