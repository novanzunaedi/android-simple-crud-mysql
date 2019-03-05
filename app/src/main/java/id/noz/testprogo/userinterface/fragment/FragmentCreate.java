package id.noz.testprogo.userinterface.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.noz.testprogo.MainActivity;
import id.noz.testprogo.R;
import id.noz.testprogo.additionalclass.CircularAnimate;
import id.noz.testprogo.additionalclass.EndPoint;
import id.noz.testprogo.data.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static id.noz.testprogo.adapter.SiswaAdapter.DATA_SISWA;

public class FragmentCreate extends Fragment {

    private View rootView;
    private Button btnInput;
    private EditText etNama, etAlamat, etNoTelpon;
    private Context mContext;

    public static FragmentCreate newInstance() {
        FragmentCreate fragmentCreate = new FragmentCreate();
        return fragmentCreate;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create, container, false);
        new CircularAnimate(rootView).run();

        etNama = (EditText) rootView.findViewById(R.id.etNama);
        etAlamat = (EditText) rootView.findViewById(R.id.etAlamat);
        etNoTelpon = (EditText) rootView.findViewById(R.id.etNo_Telpon);
        btnInput = (Button) rootView.findViewById(R.id.btnInput);

        final String sMetode = getArguments().getString("metode");
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sMetode.equalsIgnoreCase("post")){
                    sendData();
                } else {
                    updateData();
                }
            }
        });

        ((MainActivity)getActivity()).changeToolbar("Tambah Data");
        ((MainActivity)getActivity()).hideTambah();
        if (sMetode.equalsIgnoreCase("update")){
            updateDataView();
        }

        return rootView;
    }

    private void sendData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EndPoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try {
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("nama", etNama.getText().toString());
            requestBody.put("alamat", etAlamat.getText().toString());
            requestBody.put("no_telpon", etNoTelpon.getText().toString());
            Call<Object> call = apiInterface.addUser(requestBody);
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    closeThisFrag();
                    System.out.println("DATA RESPONSE CREATE " + response.body().toString());
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        Log.e("TAG", "onResponse: " + object );
                        Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateData(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EndPoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try {
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("id", getArguments().getString("id"));
            requestBody.put("nama", etNama.getText().toString());
            requestBody.put("alamat", etAlamat.getText().toString());
            requestBody.put("no_telpon", etNoTelpon.getText().toString());
            Call<Object> call = apiInterface.editUser(requestBody);
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    System.out.println("DATA RESPONSE UPDATE " + response.body().toString());
                     closeThisFrag();
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        Log.e("TAG", "onResponse: " + object );
                        Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDataView(){
        ((MainActivity)getActivity()).changeToolbar("Update Data");

        etNama.setText(getArguments().getString("nama"));
        etAlamat.setText(getArguments().getString("alamat"));
        etNoTelpon.setText(getArguments().getString("noTelp"));
    }

    private void closeThisFrag(){
        System.out.println("CLOSE FRAGMENT ");
        FragmentHome fragmentHome = FragmentHome.newInstance();
        switchContent(R.id.containerID, fragmentHome);
    }

    public void switchContent(int id, Fragment fragment) {
        System.out.println("SWITCH FRAGMENT JALAN ");
        if (mContext == null)
            return;
        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) mContext;
            Fragment frag = fragment;
            mainActivity.switchContent(id, frag);
        }
    }
}
