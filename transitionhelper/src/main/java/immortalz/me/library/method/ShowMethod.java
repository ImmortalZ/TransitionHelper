package immortalz.me.library.method;

import android.animation.AnimatorSet;
import android.view.View;
import android.widget.ImageView;

import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.view.RenderView;


/**
 * Created by Mr_immortalZ on 2016/10/24.
 * email : mr_immortalz@qq.com
 */

public abstract class ShowMethod {

    public AnimatorSet set = new AnimatorSet();


    protected int duration = 480;

    public abstract void translate(InfoBean bean, RenderView parent, View child);

    /**
     * load copyView which just a temp view.
     * the copyView is show when it's translating.
     */
    public abstract void loadCopyView(InfoBean bean,ImageView copyView);

    /**
     * load targetView
     */
    public void loadTargetView(InfoBean bean, ImageView targetView){

    }
}
