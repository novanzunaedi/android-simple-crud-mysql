package id.noz.testprogo.userinterface.fragment;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import id.noz.testprogo.MainActivity;
import id.noz.testprogo.R;
import id.noz.testprogo.adapter.SiswaAdapter;
import id.noz.testprogo.additionalclass.CheckInternet;
import id.noz.testprogo.additionalclass.CircularAnimate;
import id.noz.testprogo.data.model.Data;
import id.noz.testprogo.data.model.Siswa;
import id.noz.testprogo.data.rest.ApiClient;
import id.noz.testprogo.data.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView rv;
    SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar pbLoading;
    private Context mContext;
    private View rootView;
    private List<Siswa> dataSiswa;
    private SiswaAdapter siswaAdapter;

    private GridLayoutManager gridLayoutManager;
    private Bundle mBundle;
    private CheckInternet checkInternet;

    public static FragmentHome newInstance() {
        FragmentHome fragmentHome = new FragmentHome();
        return fragmentHome;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        new CircularAnimate(rootView).run();
        mContext = getActivity();
        checkInternet = new CheckInternet(mContext);

        gridLayoutManager = new GridLayoutManager(mContext, 1);

        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        pbLoading = (ProgressBar) rootView.findViewById(R.id.pbLoading);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);

        rv.setNestedScrollingEnabled(true);
        rv.setLayoutManager(gridLayoutManager);

        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
            }
        });

        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                swipeRefreshLayout.setEnabled(gridLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark,
                R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimaryDark));

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(true);
                }
                callSiswa();
                swipeRefreshLayout.setEnabled(false);
            }
        });


        ((MainActivity)getActivity()).changeToolbar("List Data");
        ((MainActivity)getActivity()).showTambah();

         return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(checkInternet.isOnline()){
            callSiswa();
        } else {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(false);
            }
            Toast.makeText(mContext, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void callSiswa() {
        pbLoading.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Data> call = apiService.getData();
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                pbLoading.setVisibility(View.GONE);
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                handleResponse(response);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                pbLoading.setVisibility(View.GONE);
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                System.out.println("Failed to get Siswa!");
            }
        });
    }

    private void handleResponse(Response<Data> response) {
        dataSiswa = response.body().getData();

        siswaAdapter = new SiswaAdapter(mContext, dataSiswa);
        rv.setAdapter(siswaAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onRefresh() {
        if(checkInternet.isOnline()){
            callSiswa();
        } else {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(false);
            }
            Toast.makeText(mContext, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }
}
