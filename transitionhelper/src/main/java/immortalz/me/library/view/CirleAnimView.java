package immortalz.me.library.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;

import immortalz.me.library.R;
import immortalz.me.library.bean.InfoBean;


/**
 * Created by Mr_immortalZ on 2016/10/13.
 * email : mr_immortalz@qq.com
 */

public class CirleAnimView extends RenderView{

    private Canvas mCanvas;
    private Bitmap originalBitmap; // 画布bitmap
    private Bitmap drawBitmap;
    private Xfermode xfermode;
    //画布宽度
    private int canvaswWidth = 1080;
    //画布高度
    private int canvasHeight = 1920;
    //画圆
    private int cX;
    private int cY;
    private int radius;
    private int maxRadius;
    private int increaseSpeed = 5;
    boolean startCircleAnim = false;

    public CirleAnimView(Context context) {
        super(context);
        init(context);
    }

    public CirleAnimView(Context context, Bitmap bitmap) {
        super(context);
        drawBitmap = bitmap;
        init(context);
    }

    public CirleAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CirleAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.showmethod_end_color));

        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

        setWillNotDraw(false);
    }

    @Override
    public void startCircleAnim(InfoBean bean) {
        originalBitmap = Bitmap.createBitmap(bean.windowWidth, bean.windowHeight, Bitmap.Config.ARGB_4444);
        canvaswWidth = bean.windowWidth;
        canvasHeight = bean.windowHeight;
        mCanvas = new Canvas(originalBitmap);
        startCircleAnim = true;
        cX = bean.targetWidth / 2 + bean.targetRect.left;
        cY = bean.targetHeight / 2 + bean.targetRect.top - bean.statusBarHeight - bean.titleHeight;
        radius = (int) Math.hypot(bean.targetWidth / 2, bean.targetHeight / 2) / 4;
        maxRadius = (int) Math.hypot(canvaswWidth, canvasHeight);
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (startCircleAnim) {
            zoomDraw(canvas);
        } else if (drawBitmap != null) {
            canvas.drawBitmap(drawBitmap, 0, 0, null);
        }
    }

    private void zoomDraw(Canvas canvas) {
        if (radius < maxRadius) {
            if (drawBitmap != null) {
                mCanvas.drawBitmap(drawBitmap, null, new RectF(0, 0, canvaswWidth, canvasHeight), null);
            } else {
                mCanvas.drawRect(0, 0, canvaswWidth, canvasHeight, mPaint);
            }
            mPaint.setXfermode(xfermode);
            mCanvas.drawCircle(cX, cY, radius, mPaint);
            radius += increaseSpeed;
            increaseSpeed += 6;
            mPaint.setXfermode(null);
            canvas.drawBitmap(originalBitmap, 0, 0, null);
            invalidate();
        } else {
            startCircleAnim = false;
            setVisibility(GONE);
            //recycle
            if (originalBitmap != null) {
                originalBitmap = null;
            }
            if (drawBitmap != null) {
                drawBitmap = null;
            }
        }
    }

}
