package immortalz.me.transitionhelper.demo.image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransitionsHeleper.getInstance()
                .setShowMethod(new ColorShowMethod(R.color.bg_teal_light,
                        R.color.bg_purple) {
                    @Override
                    public void loadCopyView(InfoBean bean, ImageView copyView) {
                        Glide.with(ImageDetailActivity.this)
                                .load(bean.getImgUrl())
                                .centerCrop()
                                .into(copyView);
                    }

                    @Override
                    public void loadTargetView(InfoBean bean, ImageView targetView) {
                        Glide.with(ImageDetailActivity.this)
                                .load(bean.getImgUrl())
                                .centerCrop()
                                .into(targetView);
                    }

                }).show(this, ivDetail);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_imagedetail;
    }
}
