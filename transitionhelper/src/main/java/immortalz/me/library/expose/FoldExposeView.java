package immortalz.me.library.expose;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import immortalz.me.library.expose.base.ExposeView;


/**
 * Created by Mr_immortalZ on 2017/12/6.
 * email : mr_immortalz@qq.com
 */

public class FoldExposeView extends ExposeView {

    public static final int FOLD_TYPE_HORIZONTAL = 0;
    public static final int FOLD_TYPE_VERTICAL = 1;

    private int foldType = FOLD_TYPE_VERTICAL;

    public FoldExposeView(Context context) {
        super(context);
    }

    public FoldExposeView(Context context, int foldType) {
        super(context);
        this.foldType = foldType;
    }

    @Override
    public void animDrawing(Canvas canvas, Paint paint) {
        switch (foldType) {

            case FOLD_TYPE_HORIZONTAL:
                canvas.drawRect(startExposeX - exposeWidth, 0, startExposeX + exposeWidth, screenHeight, paint);
                break;
            case FOLD_TYPE_VERTICAL:
                canvas.drawRect(0, startExposeY - exposeWidth, screenWidth, startExposeY + exposeWidth, paint);
                break;
        }
    }
}
