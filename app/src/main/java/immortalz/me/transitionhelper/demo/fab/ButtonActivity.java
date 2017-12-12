package immortalz.me.transitionhelper.demo.fab;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import immortalz.me.library.TransitionsHeleper;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.method.ColorShowMethod;
import immortalz.me.transitionhelper.R;
import immortalz.me.transitionhelper.base.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/10/30.
 * email : mr_immortalz@qq.com
 */

public class ButtonActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_fab_detail;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionsHeleper.build(this)
                .setShowMethod(new ColorShowMethod(R.color.bg_purple, R.color.bg_teal) {
                    @Override
                    public void loadPlaceholder(InfoBean bean, ImageView placeholder) {
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(
                                ObjectAnimator.ofFloat(placeholder, "alpha", 1f, 0f),
                                ObjectAnimator.ofFloat(placeholder, "scaleX", 1.5f, 1f),
                                ObjectAnimator.ofFloat(placeholder, "scaleY", 1.5f, 1f)
                        );
                        set.setInterpolator(new AccelerateInterpolator());
                        set.setDuration(showDuration / 4 * 5).start();
                    }

                    @Override
                    public void loadTargetView(InfoBean bean, View targetView) {

                    }
                })
                .setExposeColor(getResources().getColor(R.color.bg_teal))
                .show();
    }
}
