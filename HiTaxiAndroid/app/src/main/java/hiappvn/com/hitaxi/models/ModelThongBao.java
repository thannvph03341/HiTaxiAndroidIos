package hiappvn.com.hitaxi.models;

import java.io.Serializable;

/**
 * Created by nongthan on 1/10/17.
 */

public class ModelThongBao implements Serializable {

    String tieuDeThongBao, noiDungThongBao, ngayThongBao;

    public ModelThongBao(String tieuDeThongBao, String noiDungThongBao, String ngayThongBao) {
        this.tieuDeThongBao = tieuDeThongBao;
        this.noiDungThongBao = noiDungThongBao;
        this.ngayThongBao = ngayThongBao;
    }

    public String getTieuDeThongBao() {
        return tieuDeThongBao;
    }

    public void setTieuDeThongBao(String tieuDeThongBao) {
        this.tieuDeThongBao = tieuDeThongBao;
    }

    public String getNoiDungThongBao() {
        return noiDungThongBao;
    }

    public void setNoiDungThongBao(String noiDungThongBao) {
        this.noiDungThongBao = noiDungThongBao;
    }

    public String getNgayThongBao() {
        return ngayThongBao;
    }

    public void setNgayThongBao(String ngayThongBao) {
        this.ngayThongBao = ngayThongBao;
    }
}
