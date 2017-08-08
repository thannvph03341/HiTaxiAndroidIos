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
import hiappvn.com.hitaxi.models.ModelTinhThanh;


/**
 * Created by nongthan on 1/2/17.
 */

public class AdapterTinhThanh extends ArrayAdapter<ModelTinhThanh> {

    Context context;
    int resource;
    List<ModelTinhThanh> objects;

    public AdapterTinhThanh(Context context, int resource, List<ModelTinhThanh> objects) {
        super(context, resource,  objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;

    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ModelTinhThanh modelTinhThanh = this.objects.get(position);
        View view = LayoutInflater.from(this.context).inflate(this.resource, parent, false);
        TextView txtTenTinhThanh = (TextView) view.findViewById(R.id.txtTenTinhThanh);
        txtTenTinhThanh.setText(modelTinhThanh.getTentinhthanh());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ModelTinhThanh modelTinhThanh = this.objects.get(position);
        View view = LayoutInflater.from(this.context).inflate(this.resource, parent, false);
        TextView txtTenTinhThanh = (TextView) view.findViewById(R.id.txtTenTinhThanh);
        txtTenTinhThanh.setText(modelTinhThanh.getTentinhthanh());

        return view;
    }
}
