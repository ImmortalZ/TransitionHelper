package immortalz.me.library;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.expose.CirleExposeView;
import immortalz.me.library.expose.base.ExposeView;
import immortalz.me.library.listener.ExposeListener;
import immortalz.me.library.listener.TransitionListener;
import immortalz.me.library.method.InflateShowMethod;
import immortalz.me.library.method.NoneShowMethod;
import immortalz.me.library.method.ShowMethod;
import immortalz.me.library.util.BitmapUtils;


/**
 * Created by Mr_immortalZ on 2016/10/18.
 * email : mr_immortalz@qq.com
 */

public class TransitionsHeleper {


    private static HashMap<String, InfoBean> sInfoMap = new HashMap<String, InfoBean>();
    private static HashMap<String, WeakReference<TransitionsHeleper>> sTransitionMap = new HashMap<>();

    private Activity activity;

    private ShowMethod showMethod;

    private TransitionListener transitionListener;

    private int transitionDuration;

    private ExposeView exposeView;

    private int exposeColor;

    private int exposeAcceleration;

    private boolean useInflateExpose;

    private View targetView;


    private TransitionsHeleper(TransitionBuilder builder) {
        activity = builder.activity;
        showMethod = builder.showMethod;
        exposeView = builder.exposeView;
        exposeColor = builder.exposeColor;
        exposeAcceleration = builder.exposeAcceleration;
        useInflateExpose = builder.useInflateExpose;
        transitionListener = builder.transitionListener;
        transitionDuration = builder.transitionDuration;
        targetView = builder.targetView;
        sTransitionMap.put(activity.getClass().getName(), new WeakReference<TransitionsHeleper>(this));
    }


