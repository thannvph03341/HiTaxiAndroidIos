package hiappvn.com.hitaxi.manager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.Locale;

import hiappvn.com.hitaxi.MainActivity;
import hiappvn.com.hitaxi.R;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by nongthan on 1/9/17.
 */

public class FramentCaiDat extends Fragment {

    private static View viewMain;
    RadioButton rdbTiengViet, rdbTiengAnh;
    SharedPreferences.Editor editor;
    AlertDialog alertDialog;

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences("NgonNgu", MODE_PRIVATE);
        editor = sharedPreferences.edit();
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
            viewMain = LayoutInflater.from(getContext()).inflate(R.layout.frament_cai_dat_layout, container, false);
            addControler(viewMain);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "onCreateView => " + e.toString());
        }
        return viewMain;
    }

    private void addControler(View viewMain) {
        rdbTiengViet = (RadioButton) viewMain.findViewById(R.id.rdbTiengViet);
        rdbTiengAnh = (RadioButton) viewMain.findViewById(R.id.rdbTiengAnh);

        if (sharedPreferences.getString("ngonngu", "vi").trim().equals("vi")) {
            rdbTiengViet.setChecked(true);
            rdbTiengAnh.setChecked(false);
        } else {
            rdbTiengViet.setChecked(false);
            rdbTiengAnh.setChecked(true);
        }
        rdbTiengViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.restartTitle))
                        .setMessage(getString(R.string.restart))
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                editor.putString("ngonngu", "vi");
                                editor.commit();
//                                Process.killProcess(Process.myPid());
//                                System.exit(0);
                                Intent intent = new Intent(getContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        }).create();
                alertDialog.show();
            }
        });

        rdbTiengAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.restartTitle))
                        .setMessage(getString(R.string.restart))
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                editor.putString("ngonngu", "en");
                                editor.commit();

                               Intent intent = new Intent(getContext(),MainActivity.class);
                                startActivity(intent);

                            }
                        }).create();
                alertDialog.show();
            }
        });
    }
    public void changelanguage(String langufe){
        Locale locale= new Locale(langufe);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getContext().getResources().updateConfiguration(configuration,getContext().getResources().getDisplayMetrics());
        Intent intent = new Intent(getContext(),MainActivity.class);
        startActivity(intent);
    }

}
