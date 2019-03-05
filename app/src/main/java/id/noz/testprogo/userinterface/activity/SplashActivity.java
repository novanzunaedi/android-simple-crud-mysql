package id.noz.testprogo.userinterface.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import id.noz.testprogo.MainActivity;
import id.noz.testprogo.R;
import id.noz.testprogo.additionalclass.BaseActivity;

public class SplashActivity extends BaseActivity {

    private ImageView ivSplash;
    private ProgressBar pbLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        ivSplash = (ImageView) findViewById(R.id.ivSplash);
        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);

    }

    @Override
    protected void onResume() {
        super.onResume();
        startSplash();
    }


    private void startSplash() {
        new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilDone) {

            }

            public void onFinish() {
                pbLoading.setVisibility(View.GONE);
                activityTransition.FinishThisAndStartNextActivity(ivSplash, new Intent(SplashActivity.this, MainActivity.class));
            }
        }.start();
    }
}


