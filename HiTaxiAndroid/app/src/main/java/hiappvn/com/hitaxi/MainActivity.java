package hiappvn.com.hitaxi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import hiappvn.com.hitaxi.adapters.AdapterTinhThanh;
import hiappvn.com.hitaxi.asynctask.AsyncResponse;
import hiappvn.com.hitaxi.asynctask.KhachHangDangNhap;
import hiappvn.com.hitaxi.asynctask.KhachHangDangNhapVoiGoogle;
import hiappvn.com.hitaxi.asynctask.TinhThanhAsyntask;
import hiappvn.com.hitaxi.manager.FramentCaiDat;
import hiappvn.com.hitaxi.manager.FramentDanhSachTaxi;
import hiappvn.com.hitaxi.manager.FramentGioiThieu;
import hiappvn.com.hitaxi.manager.FramentLienHe;
import hiappvn.com.hitaxi.manager.FramentTaiKhoan;
import hiappvn.com.hitaxi.manager.FramentTaxiYeuThich;
import hiappvn.com.hitaxi.manager.FramentThongBao;
import hiappvn.com.hitaxi.manager.FramentTrangChu;
import hiappvn.com.hitaxi.manager.FramentUocTinhChiPhiTaxi;
import hiappvn.com.hitaxi.models.ModelTinhThanh;
import hiappvn.com.hitaxi.services.NotificeServices;
import hiappvn.com.hitaxi.sql.SQLiteConfig;
import hiappvn.com.hitaxi.thuvien.LinkData;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, KhachHangDangNhap, KhachHangDangNhapVoiGoogle, GoogleApiClient.OnConnectionFailedListener ,
        View.OnClickListener{

    SQLiteConfig sqLiteConfig;
    Spinner spTinhThanh;
    AdapterTinhThanh adapterTinhThanh;
    List<ModelTinhThanh> listTinhThanh;
    TextView txtTitleToobar, txtTenNguoiDung, txtEmailNguoiDung, txtThongBaoMoi;
    ImageView imageAccount;
    CallbackManager callbackManager;
   // Integer RC_SIGN_IN = 19980;

    AsyncResponse asyncResponse;
    GoogleSignInOptions signInOptions;
    GoogleApiClient mGoogleApiClient;

    SharedPreferences sharedPreferences;


    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("NgonNgu", MODE_PRIVATE);
        sharedPreferences.getString("ngonngu","vi");
        forceLocale(this, sharedPreferences.getString("ngonngu","vi"));
        /*****
         * notifiServices
         * *****/
        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Intent intent = new Intent(MainActivity.this, NotificeServices.class);
        startService(intent);



        /*****End
         *
         * ***/
        FacebookSdk.sdkInitialize(this);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtThongBaoMoi = (TextView) findViewById(R.id.txtThongBaoMoi);
        /**
         *
         * */
        FirebaseMessaging.getInstance().subscribeToTopic("Taxi");
        FirebaseInstanceId.getInstance().getToken();

        addControllView();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View viewHeader = navigationView.getHeaderView(0);
        imageAccount = (ImageView) viewHeader.findViewById(R.id.imageAccount);
        txtTenNguoiDung = (TextView) viewHeader.findViewById(R.id.txtTenNguoiDung);
        txtEmailNguoiDung = (TextView) viewHeader.findViewById(R.id.txtEmailNguoiDung);
        imageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.restartTitle))
                        .setMessage(getString(R.string.dangNhapVoi))
                        .setNegativeButton("Google", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                LoginGoogle();
                            }
                        })
                        //setNeutralButton
                        .setPositiveButton("Facebook", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                LoginFacebookSetup();
                            }
                        }).create();
                alertDialog.show();

            }
        });
        // Hien thi man hinh danh sach taxi truoc
