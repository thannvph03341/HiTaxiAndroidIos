package hiappvn.com.hitaxi.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hiappvn.com.hitaxi.models.ModelHangTaxi;
import hiappvn.com.hitaxi.models.ModelKhachHang;
import hiappvn.com.hitaxi.models.ModelThongBao;
import hiappvn.com.hitaxi.models.ModelTinhThanh;

/**
 * Created by Dominions on 1/11/2016.
 */

public class SQLiteConfig extends SQLiteOpenHelper {

    SQLiteDatabase db;
    public static final String nameDatabase = "taxidatabase.db";
    public static final int versionDatabase = 2;

    //  private String ,,Facebook,Password,;
    //private   int Ngaysinh;
    public static final String tbNguoiDung = "tbNguoiDung";
    public static final String hotennguoidung = "hotennguoidung";
    public static final String SDT = "SDT";
    public static final String Manguoidung = "Manguoidung";

    public static final String Password = "Password";
    public static final String Ngaysinh = "Ngaysinh";

    public static final String Mail = "Mail";
    public static final String DiaChi = "DiaChi";
    public static final String tbThongBao = "tbThongBao";
    public static final String tbHangTaxi = "tbHangTaxi";
    public static final String tbtinhThanh = "tbtinhThanh";
    //
    public static final String idThongBao = "idThongBao";
    public static final String tieuDeThongBao = "tieuDeThongBao";
    public static final String noiDungThongBao = "noiDungThongBao";
    public static final String ngayThongBao = "ngayThongBao";
    //
    public static final String Mahangtaxi = "Mahangtaxi";
    public static final String Tenhangtaxi = "Tenhangtaxi";
    public static final String Sodienthoai = "Sodienthoai";
    public static final String Sodienthoai2 = "Sodienthoai2";
    public static final String Diachi = "Diachi";
    public static final String Website = "Website";
    public static final String Khoanggia = "Khoanggia";
    public static final String Facebook = "Facebook";
    public static final String MaTinhthanh = "MaTinhthanh";
    public static final String Rate = "Rate";
    public static final String Hinhanh = "Hinhanh";
    public static final String Giamocua = "Giamocua";
    public static final String Giakmdau = "Giakmdau";
    public static final String Giakmsau = "Giakmsau";
    public static final String Khoanggia7cho = "Khoanggia7cho";
    public static final String Giamocua7 = "Giamocua7";
    public static final String Giakmdau7 = "Giakmdau7";
    public static final String Giakmsau7 = "Giakmsau7";

    //
    //public static final String MaTinhThanh = "MaTinhThanh";
    public static final String TenTinhThanh = "TenTinhThanh";


    public SQLiteConfig(Context context) {
        super(context, nameDatabase, null, versionDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql_tbNguoiDung = "CREATE TABLE IF NOT EXISTS tbNguoiDung (Manguoidung NVARCHAR NOT NULL PRIMARY KEY , Tennguoidung NVARCHAR, Sodienthoai NVARCHAR, Email NVARCHAR, Facebook NVARCHAR, Ngaysinh NVARCHAR, Password NVARCHAR)";
        sqLiteDatabase.execSQL(sql_tbNguoiDung);



        String sql_tbThongBao = "CREATE TABLE IF NOT EXISTS " + tbThongBao + "(" + idThongBao + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + tieuDeThongBao + " NVARCHAR, " +
                noiDungThongBao + " NVARCHAR," +
                ngayThongBao + " DATETIME)";
        sqLiteDatabase.execSQL(sql_tbThongBao);

        String sql_tbHangTaxi = "CREATE TABLE IF NOT EXISTS " + tbHangTaxi + "(" + Mahangtaxi + " NVARCHAR NOT NULL PRIMARY KEY, "
                + Tenhangtaxi + " NVARCHAR, "
                + Sodienthoai + " NVARCHAR, "
                + Sodienthoai2 + " NVARCHAR, "
                + Diachi + " NVARCHAR, "
                + Website + " NVARCHAR, "
                + Khoanggia + " NVARCHAR, "
                + Facebook + " NVARCHAR, "
                + MaTinhthanh + " NVARCHAR, "
                + Rate + " FLOAT, "
                + Hinhanh + " NVARCHAR, "
                + Giamocua + " FLOAT, "
                + Giakmdau + " FLOAT, "
                + Giakmsau + " FLOAT, "
                + Khoanggia7cho + " FLOAT, "
                + Giamocua7 + " FLOAT, "
                + Giakmdau7 + " FLOAT, "
                + Giakmsau7 + " FLOAT)";
        sqLiteDatabase.execSQL(sql_tbHangTaxi);

        String sql_tinhThanh = "CREATE TABLE IF NOT EXISTS " + tbtinhThanh + "( " + MaTinhthanh + " NVARCHAR NOT NULL PRIMARY KEY, "
                + TenTinhThanh + " NVARCHAR )";
        sqLiteDatabase.execSQL(sql_tinhThanh);
    }

    public SQLiteConfig open() {
        db = this.getWritableDatabase();
        return this;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public Boolean ThemThongBaoMoi(ModelThongBao thongBao) {
        SQLiteDatabase db1 = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(tieuDeThongBao, thongBao.getTieuDeThongBao());
            values.put(noiDungThongBao, thongBao.getNoiDungThongBao());
            values.put(ngayThongBao, thongBao.getNgayThongBao());

            if (db1.insert(tbThongBao, null, values) != -1) {
                db1.close();
                return true;
            } else {
                db1.close();
                return false;
            }

        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "ThemThongBaoMoi => " + e.toString());
        }
        return false;
    }


