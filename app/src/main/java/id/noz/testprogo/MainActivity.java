package id.noz.testprogo;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import id.noz.testprogo.additionalclass.BaseActivity;
import id.noz.testprogo.data.model.Data;
import id.noz.testprogo.data.rest.ApiClient;
import id.noz.testprogo.data.rest.ApiInterface;
import id.noz.testprogo.userinterface.fragment.FragmentCreate;
import id.noz.testprogo.userinterface.fragment.FragmentHome;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView txtTittle, tvTambahData;
    private FragmentHome fragmentHome;
    private ImageView ivAdd;
    private RelativeLayout rlTambah;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        txtTittle = (TextView) findViewById(R.id.txtTittle);
        tvTambahData = (TextView) findViewById(R.id.tvTambahData);
        rlTambah = (RelativeLayout) findViewById(R.id.rlTambah);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_camera);

        fragmentHome = FragmentHome.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerID, fragmentHome)
                .commit();
        ivAdd = (ImageView) findViewById(R.id.ivAdd);

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahDataSiswa();
            }
        });
        
        tvTambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahDataSiswa();
            }
        });

        txtTittle.setText("List Data");
    }
    
    private void tambahDataSiswa(){
        FragmentCreate fragmentCreate = FragmentCreate.newInstance();
        Bundle  mBundle = new Bundle();
        mBundle.putString("metode", "post");
        fragmentCreate.setArguments(mBundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.containerID, fragmentCreate).commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_camera:
                FragmentHome fragmentHome = FragmentHome.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.containerID, fragmentHome).commit();
                break;
            case R.id.ivMore:
                Toast.makeText(this, "Under Maintenance!!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void changeToolbar(String tittle){
        txtTittle.setText(tittle);
    }

    public void hideTambah(){
        rlTambah.setVisibility(View.GONE);
    }

    public void showTambah(){
        rlTambah.setVisibility(View.VISIBLE);
    }

    public void switchContent(int id, Fragment fragment) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        ft.addToBackStack(null);
        ft.commit();
    }
}
