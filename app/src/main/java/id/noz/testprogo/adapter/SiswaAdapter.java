package id.noz.testprogo.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.noz.testprogo.MainActivity;
import id.noz.testprogo.R;
import id.noz.testprogo.additionalclass.EndPoint;
import id.noz.testprogo.data.model.Siswa;
import id.noz.testprogo.data.rest.ApiInterface;
import id.noz.testprogo.userinterface.fragment.FragmentCreate;
import id.noz.testprogo.userinterface.fragment.FragmentHome;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SiswaAdapter extends RecyclerView.Adapter<SiswaAdapter.MainVH> {

    private Context mContext;
    private List<Siswa> datumList;

    public final static String DATA_SISWA = "data_siswa";
    public final static String DATA_ID_SISWA = "data_id_siswa";

    public SiswaAdapter(Context context, List<Siswa> datum) {
        this.mContext = context;
        this.datumList = datum;
    }

    @Override
    public MainVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_siswa, parent, false);
        return new MainVH(view, this);
    }

    @Override
    public void onBindViewHolder(MainVH holder, final int position) {
        final Siswa datums = datumList.get(position);

        holder.tvNama.setText(datums.getNama());
        holder.tvAlamat.setText(datums.getAlamat());
        holder.tvNoTelp.setText(datums.getNoTelpon());

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setTitle("Hapus Data !!");
                alertDialogBuilder.setMessage("Apakah Anda Ingin Menghapus data " + datums.getNama());
                alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteData(datums.getId());
                    }
                });

                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
//                        }
                    }
                });

                alertDialogBuilder.show();
            }
        });

        holder.ivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentJump(datums, position);
            }
        });
    }

    private void deleteData(String idDelete){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EndPoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try {
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("id", idDelete);
            Call<Object> call = apiInterface.delUser(requestBody);
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {

                    FragmentHome fragmentHome = FragmentHome.newInstance();
                    switchContent(R.id.containerID, fragmentHome);

                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        Log.e("TAG", "onResponse: " + object );
                        Toast.makeText(mContext, object.getString("message"), Toast.LENGTH_SHORT).show();
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

    private void fragmentJump(Siswa siswas, int index) {
        FragmentCreate fragmentCreate = FragmentCreate.newInstance();

        Bundle  mBundle = new Bundle();
        mBundle.putString("metode", "update");
        mBundle.putString("id", siswas.getId());
        mBundle.putString("nama", siswas.getNama());
        mBundle.putString("alamat", siswas.getAlamat());
        mBundle.putString("noTelp", siswas.getNoTelpon());
        mBundle.putInt(DATA_ID_SISWA, index); // pass id data in realm by bundle

        fragmentCreate.setArguments(mBundle);
        switchContent(R.id.containerID, fragmentCreate);
    }

    public void switchContent(int id, Fragment fragment) {
        if (mContext == null)
            return;
        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) mContext;
            Fragment frag = fragment;
            mainActivity.switchContent(id, frag);
        }
    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    static class MainVH extends RecyclerView.ViewHolder {

        private SiswaAdapter adapter;

        ImageView ivDelete, ivUpdate;
        TextView tvNama;
        TextView tvAlamat;
        TextView tvNoTelp;

        MainVH(View itemView, SiswaAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            tvNama = itemView.findViewById(R.id.tvnama);
            tvAlamat = itemView.findViewById(R.id.tvalamat);
            tvNoTelp = itemView.findViewById(R.id.tvno_telpon);
            ivUpdate = itemView.findViewById(R.id.ivUpdate);
            ivDelete = itemView.findViewById(R.id.ivDelete);

        }

    }
}


