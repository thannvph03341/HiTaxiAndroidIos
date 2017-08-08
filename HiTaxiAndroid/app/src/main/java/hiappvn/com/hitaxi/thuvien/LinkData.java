package hiappvn.com.hitaxi.thuvien;


/**
 * Created by nongthan on 1/9/17.
 */

public class LinkData {

    public static final String hostUrl = "http://hitaxi.vn/";

    public static final String keyGoogleApi = "AIzaSyCWa0b0qghBA0r_LhHHtzGMNZIaYUYCSLM";
    public static final String linkCacTinhThanh = hostUrl + "api/TinhthanhApi";
    public static final String linkHangTaxi = hostUrl + "api/HangTaxiApi/";
    public static final String linkDanhGia =  hostUrl + "api/Danhgia";
    public static final String linkYeuThich =  hostUrl + "api/Yeuthich";
 
    public static final String linkHangTaxiYeuThich(String maNguoiDung) {
        return new String( hostUrl + "HangTaxiApi/" + maNguoiDung + "/GetHangtaxiYeuthich");
    }
    public static final String linkLayKhoangCach(String diemBatDau, String diemKetThuc){
        return new String("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + diemBatDau + "&destinations= " + diemKetThuc + " &key=AIzaSyCWa0b0qghBA0r_LhHHtzGMNZIaYUYCSLM");
    }

    public static final String linhAnhFacebook (String idNguoiDung) {
        return new String("https://graph.facebook.com/" + idNguoiDung + "/picture?type=large");
    }
}
