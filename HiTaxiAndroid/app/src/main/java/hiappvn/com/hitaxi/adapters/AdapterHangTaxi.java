package hiappvn.com.hitaxi.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hiappvn.com.hitaxi.R;
import hiappvn.com.hitaxi.ThonTinChiTietGiaActivity;
import hiappvn.com.hitaxi.asynctask.DanhGiaHangTaxi;
import hiappvn.com.hitaxi.asynctask.KhachHangDangNhap;
import hiappvn.com.hitaxi.asynctask.ThemTaxiVaoDanhSachYeuThich;
import hiappvn.com.hitaxi.models.ModelHangTaxi;
import hiappvn.com.hitaxi.sql.SQLiteConfig;
import hiappvn.com.hitaxi.thuvien.LinkData;

/**
 * Created by nongthan on 1/9/17.
 */

public class AdapterHangTaxi extends RecyclerView.Adapter<AdapterHangTaxi.ItemHangTaxi>{

    Context context;
    List<ModelHangTaxi> listModelHangTaxis;
    DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
    SQLiteConfig sqliteConfig;
    KhachHangDangNhap khachHangDangNhap;


    public AdapterHangTaxi(Context context, List<ModelHangTaxi> listModelHangTaxis) {
        this.context = context;
        this.listModelHangTaxis = listModelHangTaxis;
    }