//        navigationView.getMenu().getItem(0).setChecked(true);
//        setupFrament(new FramentTrangChu());
        navigationView.getMenu().getItem(0).setVisible(false);
        navigationView.getMenu().getItem(1).setChecked(true);
        setupFrament(new FramentTrangChu());
        setupFrament(new FramentDanhSachTaxi());


    }




    private void LoginGoogle(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void LoginFacebookSetup(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
    }



    private void addControllView() {
        sqLiteConfig = new SQLiteConfig(this);
        sqLiteConfig.open();
        spTinhThanh = (Spinner) findViewById(R.id.spTinHThanh);
        txtTitleToobar = (TextView) findViewById(R.id.txtTitleToobar);
        txtTitleToobar.setText(getString(R.string.TrangChu));

        /**
         *
         * */
        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestId()
        .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
        .enableAutoManage(this, this)
        .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
        .build();

        /**
         *
         * */
        try {
            if (sqLiteConfig.ThongBaoMoi().getNoiDungThongBao().trim().equals("")){
                txtThongBaoMoi.setVisibility(View.GONE);
            } else {
                txtThongBaoMoi.setVisibility(View.VISIBLE);
                txtThongBaoMoi.setText(getString(R.string.restartTitle) + sqLiteConfig.ThongBaoMoi().getNoiDungThongBao());
            }

            listTinhThanh = new TinhThanhAsyntask(this).execute(LinkData.linkCacTinhThanh).get();

            adapterTinhThanh = new AdapterTinhThanh(this, R.layout.item_tinh_thanh, listTinhThanh);
            spTinhThanh.setAdapter(adapterTinhThanh);

            spTinhThanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view,int i, long l) {
                   // if (listTinhThanh.size() > 0) {
                    try {
                        asyncResponse.TinhThanhs(listTinhThanh.get(i).getMaTinhthanh());
                    }catch (Exception e){
                        Log.e(getClass().getSimpleName(), "onItemSelected -> " + e.toString());
                    }
                   // }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "addControllView -> " + e.toString());
        }

        /**
         * setupFacebookLogin
         * */

        setupFacebookLogin();
        CheckLoginFacebook();
    }

    private void CheckLoginFacebook() {
        if (AccessToken.getCurrentAccessToken() != null){
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try{
                        Glide.with(getBaseContext()).load(LinkData.linhAnhFacebook(object.getString("id"))).into(imageAccount);
                        txtTenNguoiDung.setText(object.getString("name"));
                        txtEmailNguoiDung.setText(object.getString("email"));
                    }catch (Exception e){
                        Log.e(getClass().getSimpleName(), "setupFacebookLogin -> " + e.toString());
                    }
                }
            });

            Bundle bundle = new Bundle();
            bundle.putString("fields","id, name, locale, email, link");
            request.setParameters(bundle);
            request.executeAsync();
        }

    }

    private void setupFacebookLogin() {

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try{
                            Glide.with(getBaseContext()).load(LinkData.linhAnhFacebook(object.getString("id"))).into(imageAccount);
                            txtTenNguoiDung.setText(object.getString("name"));
                            txtEmailNguoiDung.setText(object.getString("email"));
                        }catch (Exception e){
                            Log.e(getClass().getSimpleName(), "setupFacebookLogin -> " + e.toString());
                        }

                    }
                });

                Bundle bundle = new Bundle();
                bundle.putString("fields", "id, name, locale, email, link");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Toast.makeText(this, result.getStatus().toString(), Toast.LENGTH_SHORT).show();
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
               // Toast.makeText(this, acct.getDisplayName(), Toast.LENGTH_SHORT).show();
                // Get account information
                Glide.with(this).load(acct.getPhotoUrl()).into(imageAccount);
                txtTenNguoiDung.setText(acct.getDisplayName());
                txtEmailNguoiDung.setText(acct.getEmail());
