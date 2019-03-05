package id.noz.testprogo.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("data")
    @Expose
    private List<Siswa> data = null;

    public List<Siswa> getData() {
        return data;
    }

    public void setData(List<Siswa> data) {
        this.data = data;
    }

}
