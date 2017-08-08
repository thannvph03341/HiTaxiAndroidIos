package hiappvn.com.hitaxi.models;

import java.io.Serializable;

/**
 * Created by nongthan on 1/9/17.
 */

public class ModelHangTaxi implements Serializable{

    String mahangtaxi, tenhangtaxi, sodienthoai, sodienthoai2, diachi, website, khoanggia, facebook, maTinhthanh, hinhanh;
    Float rate, giamocua, giakmdau, giakmsau, Khoanggia7cho, Giamocua7, Giakmdau7, Giakmsau7;

    public ModelHangTaxi(String mahangtaxi, String tenhangtaxi, String sodienthoai, String sodienthoai2, String diachi, String website, String khoanggia, String facebook, String maTinhthanh, String hinhanh, Float rate, Float giamocua, Float giakmdau, Float giakmsau, Float khoanggia7cho, Float giamocua7, Float giakmdau7, Float giakmsau7) {
        this.mahangtaxi = mahangtaxi;
        this.tenhangtaxi = tenhangtaxi;
        this.sodienthoai = sodienthoai;
        this.sodienthoai2 = sodienthoai2;
        this.diachi = diachi;
        this.website = website;
        this.khoanggia = khoanggia;
        this.facebook = facebook;
        this.maTinhthanh = maTinhthanh;
        this.hinhanh = hinhanh;
        this.rate = rate;
        this.giamocua = giamocua;
        this.giakmdau = giakmdau;
        this.giakmsau = giakmsau;
        Khoanggia7cho = khoanggia7cho;
        Giamocua7 = giamocua7;
        Giakmdau7 = giakmdau7;
        Giakmsau7 = giakmsau7;
    }

    public String getMahangtaxi() {
        return mahangtaxi;
    }

    public void setMahangtaxi(String mahangtaxi) {
        this.mahangtaxi = mahangtaxi;
    }

    public String getTenhangtaxi() {
        return tenhangtaxi;
    }

    public void setTenhangtaxi(String tenhangtaxi) {
        this.tenhangtaxi = tenhangtaxi;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getSodienthoai2() {
        return sodienthoai2;
    }

    public void setSodienthoai2(String sodienthoai2) {
        this.sodienthoai2 = sodienthoai2;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getKhoanggia() {
        return khoanggia;
    }

    public void setKhoanggia(String khoanggia) {
        this.khoanggia = khoanggia;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getMaTinhthanh() {
        return maTinhthanh;
    }

    public void setMaTinhthanh(String maTinhthanh) {
        this.maTinhthanh = maTinhthanh;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Float getGiamocua() {
        return giamocua;
    }

    public void setGiamocua(Float giamocua) {
        this.giamocua = giamocua;
    }

    public Float getGiakmdau() {
        return giakmdau;
    }

    public void setGiakmdau(Float giakmdau) {
        this.giakmdau = giakmdau;
    }

    public Float getGiakmsau() {
        return giakmsau;
    }

    public void setGiakmsau(Float giakmsau) {
        this.giakmsau = giakmsau;
    }

    public Float getKhoanggia7cho() {
        return Khoanggia7cho;
    }

    public void setKhoanggia7cho(Float khoanggia7cho) {
        Khoanggia7cho = khoanggia7cho;
    }

    public Float getGiamocua7() {
        return Giamocua7;
    }

    public void setGiamocua7(Float giamocua7) {
        Giamocua7 = giamocua7;
    }

    public Float getGiakmdau7() {
        return Giakmdau7;
    }

    public void setGiakmdau7(Float giakmdau7) {
        Giakmdau7 = giakmdau7;
    }

    public Float getGiakmsau7() {
        return Giakmsau7;
    }

    public void setGiakmsau7(Float giakmsau7) {
        Giakmsau7 = giakmsau7;
    }
}
