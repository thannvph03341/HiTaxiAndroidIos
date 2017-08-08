package hiappvn.com.hitaxi.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by Dominions on 16/11/2016.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        getTokenOnServer(token);
        Log.d("token", token);
    }

    private void getTokenOnServer(String token) {


    }
}
