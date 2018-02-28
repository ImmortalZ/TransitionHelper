package immortalz.me.library.method;

import android.animation.AnimatorSet;
import android.view.View;
import android.widget.ImageView;

import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.expose.base.ExposeView;


/**
 * Created by Mr_immortalZ on 2016/10/24.
 * email : mr_immortalz@qq.com
 */

public abstract class ShowMethod {

    public AnimatorSet set = new AnimatorSet();

    public static final int DEFALUT_DURATION = 240;

    public int showDuration = DEFALUT_DURATION;

    public abstract void translate(InfoBean bean, ExposeView parent, View child);

    public void reviseInfo(InfoBean info) {

    }

    /**
     * load placeholder which just a temp view.
     * the placeholder is show when it's translating.
     */
    public abstract void loadPlaceholder(InfoBean bean, ImageView placeholder);

    /**
     * load targetView
     */
    public abstract void loadTargetView(InfoBean bean, View targetView);


    public void setShowDuration(int showDuration) {
        this.showDuration = showDuration;
    }
}
