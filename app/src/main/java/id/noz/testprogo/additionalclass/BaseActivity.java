package id.noz.testprogo.additionalclass;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity {

    protected ActivityTransition activityTransition;
    protected Context context;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context = this;
        activityTransition = new ActivityTransition(context);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
