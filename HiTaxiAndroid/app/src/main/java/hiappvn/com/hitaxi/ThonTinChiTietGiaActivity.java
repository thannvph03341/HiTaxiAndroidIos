package hiappvn.com.hitaxi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

import hiappvn.com.hitaxi.models.ModelHangTaxi;

public class ThonTinChiTietGiaActivity extends AppCompatActivity {

    TextView txtGiamocua, txtGiakmdau, txtGiakmsau, txtGiamocua7, txtGiakmdau7, txtGiakmsau7, txtTenHang;
    Button btnOk;
    DecimalFormat decimalFormat = new DecimalFormat("#,### đ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thon_tin_chi_tiet_gia);
        txtGiamocua = (TextView) findViewById(R.id.txtGiaMoCua4Cho);
        txtGiakmdau = (TextView) findViewById(R.id.txtGiakmdau4Cho);
        txtGiakmsau = (TextView) findViewById(R.id.txtGiakmsau4Cho);
        txtGiamocua7 = (TextView) findViewById(R.id.txtGiaMoCua7Cho);
        txtGiakmdau7 = (TextView) findViewById(R.id.txtGiakmdau7Cho);
        txtGiakmsau7 = (TextView) findViewById(R.id.txtGiakmsau7Cho);
        txtTenHang = (TextView) findViewById(R.id.txtTenHang);
        btnOk = (Button) findViewById(R.id.btnOk);
        try {
            ModelHangTaxi modelHangTaxi = (ModelHangTaxi) getIntent().getBundleExtra("hangTaxi").getSerializable("hangTaxi");
            txtGiamocua.setText(decimalFormat.format(modelHangTaxi.getGiamocua()));
            txtGiakmdau.setText(decimalFormat.format(modelHangTaxi.getGiakmdau()));
            txtGiakmsau.setText(decimalFormat.format(modelHangTaxi.getGiakmsau()));
            txtGiamocua7.setText(decimalFormat.format(modelHangTaxi.getGiamocua7()));
            txtGiakmdau7.setText(decimalFormat.format(modelHangTaxi.getGiamocua7()));
            txtGiakmsau7.setText(decimalFormat.format(modelHangTaxi.getGiamocua7()));
            txtTenHang.setText(modelHangTaxi.getTenhangtaxi());


        } catch (Exception e) {
            Log.e(getLocalClassName(), e.toString());
        }
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