    @Override
    public ItemHangTaxi onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hang_taxi, parent, false);
        khachHangDangNhap = (KhachHangDangNhap) context;
        sqliteConfig = new SQLiteConfig(context);


        return new ItemHangTaxi(view);
    }

    @Override
    public void onBindViewHolder(ItemHangTaxi holder, int position) {
        ///
        ModelHangTaxi modelHangTaxi = listModelHangTaxis.get(position);
        Glide.with(context).load(Uri.parse(modelHangTaxi.getHinhanh())).placeholder(R.mipmap.ic_launcher).into(holder.imageLogo);
        holder.txtTenHangTaxi.setText(modelHangTaxi.getTenhangtaxi());
        holder.txtGiaCuocMoCua.setText(decimalFormat.format(modelHangTaxi.getGiakmdau()) + " đ/1Km");
        holder.ratingTaxi.setRating(modelHangTaxi.getRate());
    }

    @Override
    public int getItemCount() {
        return (null != listModelHangTaxis ? listModelHangTaxis.size(): 0);
    }

    public void ShowMenu(View view, ModelHangTaxi hangTaxi){

        PopupMenu popupMenu = new PopupMenu(context, view);
       // MenuInflater menuInflater = popupMenu.getMenuInflater();
       // menuInflater.inflate(R.menu.main, popupMenu.getMenu());

        popupMenu.getMenu().add("Giá mở cửa: " + decimalFormat.format(hangTaxi.getGiamocua()) + " đ/1Km");
        popupMenu.getMenu().add("Giá 25 Km đầu: " + decimalFormat.format(hangTaxi.getGiakmdau()) + " đ/1Km");
        popupMenu.getMenu().add("Giá sau 25 Km: " + decimalFormat.format(hangTaxi.getGiakmsau()) + " đ/1Km");


        popupMenu.show();
    }



    class ItemHangTaxi extends RecyclerView.ViewHolder implements View.OnClickListener{
        public AlertDialog alertDialog;
        public CircleImageView imageLogo;
        public TextView txtTenHangTaxi, txtGiaCuocMoCua;
        public RatingBar ratingTaxi;
        public Button btnGoi, btnChiTiet;
        public ImageView imgYeuThich;
        public RatingBar raTingbarCheck;


        public ItemHangTaxi(View itemView) {
            super(itemView);
            imageLogo = (CircleImageView) itemView.findViewById(R.id.imgLogo);
            txtTenHangTaxi = (TextView) itemView.findViewById(R.id.txtTenHangTaxi);
            txtGiaCuocMoCua = (TextView) itemView.findViewById(R.id.txtGiaCuocMoCua);
            ratingTaxi = (RatingBar) itemView.findViewById(R.id.ratingTaxi);
            btnGoi = (Button) itemView.findViewById(R.id.btnGoi);
            btnChiTiet = (Button) itemView.findViewById(R.id.btnChiTiet);
            imgYeuThich = (ImageView) itemView.findViewById(R.id.imgYeuThich);
            btnGoi.setOnClickListener(this);
            btnChiTiet.setOnClickListener(this);
            imgYeuThich.setOnClickListener(this);
            ////
            final ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.foodRatingBar);
            raTingbarCheck = new RatingBar(contextThemeWrapper, null , 0);
            raTingbarCheck.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            raTingbarCheck.setNumStars(5);

            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setLayoutParams(new WindowManager.LayoutParams());
            ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            ll.setGravity(Gravity.CENTER);
            ll.addView(raTingbarCheck);
            ///
            alertDialog = new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.rate))
                    .setView(ll)
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            //Toast.makeText(contextThemeWrapper, getAdapterPosition() + "", Toast.LENGTH_SHORT).show();
                            DanhGiaHangTaxi(getAdapterPosition(), Float.toString(raTingbarCheck.getRating()));
                        }
                    })
                    .create();
            ratingTaxi.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    alertDialog.show();
                    raTingbarCheck.setRating(listModelHangTaxis.get(getAdapterPosition()).getRate());
                    return false;
                }
            });
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.btnGoi:

                    if (!listModelHangTaxis.get(getAdapterPosition()).getSodienthoai2().trim().equals(""))
                    {
                        new AlertDialog.Builder(context)
                                .setTitle(listModelHangTaxis.get(getAdapterPosition()).getTenhangtaxi())
                                .setMessage(context.getString(R.string.chonSo))
                                .setCancelable(false)
                                .setNegativeButton(context.getText(R.string.goiSo1), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        QuaySo(listModelHangTaxis.get(getAdapterPosition()).getSodienthoai().trim());
                                        dialogInterface.dismiss();
                                    }
                                }).setPositiveButton(context.getText(R.string.goiSo2), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        QuaySo(listModelHangTaxis.get(getAdapterPosition()).getSodienthoai2().trim());
                                        dialogInterface.dismiss();
                                    }
                                }).setNeutralButton(context.getText(R.string.cancel), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    }
                        }).create().show();


                    } else {
                        QuaySo(listModelHangTaxis.get(getAdapterPosition()).getSodienthoai().trim());
                    }

                    break;

                case R.id.btnChiTiet:
                  //  ShowMenu(view, listModelHangTaxis.get(getAdapterPosition()));
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("hangTaxi", listModelHangTaxis.get(getAdapterPosition()));
                    Intent intent = new Intent(view.getContext(), ThonTinChiTietGiaActivity.class);
                    intent.putExtra("hangTaxi", bundle);

                    view.getContext().startActivity(intent);

                    break;

                case R.id.imgYeuThich:

                    CheckLoginFacebook(getAdapterPosition());

                    break;

            }
        }

        public void QuaySo(String sdt){

            Uri uri = Uri.parse("tel:" + sdt);
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(uri);
            context.startActivity(intent);

        }


    }

    private void DanhGiaHangTaxi(final int adapterPosition,final String rate) {

        if (AccessToken.getCurrentAccessToken() != null){
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try{
                        //Toast.makeText(context, object.getString("id"), Toast.LENGTH_SHORT).show();
                       new DanhGiaHangTaxi().execute(LinkData.linkDanhGia, object.getString("id"), listModelHangTaxis.get(adapterPosition).getMahangtaxi(), rate);

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
            AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.restartTitle))
                    .setMessage(context.getString(R.string.dangNhapDeSuDung))
                    .setNegativeButton("Google", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                        }
                    })
                    .setPositiveButton("Facebook", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            khachHangDangNhap.DangNhap();
                        }
                    }).create();
            alertDialog.show();

        }

    }


    private void CheckLoginFacebook(final int index) {
        if (AccessToken.getCurrentAccessToken() != null){
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try{
                        //Toast.makeText(context, object.getString("id"), Toast.LENGTH_SHORT).show();
                        GuiHangTaxiNguoiDungThichVeServer(object.getString("id").trim(), listModelHangTaxis.get(index).getMahangtaxi());
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
          //  Toast.makeText(context, "Bạn cần đăng nhập để sử dụng!", Toast.LENGTH_SHORT).show();
            khachHangDangNhap.DangNhap();
        }

    }

    private void GuiHangTaxiNguoiDungThichVeServer(String id, String maHang) {
        try {
            if (new ThemTaxiVaoDanhSachYeuThich().execute(LinkData.linkYeuThich, id, maHang).get().equals("")){
                Toast.makeText(context, context.getText(R.string.daCoTrongDanhSachThich), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, context.getString(R.string.themThanhCong), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
           // Toast.makeText(context, "Bạn vui lòng thủ lại! thêm Taxi yêu thích thành công!", Toast.LENGTH_SHORT).show();
           Log.e(getClass().getSimpleName(), "GuiHangTaxiNguoiDungThichVeServer => " + e.toString());
        }
    }


}