    public static TransitionBuilder build(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("You cannot start a load on a null Activity");
        }
        return new TransitionBuilder(activity);
    }

    private static TransitionsHeleper create(TransitionBuilder builder) {
        return new TransitionsHeleper(builder);
    }

    public static void startActivity(Activity activity, Intent intent, View view) {
        startActivity(activity, intent, view, null);
    }

    public static void startActivity(Activity activity, Intent intent, View view, Object load) {
        startEngine(activity, null, intent, view, load, false, -1, null, false, null);
    }


    public static void startActivity(final Activity activity, final Class<?> cls, final View view) {
        startActivity(activity, cls, view, null);
    }

    public static void startActivity(Activity activity, final Class<?> cls, View view, Object load) {
        startEngine(activity, cls, null, view, load, false, -1, null, false, null);
    }

    //Activity ForResult
    public static void startActivityForResult(Activity activity, Intent intent, int requestCode, Bundle options, View view) {
        startActivityForResult(activity, intent, requestCode, options, view, null);
    }

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode, Bundle options, View view, Object load) {
        startEngine(activity, null, intent, view, load, true, requestCode, options, false, null);
    }

    public static void startActivityForResult(Activity activity, Class<?> cls, int requestCode, Bundle options, View view) {
        startActivityForResult(activity, cls, requestCode, options, view, null);
    }

    public static void startActivityForResult(Activity activity, Class<?> cls, int requestCode, Bundle options, View view, Object load) {
        startEngine(activity, cls, null, view, load, true, requestCode, options, false, null);
    }

    //Fragment ForResult
    public static void startActivityForResult(Fragment fragment, Intent intent, int requestCode, Bundle options, View view) {
        startActivityForResult(fragment, intent, requestCode, options, view, null);
    }

    public static void startActivityForResult(Fragment fragment, Intent intent, int requestCode, Bundle options, View view, Object load) {
        startEngine(fragment.getActivity(), null, intent, view, load, true, requestCode, options, true, fragment);
    }

    public static void startActivityForResult(Fragment fragment, Class<?> cls, int requestCode, Bundle options, View view) {
        startActivityForResult(fragment, cls, requestCode, options, view, null);
    }

    public static void startActivityForResult(Fragment fragment, Class<?> cls, int requestCode, Bundle options, View view, Object load) {
        startEngine(fragment.getActivity(), cls, null, view, load, true, requestCode, options, true, fragment);
    }


    private static void startEngine(final Activity activity, final Class<?> cls, Intent intent, final View view, final Object load,
                                    final boolean isForResult, final int requestCode, final Bundle options,
                                    final boolean isFragment, final Fragment fragment) {
        if (activity == null) {
            throw new IllegalArgumentException("You cannot start with a null activity");
        }
        if (intent == null) {
            intent = new Intent(activity, cls);
        }
        if (view == null) {
            throw new IllegalArgumentException("You cannot start a load on a null View");
        }
        final Intent finalIntent = intent;
        view.post(new Runnable() {
            @Override
            public void run() {
                InfoBean<Object> bean = new InfoBean<>();
                //get statusBar height
                view.getWindowVisibleDisplayFrame(bean.originRect);
                bean.statusBarHeight = bean.originRect.top;
                //get Origin View's rect
                view.getGlobalVisibleRect(bean.originRect);
                bean.originWidth = view.getWidth();
                bean.originHeight = view.getHeight();
                if (load == null) {
                    bean.bitmap = BitmapUtils.createBitmap(view, bean.originWidth, bean.originHeight, false);
                } else {
                    if (load instanceof Integer || load instanceof String) {
                        bean.setLoad(load);
                    } else {
                        bean.bitmap = BitmapUtils.createBitmap(view, bean.originWidth, bean.originHeight, false);
                    }
                }
                finalIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                sInfoMap.put(finalIntent.getComponent().getClassName(), bean);
                if (!isForResult) {
                    activity.startActivity(finalIntent);
                } else {
                    if (!isFragment) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            activity.startActivityForResult(finalIntent, requestCode, options);
                        } else {
                            activity.startActivityForResult(finalIntent, requestCode);
                        }
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            fragment.startActivityForResult(finalIntent, requestCode, options);
                        } else {
                            fragment.startActivityForResult(finalIntent, requestCode);
                        }
                    }
                }
            }
        });
    }

    private void show() {
        final InfoBean bean = sInfoMap.get(activity.getClass().getName());
        if (bean == null) {
            return;
        }
        if (showMethod == null) {
            showMethod = new NoneShowMethod();
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
        showMethod.setShowDuration(transitionDuration);
        //final ITransitionListener realTransitionListener = transitionListener.get();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                bean.windowWidth = parent.getWidth();
                bean.windowHeight = parent.getHeight();
                bean.titleHeight = parent.getTop();
                if (exposeView == null) {
                    exposeView = new CirleExposeView(activity);
                }
                if (showMethod instanceof InflateShowMethod) {
                    exposeView.setInflateBitmap(BitmapUtils.createBitmap(((InflateShowMethod) showMethod).inflateView, bean.windowWidth, bean.windowHeight, true));
                }
                exposeView.setExposeColor(exposeColor, useInflateExpose);
                if (exposeAcceleration > 0) {
                    exposeView.setExposeAcceleration(exposeAcceleration);
                }
                final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                parent.addView(exposeView, params);


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
                if (bean.originRect.top + bean.originHeight > (bean.windowHeight + bean.statusBarHeight + bean.titleHeight)) {
                    bean.scale = (float) (bean.windowHeight + bean.statusBarHeight
                            + bean.titleHeight - bean.originRect.top) / bean.targetHeight;
                } else {
                    bean.scale = (float) bean.originHeight / bean.targetHeight;
                }

                RelativeLayout.LayoutParams ivTempParams = new RelativeLayout.LayoutParams((int) (bean.targetWidth * bean.scale),
                        (int) (bean.targetHeight * bean.scale));
                ivTempParams.setMargins((int) (bean.originRect.left + (bean.originWidth / 2 - bean.targetWidth * bean.scale / 2))
                        , bean.originRect.top - (parent.getTop() + bean.statusBarHeight), 0, 0);
                bean.translationY = bean.originRect.top + (int) (bean.targetHeight * bean.scale) / 2 - bean.targetRect.top - bean.targetHeight / 2;
                bean.translationX = bean.originRect.left + bean.originWidth / 2 - bean.targetRect.left - bean.targetWidth / 2;

                exposeView.addView(ivTemp, ivTempParams);
                showMethod.reviseInfo(bean);
                showMethod.translate(bean, exposeView, ivTemp);
                showMethod.loadPlaceholder(bean, ivTemp);
                showMethod.set.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        exposeView.setExposeListener(new ExposeListener() {
                            @Override
                            public void onExposeStart() {
                                if (transitionListener != null) {
                                    transitionListener.onExposeStart();
                                }
                            }

                            @Override
                            public void onExposeProgrees(float progress) {
                                if (transitionListener != null) {
                                    transitionListener.onExposeProgress(progress);
                                }
                            }

                            @Override
                            public void onExposeEnd() {
                                if (transitionListener != null) {
                                    transitionListener.onExposeEnd();
                                }
                                //recycle
                                if (exposeView != null) {
                                    exposeView.stop();
                                    exposeView.removeAllViews();
                                    parent.removeView(exposeView);
                                    exposeView = null;
                                }
                            }
                        });
                        exposeView.startExposeAnim(bean);
                        if (showMethod != null && targetView != null) {
                            showMethod.loadTargetView(bean, targetView);
                        }
                    }
                });


            }
        };
        parent.post(r);
    }


    public static void unbind(Activity activity) {
        if (sTransitionMap.get(activity.getClass().getName()) != null) {
            WeakReference<TransitionsHeleper> weakT = sTransitionMap.get(activity.getClass().getName());
            if (weakT != null && weakT.get() != null) {
                if (weakT.get().exposeView != null) {
                    weakT.get().exposeView.stop();
                    weakT.get().exposeView.removeAllViews();
                    if (weakT.get().exposeView.getParent() != null) {
                        ((ViewGroup) weakT.get().exposeView.getParent()).removeView(weakT.get().exposeView);
                    }
                }
            }
        }
        if (sInfoMap.get(activity.getClass().getName()) != null) {
            InfoBean bean = sInfoMap.get(activity.getClass().getName());
            if (bean.bitmap != null) {
                bean.bitmap = null;
            }
            sInfoMap.remove(activity.getClass().getName());
        }
    }

    public static class TransitionBuilder {
        private Activity activity;

        private ShowMethod showMethod;

        private TransitionListener transitionListener;

        private int transitionDuration = ShowMethod.DEFALUT_DURATION;

        private ExposeView exposeView;

        private int exposeColor;

        private int exposeAcceleration;

        private boolean useInflateExpose;

        private View targetView;

        public TransitionBuilder(Activity activity) {
            this.activity = activity;
        }

        public TransitionBuilder setExposeView(ExposeView exposeView) {
            this.exposeView = exposeView;
            return this;
        }

        public TransitionBuilder setExposeColor(int exposeColor) {
            return setExposeColor(exposeColor, false);
        }

        public TransitionBuilder setExposeColor(int exposeColor, boolean useInflateExpose) {
            this.exposeColor = exposeColor;
            this.useInflateExpose = useInflateExpose;
            return this;
        }

        public TransitionBuilder setShowMethod(ShowMethod showMethod) {
            this.showMethod = showMethod;
            return this;
        }

        public TransitionBuilder intoTargetView(View targetView) {
            this.targetView = targetView;
            return this;
        }

        public TransitionBuilder setTransitionDuration(int transitionDuration) {
            this.transitionDuration = Math.max(transitionDuration, 0);
            return this;
        }

        public TransitionBuilder setTransitionListener(TransitionListener transitionListener) {
            this.transitionListener = transitionListener;
            return this;
        }

        public TransitionBuilder setExposeAcceleration(int exposeAcceleration) {
            this.exposeAcceleration = exposeAcceleration;
            return this;
        }

        public void show() {
            TransitionsHeleper t = TransitionsHeleper.create(this);
            t.show();
        }

    }

}
