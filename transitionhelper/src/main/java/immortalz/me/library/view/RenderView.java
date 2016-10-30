package immortalz.me.library.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import immortalz.me.library.bean.InfoBean;

/**
 * Created by Mr_immortalZ on 2016/10/29.
 * email : mr_immortalz@qq.com
 */

public abstract class RenderView extends RelativeLayout {
    protected int paintColor;
    protected Paint mPaint;

    public RenderView(Context context) {
        super(context);
    }

    public RenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void startCircleAnim(InfoBean bean);

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
        mPaint.setColor(paintColor);
    }
}
