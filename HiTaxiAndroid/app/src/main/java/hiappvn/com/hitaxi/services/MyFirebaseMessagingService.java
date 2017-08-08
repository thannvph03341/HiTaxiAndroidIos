package hiappvn.com.hitaxi.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import hiappvn.com.hitaxi.MainActivity;
import hiappvn.com.hitaxi.R;
import hiappvn.com.hitaxi.models.ModelThongBao;
import hiappvn.com.hitaxi.sql.SQLiteConfig;


/**
 * Created by Dominions on 16/11/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

   SQLiteConfig sqLiteConfig;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy hh:MM:ss");

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sqLiteConfig = new SQLiteConfig(getBaseContext());
        onShowNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());

    }

    private void onShowNotification(String body, String title) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       // Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(body)
               // .setSound(uri)
                .setSmallIcon(R.drawable.ic_grade_blu_24dp)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());

        try{

            sqLiteConfig.ThemThongBaoMoi(new ModelThongBao(title, body, simpleDateFormat.format(calendar.getTimeInMillis())));
        }catch (Exception e){
            Log.e(getClass().getSimpleName(), "onShowNotification => " + e.toString());
        }

    }


}