    public List<ModelThongBao> DanhSachThongBao() {
        List<ModelThongBao> listThongBao = new ArrayList<>();
        listThongBao.add(new ModelThongBao("Thông báo", "Hiện ứng dụng Hitaxi đều có mặt trên Android và iOS. Tuy nhiên, phiên bản đang được thử nghiệm nên dữ liệu còn khá thiếu sót, rất mong người dùng có thể bổ sung thông tin cũng như đóng góp ý kiến cho đội ngũ của chúng tôi hoàn thiện tốt hơn về sản phẩm", "5-03-2017"));
        SQLiteDatabase db2 = this.getWritableDatabase();
        try {
            Cursor cursor = db2.query(tbThongBao, null, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                listThongBao.add(new ModelThongBao(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)));
                cursor.moveToNext();
            }
            cursor.close();
            db2.close();
        } catch (Exception e) {
            db2.close();
            Log.e(getClass().getSimpleName(), "DanhSachThongBao => " + e.toString());
        }
        return listThongBao;

    }


    public ModelThongBao ThongBaoMoi() {
        ModelThongBao thongBao = new ModelThongBao("", "", "");
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + tbThongBao + " ORDER BY " + idThongBao + " DESC;", null);
            ///Cursor cursor = database.query(tbThongBao, null, null, null, null, null, null);
            cursor.moveToFirst();
            thongBao = new ModelThongBao(cursor.getString(1), cursor.getString(2), cursor.getString(3));
            cursor.close();
            database.close();
            return thongBao;
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "ThongBaoMoi => " + e.toString());
        }
        return thongBao;
    }


    public void ThemHangTaxi(List<ModelHangTaxi> list) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            database.execSQL("DELETE FROM " + tbHangTaxi);
            for (ModelHangTaxi modelHangTaxi : list) {
                values.put(Mahangtaxi, modelHangTaxi.getMahangtaxi());
                values.put(Tenhangtaxi, modelHangTaxi.getTenhangtaxi());
                values.put(Sodienthoai, modelHangTaxi.getSodienthoai());
                values.put(Sodienthoai2, modelHangTaxi.getSodienthoai2());
                values.put(Diachi, modelHangTaxi.getDiachi());
                values.put(Website, modelHangTaxi.getWebsite());
                values.put(Khoanggia, modelHangTaxi.getKhoanggia());
                values.put(MaTinhthanh, modelHangTaxi.getMaTinhthanh());
                values.put(Rate, modelHangTaxi.getRate());
                values.put(Hinhanh, modelHangTaxi.getHinhanh());
                values.put(Giamocua, modelHangTaxi.getGiamocua());
                values.put(Giakmdau, modelHangTaxi.getGiakmdau());
                values.put(Giakmsau, modelHangTaxi.getGiakmsau());
                database.insert(tbHangTaxi, null, values);
            }
            database.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "ThemHangTaxi => " + e.toString());
        }
    }

    public List<ModelHangTaxi> listHangTaxi() {
        List<ModelHangTaxi> danhSachTaxi = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            Cursor cursor = database.query(tbHangTaxi, null, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                danhSachTaxi.add(new ModelHangTaxi(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(10),
                        Float.parseFloat(cursor.getString(9)),
                        Float.parseFloat(cursor.getString(11)),
                        Float.parseFloat(cursor.getString(12)),
                        Float.parseFloat(cursor.getString(13)),
                        Float.parseFloat(cursor.getString(14)),
                        Float.parseFloat(cursor.getString(15)),
                        Float.parseFloat(cursor.getString(17)),
                        Float.parseFloat(cursor.getString(18))));
                cursor.moveToNext();
            }
            cursor.close();
            database.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "listHangTaxi => " + e.toString());
        }

        return danhSachTaxi;
    }

    public List<ModelHangTaxi> danhSachTaxiTheoTinhThanh(String maTinhThanh) {
        List<ModelHangTaxi> danhSachTaxi = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            Cursor cursor = database.query(tbHangTaxi, null, MaTinhthanh + "=?", new String[]{maTinhThanh}, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                danhSachTaxi.add(new ModelHangTaxi(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(10),
                        Float.parseFloat(cursor.getString(9)),
                        Float.parseFloat(cursor.getString(11)),
                        Float.parseFloat(cursor.getString(12)),
                        Float.parseFloat(cursor.getString(13)),
                        Float.parseFloat(cursor.getString(14)),
                        Float.parseFloat(cursor.getString(15)),
                        Float.parseFloat(cursor.getString(17)),
                        Float.parseFloat(cursor.getString(18))));
                cursor.moveToNext();
            }
            cursor.close();
            database.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "listHangTaxi => " + e.toString());
        }

        return danhSachTaxi;
    }

    public void ThemTinhThanh(List<ModelTinhThanh> tinhThanhs) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            database.execSQL("DELETE FROM " + tbtinhThanh);
            for (ModelTinhThanh modelTinhThanh : tinhThanhs) {
                values.put(MaTinhthanh, modelTinhThanh.getMaTinhthanh());
                values.put(TenTinhThanh, modelTinhThanh.getTentinhthanh());
                database.insert(tbtinhThanh, null, values);
            }
            database.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "ThemTinhThanh => " + e.toString());
        }

    }

    public List<ModelTinhThanh> listTinhTHanh() {
        List<ModelTinhThanh> list = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            Cursor cursor = database.query(tbtinhThanh, null, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(new ModelTinhThanh(cursor.getString(0), cursor.getString(1)));
                cursor.moveToNext();
            }
            cursor.close();
            database.close();

        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "listTinhTHanh => " + e.toString());
        }
        return list;
    }


    public ModelKhachHang thongTinKhachHang() {
        ModelKhachHang modelKhachHang = new ModelKhachHang();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from tbNguoiDung ", null);
        cursor.moveToFirst();



        return modelKhachHang;
    }



}
