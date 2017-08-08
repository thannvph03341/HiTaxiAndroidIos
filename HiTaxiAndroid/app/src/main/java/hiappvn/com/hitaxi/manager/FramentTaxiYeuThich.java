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
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

import java.util.List;

import hiappvn.com.hitaxi.R;
import hiappvn.com.hitaxi.adapters.AdapterHangTaxi;
import hiappvn.com.hitaxi.asynctask.HangTaxihAsyntask;
import hiappvn.com.hitaxi.models.ModelHangTaxi;
import hiappvn.com.hitaxi.thuvien.LinkData;

/**
 * Created by nongthan on 1/9/17.
 */

public class FramentTaxiYeuThich extends Fragment{

    RecyclerView recyTaxiYeuThich;
    List<ModelHangTaxi> listHangTaxi = null;
    AdapterHangTaxi adapterHangTaxi;
    private static View viewMain;
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
            viewMain = LayoutInflater.from(getContext()).inflate(R.layout.frament_taxi_yeu_thich_layout, container, false);
            addControllView(viewMain);
        }catch (Exception e){
            Log.e(getClass().getSimpleName(), "onCreateView => " + e.toString());
        }
        return viewMain;

    }

    private void addControllView(View view) {
        try {
            recyTaxiYeuThich = (RecyclerView) view.findViewById(R.id.recyTaxiYeuThich);
            recyTaxiYeuThich.setHasFixedSize(true);
            recyTaxiYeuThich.setLayoutManager(new LinearLayoutManager(getContext()));
//            if (adapterHangTaxi == null) {
            CheckLoginFacebook();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void CheckLoginFacebook() {
        if (AccessToken.getCurrentAccessToken() != null){
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try{
                        listHangTaxi = new HangTaxihAsyntask(getContext()).execute(LinkData.linkHangTaxiYeuThich(object.getString("id"))).get();
                        adapterHangTaxi = new AdapterHangTaxi(getContext(), listHangTaxi);
                        recyTaxiYeuThich.setAdapter(adapterHangTaxi);
                        adapterHangTaxi.notifyDataSetChanged();
                    }catch (Exception e){
                        Log.e(getClass().getSimpleName(), "setupFacebookLogin -> " + e.toString());
                    }
                }
            });

            Bundle bundle = new Bundle();
            bundle.putString("fields","id, name, locale, email, link");
            request.setParameters(bundle);
            request.executeAsync();

        } else {
            Toast.makeText(getContext(), getString(R.string.dangNhapDeSuDung), Toast.LENGTH_SHORT).show();
        }

    }
}
