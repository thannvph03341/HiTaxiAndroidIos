package hiappvn.com.hitaxi.manager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import hiappvn.com.hitaxi.R;
import hiappvn.com.hitaxi.adapters.AdapterSpinerHangTaxi;
import hiappvn.com.hitaxi.adapters.AdapterTinhThanh;
import hiappvn.com.hitaxi.asynctask.HangTaxiTheoTinhThanhAsyntask;
import hiappvn.com.hitaxi.asynctask.LayThongTinQuangDuong;
import hiappvn.com.hitaxi.asynctask.TinhThanhAsyntask;
import hiappvn.com.hitaxi.models.ModelHangTaxi;
import hiappvn.com.hitaxi.models.ModelTinhThanh;
import hiappvn.com.hitaxi.thuvien.LinkData;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

/**
 * Created by nongthan on 1/9/17.
 */

public class FramentUocTinhChiPhiTaxi extends Fragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Spinner spHangTaxi, spTihnhThanh;
    TextView txtKhoangCach, txtThoiGian, txtGiaUocTinh;
    Button btnGoiTaxi;
    AdapterSpinerHangTaxi adapterSpinerHangTaxi;
    List<ModelHangTaxi> listHangTaxi;
    PlaceAutocompleteFragment txtDiemBatDau, txtDiemKetThuc;
    String diaDiemBatDau, diaDiemKetThuc;
    //TextView txtTrangThai;
    DecimalFormat formatTien = new DecimalFormat("###,###,###");
    private static View view;
    List<Address> listAddress;
    GoogleApiClient googleApiClient;
    StringBuilder sb;
    Geocoder geocoder;
    AdapterTinhThanh adapterTinhThanh;
    List<ModelTinhThanh> modelTinhThanhs;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup group = (ViewGroup) view.getParent();
            if (group != null) {
                group.removeView(view);
            }
        }

        try {
            view = LayoutInflater.from(getContext()).inflate(R.layout.frament_uoc_tinh_chi_phi_layout, container, false);
            addControler(view);
        }catch (Exception e){
            Log.e(getClass().getSimpleName(), "onCreateView => " + e.toString());
        }


        return view;
    }

    private void addControler(final View view) {

        try {

            if (!CheckInternet()){
                Toast.makeText(getContext(), getString(R.string.VuilongKetNoi), Toast.LENGTH_SHORT).show();
            }

            modelTinhThanhs = new TinhThanhAsyntask(getContext()).execute(LinkData.linkCacTinhThanh).get();
           // listHangTaxi = new HangTaxiTheoTinhThanhAsyntask(getContext()).execute(LinkData.linkHangTaxi, modelTinhThanhs.get(0).getMaTinhthanh()).get();
            txtDiemBatDau = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.edtDiemBatDau);
            txtDiemKetThuc = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.edtDiemKetThuc);
            spTihnhThanh = (Spinner) view.findViewById(R.id.spTinhThanhUTCP);
            spHangTaxi = (Spinner) view.findViewById(R.id.spHangTaxiUocGia);
            txtKhoangCach = (TextView) view.findViewById(R.id.txtKhoangCach);
            txtThoiGian = (TextView) view.findViewById(R.id.txtThoiGian);
            txtGiaUocTinh = (TextView) view.findViewById(R.id.txtSoTienUocTinh);
            btnGoiTaxi = (Button) view.findViewById(R.id.btnGoiNgayTaxi);
          //  txtTrangThai = (TextView) view.findViewById(R.id.idResult);

            /**
             *An button search PlaceAutocompleteFragment
             * */
            txtDiemBatDau.getView().findViewById(R.id.place_autocomplete_search_button).setVisibility(View.GONE);
            txtDiemKetThuc.getView().findViewById(R.id.place_autocomplete_search_button).setVisibility(View.GONE);
            txtDiemBatDau.getView().findViewById(R.id.place_autocomplete_clear_button).setVisibility(View.GONE);
            txtDiemKetThuc.getView().findViewById(R.id.place_autocomplete_clear_button).setVisibility(View.GONE);
            /**
             * nhap hitnt cho PlaceAutocompleteFragment
             * */
            txtDiemBatDau.setHint(getString(R.string.diemDi));
            txtDiemKetThuc.setHint(getString(R.string.diemDen));



            adapterTinhThanh = new AdapterTinhThanh(getContext(), R.layout.item_tinh_thanh, modelTinhThanhs);
            spTihnhThanh.setAdapter(adapterTinhThanh);
            spTihnhThanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        listHangTaxi = new HangTaxiTheoTinhThanhAsyntask(getContext()).execute(LinkData.linkHangTaxi, modelTinhThanhs.get(i).getMaTinhthanh()).get();
                        adapterSpinerHangTaxi = new AdapterSpinerHangTaxi(getContext(), R.layout.item_spiner_hang_taxi,listHangTaxi);
                        spHangTaxi.setAdapter(adapterSpinerHangTaxi);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            txtDiemBatDau.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    diaDiemBatDau = place.getAddress().toString();

                  //  Log.d("diaDiemBatDau", place.getAddress().toString() );
                    if (diaDiemBatDau != null && diaDiemKetThuc != null) {
                        TinhTien(diaDiemBatDau, diaDiemKetThuc);
                    }

                }

                @Override
                public void onError(Status status) {
                   // txtTrangThai.setText(status.toString());
                }
            });

            txtDiemKetThuc.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    diaDiemKetThuc = place.getAddress().toString();
                 //   Log.d("diaDiemKetThuc", place.get.toString() );
                    if (diaDiemBatDau != null && diaDiemKetThuc != null) {
                        TinhTien(diaDiemBatDau, diaDiemKetThuc);
                    }

                }

                @Override
                public void onError(Status status) {
                 //   txtTrangThai.setText(status.toString());
                }
            });


            spHangTaxi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (diaDiemBatDau != null && diaDiemKetThuc != null) {
                        TinhTien(diaDiemBatDau, diaDiemKetThuc);
                    }
                   // Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btnGoiTaxi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!listHangTaxi.get(spHangTaxi.getSelectedItemPosition()).getSodienthoai2().trim().equals(""))
                    {
                        new AlertDialog.Builder(getContext())
                                .setTitle(listHangTaxi.get(spHangTaxi.getSelectedItemPosition()).getTenhangtaxi())
                                .setMessage(getString(R.string.chonSo))
                                .setCancelable(false)
                                .setNegativeButton(getString(R.string.goiSo1), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        QuaySo(listHangTaxi.get(spHangTaxi.getSelectedItemPosition()).getSodienthoai().trim());
                                        dialogInterface.dismiss();
                                    }
                                }).setPositiveButton(getString(R.string.goiSo2), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                QuaySo(listHangTaxi.get(spHangTaxi.getSelectedItemPosition()).getSodienthoai2().trim());
                                dialogInterface.dismiss();
                            }
                        }).setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();


                    } else {
                        QuaySo(listHangTaxi.get(spHangTaxi.getSelectedItemPosition()).getSodienthoai().trim());
                    }
                }
            });




        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.e(getClass().getSimpleName(), "addControler = >" + e.toString());
        }


    }

    public void QuaySo(String sdt){

        Uri uri = Uri.parse("tel:" + sdt);
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(uri);
        getContext().startActivity(intent);

    }

    @Override
    public void onStart() {
        super.onStart();
      //  geocoder = new Geocoder(getContext(), Locale.getDefault());
        getDataLocation();
        //if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
             googleApiClient.connect();
       // }
    }

    private boolean CheckInternet() {
        try {
            ConnectivityManager cm = (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            boolean isConnect = networkInfo != null && networkInfo.isConnectedOrConnecting();
            return isConnect;
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), e.toString());
        }
        return false;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!CheckInternet()){
            Toast.makeText(getContext(), getString(R.string.VuilongKetNoi), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!SmartLocation.with(getContext()).location().state().isGpsAvailable()){
            Toast.makeText(getContext(), getText(R.string.VuilongMoGPS), Toast.LENGTH_SHORT).show();
            return;
        } else{
            getDataLocation();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.disconnect();
    }

    private void TinhTien(String bd, String kt){

        Log.e("qqqq", bd + " -- " + kt);


        try {
            JSONObject thongTinDuongDi = new JSONObject(new LayThongTinQuangDuong().execute(bd,kt).get().toString());
           // txtTrangThai.setText(thongTinDuongDi.toString());
            if (thongTinDuongDi.getString("status").equals("OK")){
                if (thongTinDuongDi.getString("origin_addresses").equals("[]")) {
                    Toast.makeText(getContext(), getString(R.string.VuilongĐiemDi), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (thongTinDuongDi.getString("destination_addresses").equals("[]")) {
                    Toast.makeText(getContext(), getString(R.string.VuilongĐiemDen), Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONArray arrElements =  thongTinDuongDi.getJSONArray("rows").getJSONObject(0).getJSONArray("elements");
                Float km = Float.parseFloat(arrElements.getJSONObject(0).getJSONObject("distance").getString("value"));
                txtKhoangCach.setText(String.valueOf(Math.round(km * 0.001F)) + " Km");
                txtThoiGian.setText(arrElements.getJSONObject(0).getJSONObject("duration").getString("text"));

                if (Math.round(km * 0.001F) <= 1 ) {
                    //1 km dau tien
                    txtGiaUocTinh.setText(formatTien.format(listHangTaxi.get(spHangTaxi.getSelectedItemPosition()).getGiamocua() * Math.round(km * 0.001F)) + " đ");
                } else if (Math.round(km * 0.001F) <= 30) {
                    //tiep tu km 2 den km 30
                    txtGiaUocTinh.setText(formatTien.format(listHangTaxi.get(spHangTaxi.getSelectedItemPosition()).getGiakmdau() * Math.round(km * 0.001F)) + " đ");
                } else {
                    //tu km 31 tri di
                    txtGiaUocTinh.setText(formatTien.format(listHangTaxi.get(spHangTaxi.getSelectedItemPosition()).getGiakmsau() * Math.round(km * 0.001F)) + " đ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnGoiNgayTaxi:

                break;
        }
    }

    private void getDataLocation(){
        SmartLocation.with(getContext()).location().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                         sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)).append(",");//adress
                        }
                        if (txtDiemBatDau != null) {
                            txtDiemBatDau.setText(sb.substring(0, sb.indexOf(",")));
                            diaDiemBatDau = sb.substring(0, sb.indexOf(","));
                        }
//                        sb.append(address.getLocality()).append("\n");//village
//
//                        sb.append(address.getPostalCode()).append("\n");
//                        sb.append(address.getCountryName());
//                        sb.append(address.getAdminArea()).append("\n"); //state
//
//                        sb.append(address.getSubAdminArea()).append("\n");//district
//
//                        sb.append(address.getSubLocality()).append("\n");



                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//        try {
//            listAddress = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//            for (int i = 0; i < listAddress.get(0).getMaxAddressLineIndex(); i++) {
//                txtDiemBatDau.setText(listAddress.get(0).getAddressLine(0));
//            }
//
//        }catch (Exception e){}
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
