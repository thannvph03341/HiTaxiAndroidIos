package hiappvn.com.hitaxi.models;

import java.io.Serializable;

/**
 * Created by nongthan on 1/2/17.
 */

public class ModelTinhThanh implements Serializable {
    String maTinhthanh, tentinhthanh;

    public ModelTinhThanh(String maTinhthanh, String tentinhthanh) {
        this.maTinhthanh = maTinhthanh;
        this.tentinhthanh = tentinhthanh;
    }


    public String getMaTinhthanh() {
        return maTinhthanh;
    }

    public void setMaTinhthanh(String maTinhthanh) {
        this.maTinhthanh = maTinhthanh;
    }

    public String getTentinhthanh() {
        return tentinhthanh;
    }

    public void setTentinhthanh(String tentinhthanh) {
        this.tentinhthanh = tentinhthanh;
    }
}
