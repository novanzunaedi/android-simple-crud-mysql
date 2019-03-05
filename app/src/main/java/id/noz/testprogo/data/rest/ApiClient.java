package id.noz.testprogo.data.rest;


import id.noz.testprogo.additionalclass.EndPoint;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(EndPoint.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(EndPoint.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}