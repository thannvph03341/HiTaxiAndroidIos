package hiappvn.com.hitaxi.manager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import hiappvn.com.hitaxi.MainActivity;
import hiappvn.com.hitaxi.R;
import hiappvn.com.hitaxi.asynctask.AsyncTaskSuaThongTin;
import hiappvn.com.hitaxi.asynctask.KhachHangDangNhap;
import hiappvn.com.hitaxi.sql.SQLiteConfig;
import hiappvn.com.hitaxi.thuvien.LinkData;

/**
 * Created by nongthan on 1/9/17.
 */

public class FramentTaiKhoan extends Fragment {
    private GoogleApiClient mGoogleApiClient;
    private static View viewMain;
    CallbackManager callbackManager;
    CircleImageView imgProfile;
    EditText txtHoTen, txtSdt, txtEmail, txtDiachi;

    Button btnSuaThongTin, btnDangNhapDangXuat;
    KhachHangDangNhap khachHangDangNhap;
    String urll = "http://hitaxi.vn/KhachHang/UpdateNguoidung";
    RelativeLayout relativeLayout;
    private static final int RC_SIGN_IN = 9001;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                //             .enableAutoManage(getActivity() /* FragmentActivity */, (GoogleApiClient.OnConnectionFailedListener) getActivity() /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


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
            viewMain = LayoutInflater.from(getContext()).inflate(R.layout.frament_tai_khoan_layout, container, false);
            addControllView(viewMain);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "onCreateView => " + e.toString());
        }
        return viewMain;
    }

    private void addControllView(View viewMain) {
        imgProfile = (CircleImageView) viewMain.findViewById(R.id.imgProfile);
        txtHoTen = (EditText) viewMain.findViewById(R.id.txtHoTen);
        txtSdt = (EditText) viewMain.findViewById(R.id.txtSdt);
        txtEmail = (EditText) viewMain.findViewById(R.id.txtEmail);
        txtDiachi = (EditText) viewMain.findViewById(R.id.txtDiachi);
        btnSuaThongTin = (Button) viewMain.findViewById(R.id.btnCapNhatThongTin);
        btnDangNhapDangXuat = (Button) viewMain.findViewById(R.id.btnDangNhapDangXuat);
        relativeLayout = (RelativeLayout) viewMain.findViewById(R.id.txtThongTin);
        btnDangNhapDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnDangNhapDangXuat.getText().toString().equals("Đăng Xuất")) {
                    LoginManager.getInstance().logOut();
                    imgProfile.setImageResource(R.drawable.account);
                    txtHoTen.setText("");
                    txtSdt.setText("");
                    txtEmail.setText("");
                    txtDiachi.setText("");
//                        txtHoTen.setEnabled(false);
//                        txtSdt.setEnabled(false);
//                        txtEmail.setEnabled(false);
//                        txtDiachi.setEnabled(false);
                    khachHangDangNhap.DangXuat();
                    btnDangNhapDangXuat.setText("Đăng Nhập");
                    btnSuaThongTin.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                } else {
                    DangNhap();
                }
            }
        });

        setupFacebookLogin();
        CheckLoginFacebook();
    }


    private void DangNhap() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.restartTitle))
                .setMessage(getString(R.string.dangNhapVoi))
                .setNegativeButton("Google", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        SQLiteConfig sqLiteConfig = new SQLiteConfig(getContext());
                        sqLiteConfig.seletTK();
                        LoginGoogle();
                    }
                })
                //setNeutralButton
                .setPositiveButton("Facebook", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        LoginFacebook();
                    }
                }).create();
        alertDialog.show();


    }

    private void LoginGoogle() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void CheckLoginFacebook() {
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        btnDangNhapDangXuat.setText(getText(R.string.DangNhap));
                        btnSuaThongTin.setVisibility(View.VISIBLE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        Glide.with(getContext()).load(LinkData.linhAnhFacebook(object.getString("id"))).into(imgProfile);
                        txtHoTen.setText(object.getString("name"));
                        txtEmail.setText(object.getString("email"));

                    } catch (Exception e) {
                        Log.e(getClass().getSimpleName(), "setupFacebookLogin -> " + e.toString());
                    }
                }
            });

            Bundle bundle = new Bundle();
            bundle.putString("fields", "id, name, locale, email, link");
            request.setParameters(bundle);
            request.executeAsync();
        } else {
            btnDangNhapDangXuat.setText(getText(R.string.DangNhap));
            btnSuaThongTin.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.GONE);
        }
    }

    private void LoginFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));

    }

    private void setupFacebookLogin() {

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            btnDangNhapDangXuat.setText(getString(R.string.DangXuat));
                            khachHangDangNhap.CaiDatDuLieuDangNhap();
                            btnSuaThongTin.setVisibility(View.VISIBLE);
                            relativeLayout.setVisibility(View.VISIBLE);
                            Glide.with(getContext()).load(LinkData.linhAnhFacebook(object.getString("id"))).into(imgProfile);
                            txtHoTen.setText(object.getString("name"));
                            txtEmail.setText(object.getString("email"));

                        } catch (Exception e) {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            // handleSignInResult(result);
            btnSuaThongTin.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.VISIBLE);

            GoogleSignInAccount acct = result.getSignInAccount();
            String hovaten = acct.getDisplayName();
            String Họ = acct.getGivenName();
            String tenrieng = acct.getFamilyName();
            String personEmail = acct.getEmail();//
            String idGoogle = acct.getId();

            Uri anhdaidien = acct.getPhotoUrl();


            //    txtHoTen, txtSdt, txtEmail, txtDiachi;
            txtHoTen.setText(hovaten);
            //  imgProfile.setImageResource(Integer.parseInt(String.valueOf(anhdaidien)) );
            Glide.with(this).load(anhdaidien.toString()).into(imgProfile);
            txtEmail.setText(personEmail);
            btnDangNhapDangXuat.setText("Log Out");
            btnSuaThongTin.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {

                                                      modelLayThongTinKhachHangs = new ArrayList<ModelLayThongTinKhachHang>();
                                                      modelLayThongTinKhachHangs.add(new ModelLayThongTinKhachHang("", txtHoTen.getText().toString(), txtEmail.getText().toString(), "", "", txtSdt.getText().toString(), ""));
                                                      SQLiteConfig sqLiteConfig = new SQLiteConfig(getContext());
                                                      sqLiteConfig.ThemTTTK(modelLayThongTinKhachHangs);
                                                      JSONObject jsonObject = new JSONObject();
                                                      try {
                                                          jsonObject.put("Manguoidung", UUID.randomUUID().toString()); // lay thong tin roi moi put chu
                                                          jsonObject.put("Tennguoidung", txtHoTen.getText());//
                                                          jsonObject.put("Sodienthoai", txtSdt.getText());//
                                                          jsonObject.put("Email", txtEmail.getText());//
                                                          jsonObject.put("Facebook", "cai nay cho cai id face vao");//
                                                          jsonObject.put("Ngaysinh", "04/24/2017");//
                                                          jsonObject.put("Password", "123");//
                                                          new AsyncTaskSuaThongTin(getContext()).execute(urll, String.valueOf(jsonObject));


                                                      } catch (Exception e) {
                                                          e.printStackTrace();
                                                          Toast.makeText(getActivity().getApplicationContext(), "Sửa Dữ Liệu Thất Bại", Toast.LENGTH_SHORT).show();

                                                      }
                                                      txtHoTen.setText(txtHoTen.getText().toString());
                                                      txtSdt.setText(txtSdt.getText().toString());
                                                      txtEmail.setText(txtEmail.getText().toString());
                                                      txtDiachi.setText(txtDiachi.getText().toString());

                                                  }
                                              }

            );
        } else {

            Toast.makeText(getActivity(), "Đăng Nhập Không Thành Công", Toast.LENGTH_SHORT).show();
        }
        btnDangNhapDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //signOut();
                //  getActivity().finish();
                imgProfile.setImageResource(R.drawable.account);
                txtHoTen.setText("");
                txtSdt.setText("");
                txtEmail.setText("");
                txtDiachi.setText("");
//                txtHoTen.setEnabled(false);
//                txtSdt.setEnabled(false);
//                txtEmail.setEnabled(false);
//                txtDiachi.setEnabled(false);
                btnSuaThongTin.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.GONE);

                btnDangNhapDangXuat.setText("Đăng Nhập");
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
//                signOut();

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            khachHangDangNhap = (KhachHangDangNhap) context;
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "onAttach => " + e.toString());
        }

    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(getActivity(), "Đăng Xuất Thành  Công", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {


        } else {

        }
    }


}

