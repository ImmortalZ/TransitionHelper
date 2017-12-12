package immortalz.me.transitionhelper.demo.image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import immortalz.me.library.TransitionsHeleper;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.method.ColorShowMethod;
import immortalz.me.transitionhelper.R;
import immortalz.me.transitionhelper.base.BaseActivity;

/**
 * Created by Mr_immortalZ on 2016/10/28.
 * email : mr_immortalz@qq.com
 */

public class ImageDetailActivity extends BaseActivity {
    @Bind(R.id.iv_detail)
    ImageView ivDetail;
    @Bind(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionsHeleper.build(this)
                .setShowMethod(new ColorShowMethod(R.color.bg_teal_light, R.color.bg_purple) {
                    @Override
                    public void loadPlaceholder(InfoBean bean, ImageView placeholder) {
                        Glide.with(ImageDetailActivity.this)
                                .load(bean.getLoad())
                                .centerCrop()
                                .into(placeholder);
                    }

                    @Override
                    public void loadTargetView(InfoBean bean, View targetView) {
                        Glide.with(ImageDetailActivity.this)
                                .load(bean.getLoad())
                                .centerCrop()
                                .into((ImageView) targetView);
                        tv.setText("immortalz");
                    }
                })
                .setExposeColor(getResources().getColor(R.color.bg_purple))
                .intoTargetView(ivDetail)
                .show();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_imagedetail;
    }
}
