package immortalz.me.library;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.HashMap;

import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.method.InflateShowMethod;
import immortalz.me.library.method.NoneShowMethod;
import immortalz.me.library.method.ShowMethod;
import immortalz.me.library.view.CirleAnimView;

/**
 * Created by Mr_immortalZ on 2016/10/18.
 * email : mr_immortalz@qq.com
 */

public class TransitionsHeleper {


    private static TransitionsHeleper INSTANCE;


    private static HashMap<String, InfoBean> staticMap = new HashMap<>();

    private static ShowMethod showMethod = new NoneShowMethod();


    private TransitionsHeleper() {
    }

    public static TransitionsHeleper getInstance() {
        if (INSTANCE == null) {
            synchronized (TransitionsHeleper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TransitionsHeleper();
                }
            }
        }
        return INSTANCE;
    }

    public static void startAcitivty(final Activity activity, final Class<?> cls, final View view) {
        startAcitivty(activity, cls, view, 0);
    }

    public static void startAcitivty(final Activity activity, final Class<?> cls, final View view, final Integer imgId) {
        final Intent intent = new Intent(activity, cls);
        final InfoBean bean = new InfoBean();
        view.post(new Runnable() {
            @Override
            public void run() {
                //get statusBar height
                view.getWindowVisibleDisplayFrame(bean.originRect);
                bean.statusBarHeight = bean.originRect.top;
                //get Origin View's rect
                view.getGlobalVisibleRect(bean.originRect);
                bean.originWidth = bean.originRect.right - bean.originRect.left;
                bean.originHeight = bean.originRect.bottom - bean.originRect.top;
                if (imgId == 0) {
                    bean.bitmap = createBitmap(view, bean.originWidth, bean.originHeight);
                } else {
                    bean.setImgId(imgId);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                staticMap.put(cls.getName(), bean);
                activity.startActivity(intent);
                activity.overridePendingTransition(0,0);
            }
        });
    }

    public static void startAcitivty(final Activity activity, final Class<?> cls, final View view, final String imgUrl) {
        final Intent intent = new Intent(activity, cls);
        final InfoBean bean = new InfoBean();
        view.post(new Runnable() {
            @Override
            public void run() {
                //get statusBar height
                view.getWindowVisibleDisplayFrame(bean.originRect);
                bean.statusBarHeight = bean.originRect.top;
                //get Origin View's rect
                view.getGlobalVisibleRect(bean.originRect);
                bean.originWidth = bean.originRect.right - bean.originRect.left;
                bean.originHeight = bean.originRect.bottom - bean.originRect.top;
                if (TextUtils.isEmpty(imgUrl)) {
                    bean.bitmap = createBitmap(view, bean.originWidth, bean.originHeight);
                } else {
                    bean.setImgUrl(imgUrl);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                staticMap.put(cls.getName(), bean);
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }
        });
    }


    public void show(final Activity activity, final ImageView targetView) {
        final InfoBean bean = staticMap.get(activity.getClass().getName());
        if (bean == null) {
            return;
        }
        final ViewGroup parent = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        //if TranslucentStatus is true , statusBarHeight = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (activity.getWindow().getStatusBarColor() == 0 ||
                    (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS & activity.getWindow().getAttributes().flags)
                            == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) {
                bean.statusBarHeight = 0;
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if ((WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS & activity.getWindow().getAttributes().flags)
                    == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) {
                bean.statusBarHeight = 0;
            }
        }
        parent.post(new Runnable() {
            @Override
            public void run() {
                bean.windowWidth = parent.getWidth();
                bean.windowHeight = parent.getHeight();
                bean.titleHeight = parent.getTop();
                final CirleAnimView cirleAnimView;
                if (showMethod instanceof InflateShowMethod) {
                    cirleAnimView = new CirleAnimView(activity,
                            createBitmap(((InflateShowMethod) showMethod).inflateView, bean.windowWidth, bean.windowHeight));
                } else {
                    cirleAnimView = new CirleAnimView(activity);
                }
                final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                parent.addView(cirleAnimView, params);


                if (targetView != null) {
                    //get Target View's position
                    targetView.getGlobalVisibleRect(bean.targetRect);
                    bean.targetWidth = bean.targetRect.right - bean.targetRect.left;
                    bean.targetHeight = bean.targetRect.bottom - bean.targetRect.top;
                    bean.translationX = bean.originRect.left + bean.originWidth / 2 - bean.targetRect.left - bean.targetWidth / 2;
                    bean.translationY = bean.originRect.top + bean.originHeight / 2 - bean.targetRect.top - bean.targetHeight / 2;
                } else {
                    bean.targetRect.left = bean.originRect.left;
                    bean.targetRect.top = bean.originRect.top;
                    bean.targetWidth = bean.originWidth;
                    bean.targetHeight = bean.originHeight;
                    bean.translationX = 0;
                    bean.translationY = 0;
                }
                //create a temp ImageView to replace origin view
                final ImageView ivTemp = new ImageView(activity);
                if (bean.bitmap != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ivTemp.setImageDrawable(new BitmapDrawable(bean.bitmap));
                    } else {
                        ivTemp.setBackgroundDrawable(new BitmapDrawable(bean.bitmap));
                    }
                }
                RelativeLayout.LayoutParams ivTempParams = new RelativeLayout.LayoutParams(bean.targetWidth,
                        bean.targetHeight);

                ivTempParams.setMargins(bean.originRect.left - (bean.targetWidth / 2 - bean.originWidth / 2)
                        , bean.originRect.top - (parent.getTop() + bean.statusBarHeight) - (bean.targetHeight / 2 - bean.originHeight / 2), 0, 0);
                cirleAnimView.addView(ivTemp, ivTempParams);
                ivTemp.setScaleX((float) bean.originHeight / bean.targetHeight);
                ivTemp.setScaleY((float) bean.originWidth / bean.targetWidth);
                showMethod.translate(bean, cirleAnimView, ivTemp);
                showMethod.loadCopyView(bean, ivTemp);
                showMethod.set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        cirleAnimView.startCircleAnim(bean);
                        showMethod.loadTargetView(bean, targetView);
                    }
                });

            }
        });
    }


    public TransitionsHeleper setShowMethod(ShowMethod showMethod) {
        this.showMethod = showMethod;
        return this;
    }

    public static void unBind(Activity activity) {
        if (staticMap.get(activity.getClass().getName()) != null) {
            InfoBean bean = staticMap.get(activity.getClass().getName());
            if (bean.bitmap != null) {
                bean.bitmap = null;
            }
            staticMap.remove(activity.getClass().getName());
        }
    }

    private static Bitmap createBitmap(View view, int width, int height) {
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

}
