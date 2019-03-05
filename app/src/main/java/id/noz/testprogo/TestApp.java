package id.noz.testprogo;

import android.app.Application;
import id.co.noz.circular.cA;

public class TestApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        cA.init(400, 400, R.color.colorPrimary);

    }

    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory();

    }
}
