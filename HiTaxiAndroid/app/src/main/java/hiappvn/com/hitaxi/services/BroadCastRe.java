package hiappvn.com.hitaxi.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by VAIO-LAPTOP on 4/18/2017.
 */

public class BroadCastRe extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        isConnected(context);
      // if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
        Intent serviceIntent = new Intent(context, NotificeServices.class);
        context.startService(serviceIntent);
        Log.d("Network Monitoring", "onReceive ");
    //    Toast.makeText(context, "123", Toast.LENGTH_SHORT).show();

      //  }
    }
    public  boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
       // Toast.makeText(context, "HiTaxi", Toast.LENGTH_SHORT).show();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
