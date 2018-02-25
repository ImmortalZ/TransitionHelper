package immortalz.me.library.expose.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import immortalz.me.library.R;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.listener.ExposeListener;


/**
 * Created by Mr_immortalZ on 2017/12/6.
 * email : mr_immortalz@qq.com
 */

public abstract class ExposeView extends RelativeLayout {
    private Context context;
    protected Paint exposePaint;
    private Canvas exposeCanvas;
    private Bitmap canvasBitmap; // 画布bitmap
    private Bitmap inflateBitmap;
    private Xfermode xfermode;

    //画布宽度
    protected int screenWidth = 1080;
    //画布高度
    protected int screenHeight = 1920;
    private int maxExposeWidth;
    protected int exposeWidth;
    protected int startExposeX;
    protected int startExposeY;
    private int exposeSpeed = 5;
    private int exposeAcceleration = 7;
    private int exposeColor = getResources().getColor(R.color.transitionhelper_showmethod_end_color);
    private boolean useInflateExpose = false;
    private boolean isResetExposeColor = false;

    private ExposeListener mExposeListener;
    private ExposeType mExposeType = ExposeType.DEFAULT;
    private ExposeStatus exposeStatus = ExposeStatus.PENDDING;


    public ExposeView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        exposePaint = new Paint();
        exposePaint.setStyle(Paint.Style.FILL);
        exposePaint.setAntiAlias(true);
        exposePaint.setColor(exposeColor);

        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

        setWillNotDraw(false);
    }

    public void startExposeAnim(InfoBean bean) {
        screenWidth = bean.windowWidth;
        screenHeight = bean.windowHeight;
        canvasBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_4444);
        if (canvasBitmap != null && !canvasBitmap.isRecycled()) {
            exposeCanvas = new Canvas(canvasBitmap);
        }
        exposeStatus = ExposeStatus.SHOW;
        startExposeX = bean.targetWidth / 2 + bean.targetRect.left;
        startExposeY = bean.targetHeight / 2 + bean.targetRect.top - bean.statusBarHeight - bean.titleHeight;
        exposeWidth = Math.min((int) Math.hypot(bean.targetWidth / 2, bean.targetHeight / 2) / 4, exposeSpeed);
        maxExposeWidth = (int) Math.hypot(Math.max(startExposeX, screenWidth - startExposeX), Math.max(startExposeY, screenHeight - startExposeY));
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
        if (mExposeListener != null) {
            mExposeListener.onExposeStart();
        }
        if (inflateBitmap != null) {
            mExposeType = ExposeType.INFLATE;
        } else {
            mExposeType = ExposeType.DEFAULT;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (mExposeType) {
            case DEFAULT:
                if (exposeStatus == ExposeStatus.SHOW) {
                    exposeDraw(canvas);
                } else {
                    //do nothing
                }
                break;
            case INFLATE:
                if (exposeStatus == ExposeStatus.SHOW) {
                    exposeDraw(canvas);
                } else if (exposeStatus != ExposeStatus.STOP) {
                    if (inflateBitmap != null && !inflateBitmap.isRecycled()) {
                        canvas.drawBitmap(inflateBitmap, 0, 0, null);
                    }
                }
                break;
        }
    }

    private void exposeDraw(Canvas canvas) {
        if ((exposeWidth - exposeSpeed) < maxExposeWidth) {
            if (mExposeListener != null) {
                mExposeListener.onExposeProgrees((float) (exposeWidth - exposeSpeed) / maxExposeWidth);
            }
            if (useInflateExpose && inflateBitmap != null) {
                exposeCanvas.drawBitmap(inflateBitmap, null, new RectF(0, 0, screenWidth, screenHeight), null);
            } else {
                exposeCanvas.drawRect(0, 0, screenWidth, screenHeight, exposePaint);
            }
            exposePaint.setXfermode(xfermode);
            //exposeCanvas.drawCircle(startExposeX, startExposeY, exposeWidth, mPaint);
            animDrawing(exposeCanvas, exposePaint);
            //加速度的方式更改揭露动画速度
            exposeWidth += exposeSpeed;
            exposeSpeed += exposeAcceleration;
            exposePaint.setXfermode(null);
            if (canvasBitmap != null && !canvasBitmap.isRecycled()) {
                canvas.drawBitmap(canvasBitmap, 0, 0, null);
            }
            invalidate();
        } else {
            exposeStatus = ExposeStatus.STOP;
            setVisibility(GONE);
            if (mExposeListener != null) {
                mExposeListener.onExposeEnd();
            }
            //recycle
            recyle();
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean result = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                result = true;
        }
        return result;
    }

    private void recyle() {
        if (canvasBitmap != null) {
            canvasBitmap.recycle();
            canvasBitmap = null;
        }
        if (inflateBitmap != null) {
            inflateBitmap.recycle();
            inflateBitmap = null;
        }
    }

    public void setInflateBitmap(Bitmap inflateBitmap) {
        this.inflateBitmap = inflateBitmap;
        mExposeType = ExposeType.INFLATE;
        invalidate();
    }

    public void setExposeListener(ExposeListener exposeListener) {
        this.mExposeListener = exposeListener;
    }

    public void setExposeColor(int exposeColor) {
        setExposeColor(exposeColor, false);
    }

    public void setExposeAcceleration(int exposeAcceleration) {
        this.exposeAcceleration = exposeAcceleration;
    }

    public void setExposeColor(int exposeColor, boolean useInflate) {
        this.useInflateExpose = useInflate;
        this.isResetExposeColor = true;
        if (!useInflate) {
            this.exposeColor = exposeColor;
            exposePaint.setColor(exposeColor);
        }
    }

    public void stop() {
        exposeStatus = ExposeStatus.STOP;
        //recycle
        recyle();
    }

    public boolean isResetExposeColor() {
        return isResetExposeColor;
    }


    public abstract void animDrawing(Canvas canvas, Paint paint);


}
