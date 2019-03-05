package id.noz.testprogo.data.rest;


import java.util.Map;
import id.noz.testprogo.data.model.Data;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;


public interface ApiInterface {

    @GET("api/siswa/read.php")
    Call<Data> getData();

    @POST("api/siswa/create.php")
    Call<Object> addUser(@Body Map<String, String> body);

    @PATCH("api/siswa/update.php")
    Call<Object> editUser(@Body Map<String, String> body);

    @POST("api/siswa/delete.php") // used for Delete.. depending the REST API
    Call<Object> delUser(@Body Map<String, String> body);

}