//                mFullName = acct.getDisplayName();
//                mEmail = acct.getEmail();
            }
        }
   }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.navTranChu:
                if (!item.isChecked()) {
                    txtTitleToobar.setText(getText(R.string.TrangChu));
                    spTinhThanh.setVisibility(View.GONE);
                    setupFrament(new FramentTrangChu());
                }
                break;
            case R.id.navThongBao:
                if (!item.isChecked()) {
                    txtTitleToobar.setText(getText(R.string.ThongBao));
                    spTinhThanh.setVisibility(View.GONE);
                    setupFrament(new FramentThongBao());
                }
                break;
            case R.id.navTaxiYeuThich:
                if (!item.isChecked()) {
                    txtTitleToobar.setText(getText(R.string.TaxiYeuThich));
                    spTinhThanh.setVisibility(View.GONE);
                    setupFrament(new FramentTaxiYeuThich());
                }
                break;
            case R.id.navUocTinhGia:
                if (!item.isChecked()) {
                    txtTitleToobar.setText(getText(R.string.UocTinhChiPhi));
                    spTinhThanh.setVisibility(View.GONE);
                    setupFrament(new FramentUocTinhChiPhiTaxi());
                }
                break;
            case R.id.navDanhSachTaxi:
                if (!item.isChecked()) {
                    txtTitleToobar.setText(getText(R.string.DanhSachTaxi));
                    spTinhThanh.setVisibility(View.VISIBLE);
                    setupFrament(new FramentDanhSachTaxi());
                }
                break;
            case R.id.navTaiKhoan:
                if (!item.isChecked()) {
                    txtTitleToobar.setText(getText(R.string.TaiKhoan));
                    spTinhThanh.setVisibility(View.GONE);
                    setupFrament(new FramentTaiKhoan());
                }
                break;
            case R.id.navCaiDat:
                if (!item.isChecked()) {
                    txtTitleToobar.setText(getText(R.string.CaiDat));
                    spTinhThanh.setVisibility(View.GONE);
                    setupFrament(new FramentCaiDat());
                }
                break;
            case R.id.navGioiThieu:
                if (!item.isChecked()) {
                    txtTitleToobar.setText(getText(R.string.GioiThieu));
                    spTinhThanh.setVisibility(View.GONE);
                    setupFrament(new FramentGioiThieu());
                }
                break;
            case R.id.navLienHe:
                if (!item.isChecked()) {
                    txtTitleToobar.setText(getText(R.string.LienHe));
                    spTinhThanh.setVisibility(View.GONE);
                    setupFrament(new FramentLienHe());
                }
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //Cai dat framet
    private void setupFrament(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.animation_right_in,R.anim.animation_right_out).replace(R.id.contenFrameLayout, fragment).commit();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        try {
            asyncResponse = (AsyncResponse) fragment;
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "onAttachFragment -> " + e.toString());
        }
    }

    @Override
    public void DangXuat() {
        LoginManager.getInstance().logOut();
        CheckLoginFacebook();
        imageAccount.setImageResource(R.drawable.account);
        txtTenNguoiDung.setText("");
        txtEmailNguoiDung.setText("");
    }

    @Override
    public void DangNhap() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
    }

    @Override
    public void CaiDatDuLieuDangNhap() {
        CheckLoginFacebook();
    }

    @Override
    public void KhachHangDangNhap() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();


    }

    @Override
    public void KhachHangDangXuat() {

    }

    @Override
    public void CapNhatThongTinKhachHang() {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }






    @SuppressWarnings("deprecation")
    public static void forceLocale(Context context, String localeCode) {
        String localeCodeLowerCase = localeCode.toLowerCase();
        Resources resources = context.getResources();
        Configuration overrideConfiguration = resources.getConfiguration();
        Locale overrideLocale = new Locale(localeCodeLowerCase);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            overrideConfiguration.setLocale(overrideLocale);
        } else {
            overrideConfiguration.locale = overrideLocale;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(overrideConfiguration);
        } else {
            resources.updateConfiguration(overrideConfiguration, resources.getDisplayMetrics());
        }
    }
    @Override
    public void onClick(View v) {

    }
}
