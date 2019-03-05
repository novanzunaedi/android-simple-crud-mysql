package id.noz.testprogo.additionalclass;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

public class CircularAnimate {

    private View rootView;
    private Context mContext;

    public CircularAnimate(Context context, View view) {
        this.mContext = context;
        this.rootView = view;
    }

    public CircularAnimate(View view) {
        this.rootView = view;
    }

    public void run(){
        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);

                int finalRadius = Math.max(rootView.getWidth(), rootView.getHeight());
                int centerX = (int) (rootView.getX() + rootView.getWidth() / 2);
                int centreY = (int) (rootView.getY() + rootView.getHeight() / 2);

                Animator anim = ViewAnimationUtils.createCircularReveal(rootView, centerX, centreY, 0, finalRadius);
                anim.setDuration(700);
                anim.start();
            }
        });
    }
}
