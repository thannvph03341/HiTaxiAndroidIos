package hiappvn.com.hitaxi.models;


import java.io.Serializable;
import java.util.List;

/**
 * Created by nongthan on 1/9/17.
 */

public class ModelTrangChuMain implements Serializable{

    String tenDanhMucTaxi;
    List<ModelHangTaxi> listModelHangTaxis;

    public ModelTrangChuMain(String tenDanhMucTaxi, List<ModelHangTaxi> listModelHangTaxis) {
        this.tenDanhMucTaxi = tenDanhMucTaxi;
        this.listModelHangTaxis = listModelHangTaxis;
    }

    public String getTenDanhMucTaxi() {
        return tenDanhMucTaxi;
    }

    public void setTenDanhMucTaxi(String tenDanhMucTaxi) {
        this.tenDanhMucTaxi = tenDanhMucTaxi;
    }

    public List<ModelHangTaxi> getListModelHangTaxis() {
        return listModelHangTaxis;
    }

    public void setListModelHangTaxis(List<ModelHangTaxi> listModelHangTaxis) {
        this.listModelHangTaxis = listModelHangTaxis;
    }
}
