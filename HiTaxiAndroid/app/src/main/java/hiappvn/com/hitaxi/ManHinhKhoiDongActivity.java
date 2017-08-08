package hiappvn.com.hitaxi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class ManHinhKhoiDongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_khoi_dong);
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
                StartActivity();

           }
       }, 500);
    }

    private void StartActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
