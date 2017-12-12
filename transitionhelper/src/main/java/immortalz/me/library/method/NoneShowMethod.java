package immortalz.me.library.method;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import immortalz.me.library.R;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.expose.base.ExposeView;


/**
 * Created by Mr_immortalZ on 2016/10/24.
 * email : mr_immortalz@qq.com
 */

public  class NoneShowMethod extends ShowMethod {

    public int startColor;
    public int endColor;

    @Override
    public void translate(InfoBean bean, ExposeView parent, View child) {

        startColor = parent.getResources().getColor(R.color.transitionhelper_showmethod_start_color);
        endColor = parent.getResources().getColor(R.color.transitionhelper_showmethod_end_color);

        set.setInterpolator(new AccelerateInterpolator());
        set.setDuration(showDuration).start();
    }

    @Override
    public void loadPlaceholder(InfoBean bean, ImageView placeholder) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(placeholder,"rotation",0,180),
                ObjectAnimator.ofFloat(placeholder, "scaleX", 1, 0),
                ObjectAnimator.ofFloat(placeholder, "scaleY", 1, 0)
        );
        set.setInterpolator(new AccelerateInterpolator());
        set.setDuration(showDuration / 4 * 5).start();
    }

    @Override
    public void loadTargetView(InfoBean bean, View targetView) {

    }


}
