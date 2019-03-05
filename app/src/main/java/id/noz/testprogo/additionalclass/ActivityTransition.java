package id.noz.testprogo.additionalclass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import id.noz.testprogo.R;
import id.co.noz.circular.cA;

public class ActivityTransition {
    private Activity activity;

    public ActivityTransition(Context context) {
        activity = ((Activity) context);
    }

    public void finishCir(){
        activity.finish();
    }

    public void StartNextActivity(View v, final Intent i){
        cA.fullActivity(activity, v)
                .colorOrImageRes(R.color.colorPrimaryDark)
                .go(new cA.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {
                        activity.startActivity(i);
                    }
                });
    }

    public void FinishThisAndStartNextActivity(View v, final Intent i){
        cA.fullActivity(activity, v)
                .colorOrImageRes(R.color.colorPrimaryDark)
                .go(new cA.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {
                        activity.startActivity(i);
                        activity.finish();
                    }
                });
    }
}























































































