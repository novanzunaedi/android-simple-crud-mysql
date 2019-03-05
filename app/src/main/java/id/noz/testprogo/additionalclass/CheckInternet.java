package id.noz.testprogo.additionalclass;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternet {

    private Context context;

    public CheckInternet(Context context) { this.context = context; }

    public boolean isOnline(){
        try{
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivity != null){
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if(info != null){
                    for (int i = 0; i<info.length; i++){
                        if (info[i].getState() == NetworkInfo.State.CONNECTED){
                            return true;
                        }
                    }
                }
            }
        }catch (Exception e){}
        return false;
    }
}





























