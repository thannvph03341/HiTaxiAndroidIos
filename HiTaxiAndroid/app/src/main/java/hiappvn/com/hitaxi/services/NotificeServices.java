package hiappvn.com.hitaxi.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.net.URISyntaxException;

import hiappvn.com.hitaxi.MainActivity;
import hiappvn.com.hitaxi.R;
import io.socket.client.IO;
import io.socket.emitter.Emitter;

/**
 * Created by VAIO-LAPTOP on 4/18/2017.
 */

public class NotificeServices extends Service {
    public io.socket.client.Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.100.17:8989");
        } catch (URISyntaxException e) {
            Log.e("Lỗi kết nối", "Lỗi: " + e.toString());

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSocket.connect();
       // Toast.makeText(this, "da Connect", Toast.LENGTH_SHORT).show();
        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        mSocket.emit("RegisterId",android_id);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        mSocket.on("ThongBaoTiMo", NhanThonBaoTrenServer);
//        //Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    private void onShowNotification(String body, String title) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(uri)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
        //getBaseContext().
    }



    @Override
    public void onDestroy() {
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public Emitter.Listener NhanThonBaoTrenServer = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            onShowNotification(String.valueOf(args[0]), "sssss");
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//                 //  Toast.makeText(TiMoNotfificationService.this, String.valueOf(args[0]), Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    };

}
